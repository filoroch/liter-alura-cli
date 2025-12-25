package com.alura.liter_alura.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.Set;

@Entity
@Table(name = "authors")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    @Getter
    private String name;

    @OneToMany(mappedBy = "author")
    private Set<Book> books;

    @Column(name = "birth_year")
    private Year birthYear;

    @Column(name = "death_year")
    private Year deathYear;

    @Override
    public String toString() {
        // nome: J.K. Rowling, Ano de Nascimento: 1965, Ano de Falecimento: null
        return "nome: " + name + ", Ano de Nascimento: " + birthYear + ", Ano de Falecimento: " + deathYear;
    }
}
