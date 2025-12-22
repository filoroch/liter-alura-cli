package com.alura.liter_alura.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Classe de serviço que realiza operações na API do Gutenberg de CRUD
 */

@Service
public class GutenbergService {

    private final HttpClient client;
    private static final String BASE_URL = "http://gutendex.com/books";

    public GutenbergService(HttpClient client) {
        this.client = client.newBuilder().build();
    }

    private HttpResponse<String> makeRequest(String url) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer requisição para o endpoint de livros do Gutenberg", e);
        }
    }

    /**
     * Método que realiza uma requisição GET ao endpoint de livros do Gutenberg
     * @return HttpResponse<String> contendo a resposta da requisição
     */

    public HttpResponse<String> requestBooksEndpoint() {
        return makeRequest(BASE_URL);
    }

    public HttpResponse<String> requestBooksEndpoint(String search) {
        var encoded = search.replace(" ", "%20");
        var url = BASE_URL + "?search=" + encoded;
        return makeRequest(url);
    }

    /**
     * O metodo vai chamar:
     * requestBooksEndpoint(String search)
     * validar se os espaços foram tratados par %20 ou encodados
     * e validar se o retorno é o esperado
     * */
    public String searchBooks(String query){

        if (query == null || query.isEmpty()){
            throw new IllegalArgumentException("Query não pode ser nula ou vazia");
        }

        var response = requestBooksEndpoint(query);

        if (response.statusCode() == 200){
            return response.body();
        } else {
            throw new RuntimeException("Erro ao buscar livros: " + response.statusCode());
        }
    }


}
