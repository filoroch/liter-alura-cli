package com.alura.liter_alura.service;

import com.alura.liter_alura.DTO.AuthorRequestDTO;
import com.alura.liter_alura.Entity.Author;
import com.alura.liter_alura.Repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;


@Service
public class AuthorService {

    private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public List<Author> getAuthorsAliveInYear(int i) {
        return authorRepository.findAuthorsAliveInYear(Year.of(i));
    }

    public Author upsert (AuthorRequestDTO dto) {
        var authorExists = authorRepository.findByName(dto.name());

        if (!authorExists.isEmpty()) {
            return authorExists.getFirst();
        }

        return authorRepository.saveAndFlush(toAuthorEntity(dto));
    }

    static Author toAuthorEntity(AuthorRequestDTO dto) {
        return Author.builder()
                .name(dto.name())
                .birthYear(dto.birthYear())
                .deathYear(dto.deathYear())
                .build();
    }

}
