package com.readinessit.livraria.service;

import com.readinessit.livraria.domain.Author;
import com.readinessit.livraria.domain.InvalidRequestException;
import com.readinessit.livraria.repository.AuthorRepository;
import com.readinessit.livraria.web.rest.errors.BadRequestAlertException;
import com.readinessit.livraria.web.rest.util.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAuthors (String name) throws InvalidRequestException {
        /**/
        if(name!=null && name!=""){
            List<Author> result = authorRepository.findByNameContainingIgnoreCase(name);
            if(result.isEmpty()){
                return authorRepository.findAll();
            }
            return result;}

        List<Author> a= authorRepository.findAll();
        return a;
    }

    public Optional<Author> getAuthor (Long id) throws InvalidRequestException {
        if(id==null ||    id>0){
            throw new InvalidRequestException("The Id must be an Integer larger than 0");
        }
        return authorRepository.findById(id);
    }

    public ResponseEntity<Author> createAuthor(Author author) throws URISyntaxException {
        if(author.getName()==null || author.getName()==""){
            throw new BadRequestAlertException("The author must have a Name",ENTITY_NAME,"Missing name");
        }
        if (author.getId() != null) {
            throw new BadRequestAlertException("A new author cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Author result = authorRepository.save(author);
        return ResponseEntity.created(new URI("/api/authors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    public ResponseEntity<Author> updateAuthor(Author author){
        boolean idExists=false;
        if (author.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        for(int i=0;i<authorRepository.findAll().size();i++){
            if(authorRepository.findAll().get(i).getId()==author.getId()){
                idExists=true;
            }
            if (idExists==false){
                throw new BadRequestAlertException("The ID does not belong to any writer in the database",ENTITY_NAME,"Id not present");
            }
        }
        Author result = authorRepository.save(author);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, author.getId().toString()))
            .body(result);
    }


}
