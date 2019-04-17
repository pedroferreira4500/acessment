package com.readinessit.livraria.repository;

import com.readinessit.livraria.domain.Client;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> getByEmail(String email);
}
