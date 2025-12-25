package com.alura.liter_alura.service;

import com.alura.liter_alura.DTO.AuthorRequestDTO;
import com.alura.liter_alura.DTO.BookRequestDTO;
import com.alura.liter_alura.Entity.Author;
import com.alura.liter_alura.Entity.Book;
import com.alura.liter_alura.Entity.Language;
import com.alura.liter_alura.Repository.AuthorRepository;
import com.alura.liter_alura.Repository.BookRepository;
import com.alura.liter_alura.exceptions.BookNotFound;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final ObjectMapper mapper;
    private BookRepository bookRepository;
    private AuthorService authorService;

    public BookService(
            BookRepository bookRepository,
            AuthorService authorService
    ) {
        this.authorService = authorService;
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.bookRepository = bookRepository;
    }

    public Book create (BookRequestDTO dto) {
        /// Verificar se os livros ja existem
        var book = toBookEntity(dto);
        var bookExists = bookRepository.findByTitle(book.getTitle());

        if (bookExists != null){
            return bookExists.getFirst();
        }

        /// Verificar se um autor existe e retorna-lo
        var author = book.getAuthor();

        if (author != null){
            var authorExists = authorService.upsert(dto.authors().stream().findFirst().get());

            if (authorExists != null){
                book.setAuthor(authorExists);
            }
        }

        return bookRepository.saveAndFlush(book);
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public static Book toBookEntity(BookRequestDTO dto) {
        var authors = dto.authors().stream()
                .map(AuthorService::toAuthorEntity)
                .collect(Collectors.toSet());

        return Book.builder()
                .title(dto.title())
                .author(authors.stream().findFirst().get())
                .languages(dto.languages().stream().findFirst().get())
                .download_count(dto.downloadCount())
                .build();
    }

    public List<BookRequestDTO> convertJsonToDTOs(String json) throws JsonProcessingException {
        var root = mapper.readTree(json);
        var countNode = root.get("count");

        if (countNode == null || countNode.asInt() == 0) throw new BookNotFound("Não foram encontrados livros com os critérios informados.");

        var resultsNode = root.get("results");
        if (resultsNode == null || !resultsNode.isArray()) throw new RuntimeException("Formato de resposta inesperado da API de livros.");

        return mapper.convertValue(resultsNode, new TypeReference<List<BookRequestDTO>>() {});
    }

    public List<Book> getBooksByLanguage(Language language) {
        var books = bookRepository.findByLanguages(language);
        if (books.isEmpty()) throw new BookNotFound("Nenhum livro encontrado para o idioma: " + language);
        return books;
    }
}

