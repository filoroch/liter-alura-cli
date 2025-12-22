package com.alura.liter_alura.DTO;

import com.alura.liter_alura.Entity.Language;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.util.Set;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookRequestDTO(

    @JsonAlias({"title", "book_title"})
    String title,

    @JsonAlias({"authors", "book_authors"})
    Set<AuthorRequestDTO> authors,

    @JsonAlias
    Set<Language> languages,

    @JsonAlias({"download_count", "downloadCount"})
    Double downloadCount
) {
}
