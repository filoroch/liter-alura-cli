package com.alura.liter_alura.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;

import java.time.Year;

@Builder
public record AuthorRequestDTO(

        @JsonAlias({"name", "author_name"})
        String name,

        @JsonAlias({"birth_year", "birthYear"})
        Year birthYear,

        @JsonAlias({"death_year", "deathYear"})
        Year deathYear
) {
}
