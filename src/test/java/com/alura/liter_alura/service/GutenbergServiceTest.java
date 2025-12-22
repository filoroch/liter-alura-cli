package com.alura.liter_alura.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Nested;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GutenbergServiceTest {
    //TODO: implementar requests para o Gutenberg API
    // Quando a api consegue retornar livros. 200
    // Quando a api retorna erro. 4xx ou 5xx

    @InjectMocks
    GutenbergService gutenbergService;

    @Mock
    HttpClient client;

    @Mock
    HttpResponse<String> mockResponse;

    @Nested
    @DisplayName("Testes para o método requestBooksEndpoint")
    class RequestBooksEndpointTests {
        /**
         * Preciso que esse teste, o metodo
         * faça uma requisição a url e
         * retorne o resultado quando nenhum parametro é passado para ela. Preciso validar
         * se o resultado é oque esperamos
         *
         */
        @Test
        @DisplayName("Deve retornar livros quando nenhum parâmetro for passado")
        void requestBookEndpoint_ShouldReturnBooksWhenNoParameters() throws IOException, InterruptedException {

            var expectedBody = "{ \"count\": 1 }";

            when(mockResponse.statusCode()).thenReturn(200);
            when(mockResponse.body()).thenReturn(expectedBody);
            when(client.send(any(HttpRequest.class),
                    eq(HttpResponse.BodyHandlers.ofString())))
                    .thenReturn(mockResponse);

            var response = gutenbergService.requestBooksEndpoint();

            assertNotNull(response, "Response não deve ser null");
            assertEquals(200, response.statusCode(), "Status code deve ser 200");
            assertEquals(expectedBody, response.body(), "Body da resposta deve ser igual ao esperado");

            // Verifica se cliente foi chamado pelo menos uma vez
            verify(client, times(1))
                    .send(any(HttpRequest.class),
                            eq(HttpResponse.BodyHandlers.ofString()));
        }

        @Test
        @DisplayName("Deve retornar status de erro quando a resposta for 5xx")
        void requestBookEndpoint_ShouldReturnErrorStatusWhen5xx() throws IOException, InterruptedException {

            // Quando o status da response é igual a 500
            when(mockResponse.statusCode()).thenReturn(500);
            when(client.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(mockResponse);

            var response = gutenbergService.requestBooksEndpoint();

            assertNotNull(response, "Response não deve ser null mesmo em erro");
            assertEquals(500, response.statusCode(), "Status code deve ser 500");

            verify(client, times(1)).send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()));
        }
    }


    @Nested
    @DisplayName("Testes para o método searchBooks")
    class SearchBooksTests {

        @Test
        @DisplayName("Deve retornar uma lista vazia quando nenhum livro for encontrado")
        void searchBooks_ShouldReturnEmptyListWhenNoBooksFound() throws IOException, InterruptedException {

        }

        @Test
        @DisplayName("Deve codificar a query e retornar o corpo da resposta")
        void searchBooks_ShouldEncodeQueryAndRetornBody() throws IOException, InterruptedException {

            // Defino a query e a resposta da query
            var search = "Dom Casmurro";
            var expectedResponse = "{ \"title\": \"Dom Casmurro\" }";

            // Configuro o mock para retornar a resposta esperada
            when(mockResponse.statusCode()).thenReturn(200);
            when(mockResponse.body()).thenReturn(expectedResponse);

            // Capturo o request feito pelo cliente
            ArgumentCaptor<HttpRequest> requestCaptor = ArgumentCaptor.forClass(HttpRequest.class);

            when(client.send(requestCaptor.capture(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(mockResponse);

            var response = gutenbergService.searchBooks(search);

            // Validações
            assertNotNull(response, "Response não deve ser null");
            assertTrue(response.contains(search), "Response deve conter o termo buscado");

            HttpRequest capturedRequest = requestCaptor.getValue();
            String actualUrl = capturedRequest.uri().toString();

            assertTrue(actualUrl.contains("Dom%20Casmurro"), "A URL deve conter o termo buscado com espaços codificados como %20" + actualUrl);

            // Verifica se cliente foi chamado pelo menos uma vez
            verify(client, times(1)).send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()));
        }

        @Test
        @DisplayName("Deve lançar IllegalArgumentException para query nula ou vazia")
        void searchBooks_ShouldThrowExceptionForNullOrEmptyQuery() throws IOException, InterruptedException {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> gutenbergService.searchBooks(null));

            assertNotNull(ex.getMessage(), "Mensagem de exceção não deve ser null");;
            assertTrue(ex.getMessage().contains("Query não pode ser nula ou vazia"), "Mensagem de exceção deve indicar que a query não pode ser nula ou vazia");

            verifyNoInteractions(client);
        }
    }
}