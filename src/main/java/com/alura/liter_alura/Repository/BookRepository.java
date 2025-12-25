package com.alura.liter_alura.Repository;

import com.alura.liter_alura.Entity.Book;
import com.alura.liter_alura.Entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitle(String title);
    List<Book> findByLanguages(Language language);
}
