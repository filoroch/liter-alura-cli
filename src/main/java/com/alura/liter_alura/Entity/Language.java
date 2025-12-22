package com.alura.liter_alura.Entity;

import com.fasterxml.jackson.annotation.JsonAlias;

public enum Language {
    @JsonAlias({"en", "english"})
    ENGLISH("en"),
    SPANISH("es"),
    FRENCH("fr"),
    GERMAN("de"),
    ITALIAN("it"),
    PORTUGUESE("pt");

    private final String code;

    Language(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
