package com.readinessit.livraria.web.rest;
import com.readinessit.livraria.domain.Client;
import com.readinessit.livraria.repository.ClientRepository;
import com.readinessit.livraria.security.SecurityUtils;
import com.readinessit.livraria.service.ClientService;
import com.readinessit.livraria.web.rest.errors.BadRequestAlertException;
import com.readinessit.livraria.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

import static com.readinessit.livraria.security.AuthoritiesConstants.ADMIN;

/**
 * REST controller for managing Client.
 */
@RestController
@RequestMapping("/api")
public class ClientResource {

    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private static final String ENTITY_NAME = "client";

    @Autowired
    ClientService clientService;

    private final ClientRepository clientRepository;

    public ClientResource(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * POST  /clients : Create a new client.
     *
     * @param client the client to create
     * @return the ResponseEntity with status 201 (Created) and with body the new client, or with status 400 (Bad Request) if the client has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) throws URISyntaxException {
        if(!SecurityUtils.isCurrentUserInRole(ADMIN)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"Not_Authenthicated","You need to be logged in to perform this action")).body(null);
        }
        log.debug("REST request to save Client : {}", client);
        return clientService.createClient(client);
    }

    /**
     * PUT  /clients : Updates an existing client.
     *
     * @param client the client to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated client,
     * or with status 400 (Bad Request) if the client is not valid,
     * or with status 500 (Internal Server Error) if the client couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clients")
    public ResponseEntity<Client> updateClient(@Valid @RequestBody Client client) throws URISyntaxException {
        if(!SecurityUtils.isCurrentUserInRole(ADMIN)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"Not_Authenthicated","You need to be logged in to perform this action")).body(null);
        }
        return clientService.updateClient(client);
    }

    /**
     * GET  /clients : get all the clients.
     * @Param email(optional)
     * @return the ResponseEntity with status 200 (OK) and the list of clients in body
     */
    @GetMapping("/clients")
    public List<Client> getAllClients(@RequestParam (required = false, name = "email") String email) {
        return clientService.getClients(email);
    }

    /**
     * GET  /clients/:id : get the "id" client.
     *
     * @param id the id of the client to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the client, or with status 404 (Not Found)
     */
    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        log.debug("REST request to get Client : {}", id);
        return clientService.getClient(id);
    }

    /**
     * DELETE  /clients/:id : delete the "id" client.
     *
     * @param id the id of the client to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        throw new BadRequestAlertException("It is not possible to delete a client because of dependency issues",ENTITY_NAME,"Dependency issues");
    }

    @DeleteMapping("/clients")
    public ResponseEntity<Void> deleteClients() {
        throw new BadRequestAlertException("It is not possible to delete a client because of dependency issues",ENTITY_NAME,"Dependency issues");
    }
}
