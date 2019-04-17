package com.readinessit.livraria.repository;

import com.readinessit.livraria.domain.Sales;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Sales entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {
    List <Sales> getAllByClientId(Long client);
    List <Sales> getAllByBookId(Long book);
}
