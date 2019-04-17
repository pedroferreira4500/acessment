package com.readinessit.livraria.repository;

import com.readinessit.livraria.domain.Author;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Author entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findAllByName(String author);
    List<Author> findByNameContainingIgnoreCase(String author);


}
