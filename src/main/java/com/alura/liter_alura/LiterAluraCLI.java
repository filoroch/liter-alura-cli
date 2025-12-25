package com.alura.liter_alura;

import com.alura.liter_alura.Entity.Author;
import com.alura.liter_alura.Entity.Book;
import com.alura.liter_alura.Entity.Language;
import com.alura.liter_alura.exceptions.AuthorNotFound;
import com.alura.liter_alura.exceptions.BookNotFound;
import com.alura.liter_alura.service.AuthorService;
import com.alura.liter_alura.service.BookService;
import com.alura.liter_alura.service.GutenbergService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.alura.liter_alura.service.BookService.toBookEntity;
import static java.lang.Integer.parseInt;

@SpringBootApplication
public class LiterAluraCLI implements CommandLineRunner {

    @Autowired
    private GutenbergService gutenbergService;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private Scanner input;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraCLI.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

        int ctrl = -1;

        while (ctrl != 0) {
            System.out.println(
                """
                
                1. Buscar livros por titulo
                2. Buscar todos os livros registrados
                3. Buscar livros por autores
                4. Buscar autores vivos em um determinado ano   
                5. Buscar livros por linguagem
                0. Sair
                
                """
            );

            ctrl = readIntInput("Escolha uma opção: ");

            switch (ctrl){
                case 1 -> bookByTitle();
                case 2 -> listBooks();
                case 3 -> listAuthors();
                case 4 -> authorByYearAlive();
                case 5 -> booksByLanguage();
                case 0 -> {
                    System.out.println("Saindo...");
                    ctrl = 0;
                }
            }
        }
    }
    private void booksByLanguage(){
        try {
            var languageTarget = readTextInput("Digite o código da linguagem (ex: 'en' para inglês): ");
            Language language;

            switch (languageTarget){
                case "en" -> language = Language.ENGLISH;
                case "fr" -> language = Language.FRENCH;
                case "de" -> language = Language.GERMAN;
                case "es" -> language = Language.SPANISH;
                case "it" -> language = Language.ITALIAN;
                case "sv" -> language = Language.SWEDISH;
                case "nl" -> language = Language.DUTCH;
                case "fi" -> language = Language.FINNISH;
                case "pl" -> language = Language.POLISH;
                case "pt" -> language = Language.PORTUGUESE;
                default -> throw new IllegalArgumentException("Idioma não suportado: " + languageTarget);
            }
            var booksByLanguage = bookService.getBooksByLanguage(language);
            booksByLanguage.forEach(book -> System.out.println(book.toString()));
        } catch (BookNotFound | IllegalArgumentException e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
        } catch (RuntimeException e){
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        } finally {
            System.out.println("");
        }
    }
    private void authorByYearAlive(){
        try{
            var year = readIntInput("Digite o ano do author que procura: ");
            var autoresVivosEmDeterminadoAno = authorService.getAuthorsAliveInYear(year);

            if (autoresVivosEmDeterminadoAno.isEmpty()){
                throw new AuthorNotFound("Nenhum autor vivo encontrado para o ano especificado." + year);
            }

            autoresVivosEmDeterminadoAno.forEach(author -> System.out.println(author.toString()));
        } catch (RuntimeException e){
            System.out.println("Ocorreu um erro: " + e.getMessage());
        } finally {
            System.out.println("");
        }
    }
    private void listAuthors(){
        var authors = authorService.getAll();
        authors.stream()
                .sorted(Comparator.comparing(Author::getName))
                .forEach(author -> System.out.println(author.toString()));
    }
    private void listBooks(){
        var allboks = bookService.getAll();

        if (allboks.isEmpty()){
            throw new BookNotFound("Nenhum livro cadastrado no sistema.");
        }

        allboks.stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .forEach(book -> System.out.println(book.toString()));
    }
    private void bookByTitle(){
        try {
            var title = readTextInput("Digite o título do livro: ");
            var response = gutenbergService.searchBooks(title);
            var resultsDto = bookService.convertJsonToDTOs(response);
            var results = resultsDto.getFirst();
            var result = toBookEntity(results);

            System.out.println("Livro encontrado: \n" + result.getTitle() + " de " + result.getAuthor().getName() + " com " + result.getDownloadCount() + " downloads.");

        } catch (RuntimeException | JsonProcessingException e){
            System.out.println("Ocorreu um erro: " + e.getMessage());
        } finally {
            System.out.println("");
        }
    }

    private int readIntInput(String message){
        System.out.println(message);

        if (!input.hasNextLine()) throw new RuntimeException("Entrada inválida. O valor não pode ser textual. Tente novamente.");

        var userInput = input.nextLine();
        return parseInt(userInput);

    }

    private String readTextInput(String message){

            if (!message.isEmpty()) {
                System.out.println(message);
            }
            if (!input.hasNextLine() | input.nextLine().isEmpty()) {
                throw new RuntimeException("Entrada inválida. Tente novamente.");
            }

            return input.nextLine();

    }

}
