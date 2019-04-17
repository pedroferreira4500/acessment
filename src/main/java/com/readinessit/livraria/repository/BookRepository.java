package com.readinessit.livraria.repository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.readinessit.livraria.domain.Book;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;



/**
 * Spring Data  repository for the Book entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByName(String name);
    List<Book> findByNameContainingIgnoreCase(String name);
    List<Book> findAllByWriterId(String author);
    void deleteAllByName(String title);
}
