package com.alura.liter_alura.service;

import com.alura.liter_alura.DTO.AuthorRequestDTO;
import com.alura.liter_alura.DTO.BookRequestDTO;
import com.alura.liter_alura.Entity.Book;
import com.alura.liter_alura.Entity.Language;
import com.alura.liter_alura.Repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.Collections;

import static com.alura.liter_alura.service.BookService.toBookEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    BookService bookService;

    @Mock
    BookRepository bookRepository;

    @Nested
    @DisplayName("Crud Operations")
    class CrudOperationsTest{

        private BookRequestDTO b1;
        private BookRequestDTO b2;

        @BeforeEach
        void setUp(){
            var author = AuthorRequestDTO.builder()
                    .name("George Orwell")
                    .birthYear(Year.of(1903))
                    .deathYear(Year.of(1950))
                    .build();

            b1 = BookRequestDTO.builder()
                    .title("1984")
                    .authors(Collections.singleton((author)))
                    .languages(Collections.singleton(Language.ENGLISH))
                    .downloadCount(15615.0)
                    .build();

            b2 = BookRequestDTO.builder()
                    .title("A Revolução dos Bichos")
                    .authors(Collections.singleton((author)))
                    .languages(Collections.singleton(Language.ENGLISH))
                    .downloadCount(2305.5)
                    .build();
        }

        @Test
        @DisplayName("Deve criar dois novos livros no banco de dados")
        void createBook_ShouldCreateNewBookInDatabase() {

            Book savedBook1 = toBookEntity(b1);
            savedBook1.setId(1);  // Simula ID gerado pelo banco

            Book savedBook2 = toBookEntity(b2);
            savedBook2.setId(2);

            when(bookRepository.save(any(Book.class)))
                    .thenReturn(savedBook1)
                    .thenReturn(savedBook2);

            var r1 = bookService.create(b1);
            var r2 = bookService.create(b2);

            assertThat(r1).isNotNull();
            assertThat(r2).isNotNull();
            assertThat(r1.getTitle()).isEqualTo("1984");
            assertThat(r2.getTitle()).isEqualTo("A Revolução dos Bichos");
            assertThat(r1.getId()).isEqualTo(1L);
            assertThat(r2.getId()).isEqualTo(2L);

            verify(bookRepository, times(2)).save(any(Book.class));
        }
    }

    @Nested
    @DisplayName("Converter DTO para Entidade")
    class ConvertDtoToEntityTests {

        @Test
        @DisplayName("Deve chamar o método convertDtoToEntity do BookService")
        void convertDtoToEntity_ShouldCallMethod() {
            // Arrange
            BookRequestDTO bookRequestDTO = BookRequestDTO.builder()
                    .title("Test Book")
                    .authors(Collections.emptySet())
                    .languages(Collections.emptySet())
                    .build();

            // Act
            var entity = toBookEntity(bookRequestDTO);

            assertNotNull(entity);
            assertTrue(entity instanceof Book);
            assertEquals(bookRequestDTO.title(), entity.getTitle());
        }
    }

    @Nested
    @DisplayName("Testando a conversão de JSON para DTOs")
    class ConvertJsonToDTOsTests {

        @Mock
        BookRequestDTO mockBookRequestDTO;

        @InjectMocks
        ObjectMapper mapper = new ObjectMapper();

        @BeforeEach
        void setup() {
            mapper.registerModule(new JavaTimeModule());
        }

        @Test
        @DisplayName("Sucesso na conversão de JSON para DTOs")
        void convertJsonToDTOs_ShouldSucceed() throws JsonProcessingException {

            // Json de exemplo
            String json = """
            {
                "count": 1,
                "next": null,
                "previous": null,
                "results": [
                    {
                        "id": 2701,
                        "title": "Moby Dick; Or, The Whale",
                        "authors": [
                            {
                                "name": "Melville, Herman",
                                "birth_year": 1819,
                                "death_year": 1891
                            }
                        ],
                        "summaries": [
                            "\\"Moby Dick; Or, The Whale\\" by Herman Melville is a novel written in the mid-19th century. The story follows Ishmael, a sailor on a whaling voyage, who seeks adventure and escape from his gloomy life on land. As he embarks on this journey, he becomes drawn into the complex world of whaling and is introduced to the ominous figure of Captain Ahab, whose obsession with a legendary white whale ultimately drives the narrative.  At the start of the novel, Ishmael introduces himself and shares his philosophy about the sea as a remedy for his melancholic disposition. He muses on the magnetic pull of the ocean, describing not only his own urge to set sail but also the collective longing of city dwellers for the water. Ishmael's journey takes him to New Bedford, where he experiences a series of humorous and strange encounters while seeking lodging before joining a whaling ship. As he navigates his way through the town, he is introduced to Queequeg, a tattooed harpooner with a mysterious past, setting the stage for a unique friendship that unfolds amidst the backdrop of whaling adventures. (This is an automatically generated summary.)"
                        ],
                        "editors": [],
                        "translators": [],
                        "subjects": [
                            "Adventure stories",
                            "Ahab, Captain (Fictitious character) -- Fiction",
                            "Mentally ill -- Fiction",
                            "Psychological fiction",
                            "Sea stories",
                            "Ship captains -- Fiction",
                            "Whales -- Fiction",
                            "Whaling -- Fiction",
                            "Whaling ships -- Fiction"
                        ],
                        "bookshelves": [
                            "Best Books Ever Listings",
                            "Category: Adventure",
                            "Category: American Literature",
                            "Category: Classics of Literature",
                            "Category: Novels"
                        ],
                        "languages": [
                            "en"
                        ],
                        "copyright": false,
                        "media_type": "Text",
                        "formats": {
                            "text/html": "https://www.gutenberg.org/ebooks/2701.html.images",
                            "application/epub+zip": "https://www.gutenberg.org/ebooks/2701.epub3.images",
                            "application/x-mobipocket-ebook": "https://www.gutenberg.org/ebooks/2701.kf8.images",
                            "text/plain; charset=us-ascii": "https://www.gutenberg.org/ebooks/2701.txt.utf-8",
                            "application/rdf+xml": "https://www.gutenberg.org/ebooks/2701.rdf",
                            "image/jpeg": "https://www.gutenberg.org/cache/epub/2701/pg2701.cover.medium.jpg",
                            "application/octet-stream": "https://www.gutenberg.org/cache/epub/2701/pg2701-h.zip"
                        },
                        "download_count": 119658
                    }
                ]
            }
            """;

            var root = mapper.readTree(json);
            var results = root.get("results");
            var books = results.get(0);
            var authors = books.get("authors");
            var _language = books.get("languages").get(0);
            var language = Language.ENGLISH;
            var download_count = books.get("download_count").asDouble();

            switch (_language.asText()) {
                case "en" -> language = Language.ENGLISH;
                case "fr" -> language = Language.FRENCH;
                case "de" -> language = Language.GERMAN;
                case "es" -> language = Language.SPANISH;
                default -> throw new IllegalArgumentException("Idioma não suportado: " + _language.asText());
            }

            var author = AuthorRequestDTO.builder()
                    .name(authors.get(0).get("name").asText())
                    .birthYear(Year.of(authors.get(0).get("birth_year").asInt()))
                    .deathYear(Year.of(authors.get(0).get("death_year").asInt()))
                    .build();

            var book = BookRequestDTO.builder()
                    .title(books.get("title").asText())
                    .authors(Collections.singleton(author))
                    .languages(Collections.singleton(language))
                    .downloadCount(download_count)
                    .build();

           var response = bookService.convertJsonToDTOs(json);

           // Verificar se o retorno é o esperado
          assertNotNull(response);
          assertEquals(1, response.size());
          assertEquals(response.getFirst().title(), book.title());
          assertEquals(response.getFirst().authors(), book.authors());
          assertEquals(response.getFirst().languages(), book.languages());
          assertEquals(response.getFirst().downloadCount(), book.downloadCount());
        }

        @Test
        @DisplayName("Falha na conversão por não encontrar livros")
        void convertJsonToDTOs_ShouldFail_NoBooksFound() {
            String json = """
            {
                "count": 0,
                "next": null,
                "previous": null,
                "results": []
            }
            """;

            Exception exception = assertThrows(RuntimeException.class, () -> {
                bookService.convertJsonToDTOs(json);
            });

            String expectedMessage = "Não foram encontrados livros com os critérios informados.";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
        }

        @Test
        @DisplayName("Falha na conversão por formato inesperado")
        void convertJsonToDTOs_ShouldFail_UnexpectedFormat() {
            String json = """
            {
                "count": 1,
                "next": null,
                "previous": null,
                "results": {}
            }
            """;

            Exception exception = assertThrows(RuntimeException.class, () -> {
                bookService.convertJsonToDTOs(json);
            });

            String expectedMessage = "Formato de resposta inesperado da API de livros.";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
        }
    }
}