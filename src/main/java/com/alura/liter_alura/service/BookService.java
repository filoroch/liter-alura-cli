package com.alura.liter_alura.service;

import com.alura.liter_alura.DTO.BookRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final ObjectMapper mapper;

    public BookService() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    public List<BookRequestDTO> convertJsonToDTOs(String json) throws JsonProcessingException {
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

