package com.alura.liter_alura.Entity;

import com.fasterxml.jackson.annotation.JsonAlias;

public enum Language {
    @JsonAlias({"en", "english"})
    ENGLISH("en"),

    @JsonAlias({"es", "spanish"})
    SPANISH("es"),

    @JsonAlias({"fr", "french"})
    FRENCH("fr"),

    @JsonAlias({"de", "german"})
    GERMAN("de"),

    @JsonAlias({"it", "italian"})
    ITALIAN("it"),

    @JsonAlias({"sv", "swedish"})
    SWEDISH("sv"),

    @JsonAlias({"nl", "dutch"})
    DUTCH("nl"),

    @JsonAlias({"fi", "finnish"})
    FINNISH("fi"),

    @JsonAlias({"pl", "polish"})
    POLISH("pl"),

    @JsonAlias({"pt", "portuguese"})
    PORTUGUESE("pt");

    private final String code;

    Language(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
