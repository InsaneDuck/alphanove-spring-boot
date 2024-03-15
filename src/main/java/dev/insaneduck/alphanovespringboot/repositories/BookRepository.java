package dev.insaneduck.alphanovespringboot.repositories;

import dev.insaneduck.alphanovespringboot.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}