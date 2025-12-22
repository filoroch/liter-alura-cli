package com.alura.liter_alura.Repository;

import com.alura.liter_alura.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
