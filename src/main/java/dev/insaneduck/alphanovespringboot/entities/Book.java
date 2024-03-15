package dev.insaneduck.alphanovespringboot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "book", schema = "data")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "author", nullable = false, length = 50)
    private String author;

    @Column(name = "isbn", nullable = false, length = 15)
    private String isbn;

    @Column(name = "publisher", nullable = false, length = 50)
    private String publisher;

    @Column(name = "publication_year", nullable = false)
    private Integer publicationYear;

    @Column(name = "genre", nullable = false, length = 200)
    private String genre;

    @Column(name = "description", nullable = false, length = 2000)
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "language", nullable = false, length = 200)
    private String language;

    @Column(name = "page_count", nullable = false)
    private Integer pageCount;

    @Column(name = "cover_image", nullable = false, length = 50)
    private String coverImage;

    @Column(name = "availability", nullable = false)
    private Byte availability;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "edition", nullable = false)
    private Integer edition;

    @Column(name = "series", nullable = false, length = 500)
    private String series;

}