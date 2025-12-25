package com.alura.liter_alura.Repository;

import com.alura.liter_alura.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Year;
import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    List<Author> findByName(String name);

    @Query("SELECT a FROM Author a WHERE a.birthYear <= :year AND (a.deathYear IS NULL OR a.deathYear >= :year)")
    List<Author> findAuthorsAliveInYear(Year year);
}
