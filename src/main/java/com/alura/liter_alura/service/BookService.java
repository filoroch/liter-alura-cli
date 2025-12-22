package com.alura.liter_alura.service;

import com.alura.liter_alura.DTO.AuthorRequestDTO;
import com.alura.liter_alura.DTO.BookRequestDTO;
import com.alura.liter_alura.Entity.Author;
import com.alura.liter_alura.Entity.Book;
import com.alura.liter_alura.Repository.BookRepository;
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

    public BookService(BookRepository bookRepository) {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.bookRepository = bookRepository;
    }

    public Book create (BookRequestDTO dto) {
        var book = toBookEntity(dto);
        return bookRepository.save(book);
    }

    static Author toAuthorEntity(AuthorRequestDTO dto) {
        return Author.builder()
                .name(dto.name())
                .birthYear(dto.birthYear())
                .deathYear(dto.deathYear())
                .build();
    }

    static Book toBookEntity(BookRequestDTO dto) {
        var authors = dto.authors().stream()
                .map(BookService::toAuthorEntity)
                .collect(Collectors.toSet());

        return Book.builder()
                .title(dto.title())
                .authors(authors)
                .languages(dto.languages().stream().toList())
                .build();
    }

    protected List<BookRequestDTO> convertJsonToDTOs(String json) throws JsonProcessingException {
        var root = mapper.readTree(json);
        var countNode = root.get("count");

        if (countNode == null || countNode.asInt() == 0) {
            throw new RuntimeException("Não foram encontrados livros com os critérios informados.");
        }

        var resultsNode = root.get("results");

        if (resultsNode == null || !resultsNode.isArray()) {
            throw new RuntimeException("Formato de resposta inesperado da API de livros.");
        }

        return mapper.convertValue(resultsNode, new TypeReference<List<BookRequestDTO>>() {});
    }
}

