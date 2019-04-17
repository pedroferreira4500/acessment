package com.readinessit.livraria.web.rest;
import com.readinessit.livraria.domain.Author;
import com.readinessit.livraria.domain.Basket;
import com.readinessit.livraria.domain.InvalidRequestException;
import com.readinessit.livraria.repository.AuthorRepository;
import com.readinessit.livraria.security.SecurityUtils;
import com.readinessit.livraria.service.AuthorService;
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
 * REST controller for managing Author.
 */
@RestController
@RequestMapping("/api")
public class AuthorResource {

    private final Logger log = LoggerFactory.getLogger(AuthorResource.class);

    private static final String ENTITY_NAME = "author";

    @Autowired
    private AuthorService authorService;

    private final AuthorRepository authorRepository;

    public AuthorResource(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * POST  /authors : Create a new author.
     *
     * @param author the author to create
     * @return the ResponseEntity with status 201 (Created) and with body the new author, or with status 400 (Bad Request) if the author has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/authors")
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) throws URISyntaxException {
        if(!SecurityUtils.isCurrentUserInRole(ADMIN)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"Not_Authenthicated","You need to be logged in to perform this action")).body(null);
        }
        log.debug("REST request to save Author : {}", author);
        return authorService.createAuthor(author);
    }

    /**
     * PUT  /authors : Updates an existing author.
     *
     * @param author the author to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated author,
     * or with status 400 (Bad Request) if the author is not valid,
     * or with status 500 (Internal Server Error) if the author couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/authors")
    public ResponseEntity<Author> updateAuthor(@Valid @RequestBody Author author) throws URISyntaxException {
        if(!SecurityUtils.isCurrentUserInRole(ADMIN)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"Not_Authenthicated","You need to be logged in to perform this action")).body(null);
        }
        log.debug("REST request to update Author : {}", author);
        return authorService.updateAuthor(author);
    }

    /**
     * GET  /authors : get all the authors.
     * GET  /authors{name} get authors by name
     *
     * @return the ResponseEntity with status 200 (OK) and the list of authors in body
     */
    @GetMapping("/authors")
    public List<Author> getAuthors(
        @RequestParam (required = false, name="name") String name ) throws InvalidRequestException {
            return authorService.getAuthors(name);
    }

    /**
     * GET  /author/:id : get the "id" author.
     *
     * @param id the id of the author to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the author, or with status 404 (Not Found)
     */
    @GetMapping("/authors/{id}")
    public ResponseEntity<Author> getAuthors(@PathVariable Long id) {
        log.debug("REST request to get Author : {}", id);
        Optional<Author> author = authorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(author);
    }



    /**
     * DELETE  /authors/:id : delete the "id" author.
     *
     * @param id the id of the author to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) throws BadRequestAlertException {
        throw new BadRequestAlertException("Not possible to delete Book due to dependency issues",ENTITY_NAME,"Dependency issues");
    }

    @DeleteMapping("/authors")
    public ResponseEntity<Void> deleteAuthors() throws InvalidRequestException {
        throw new BadRequestAlertException("Not possible to delete Books due to dependency issues",ENTITY_NAME,"Dependency issues");
    }
}
