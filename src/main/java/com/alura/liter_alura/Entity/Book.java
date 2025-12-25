package com.alura.liter_alura.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Enumerated(EnumType.STRING)
    @Column(name = "languages")
    private Language languages;

    @Column(name = "download_count")
    private Double download_count;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Language getLanguages() {
        return languages;
    }

    public Double getDownloadCount() {
        return download_count;
    }

    @Override
    public String toString() {
        // " title: 'The Great Book', author: 'John Doe', languages: 'EN', download_count: 1234.0
        return " title: '" + title + ", author: " + author.getName() + ", languages: " + languages + ", download_count: " + download_count;
    }
}
