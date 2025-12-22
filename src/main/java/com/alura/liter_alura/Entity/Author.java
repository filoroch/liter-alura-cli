package com.alura.liter_alura.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private String name;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;

    @Column(name = "birth_year")
    private Year birthYear;

    @Column(name = "death_year")
    private Year death_year;
}
