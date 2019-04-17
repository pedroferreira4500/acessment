package com.readinessit.livraria.service;

import com.readinessit.livraria.domain.Book;
import com.readinessit.livraria.repository.BookRepository;
import com.readinessit.livraria.web.rest.errors.BadRequestAlertException;
import com.readinessit.livraria.web.rest.util.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorService authorService;

    /**
     * will receive a String with the title of the book and a String with the name of the author.
     * Will output the books matching one or both of those paramenters, if both parameters are null
     * will return all the books
     */
    public List<Book> findAllByTitleAndAuthor(String title, String author) {
        List<Book> response = null;
        List<Book> books = bookRepository.findAllByName(title);
        //if we have both parameters
        if (title != null && title != "" && author != null && author != "") {
            List<Book> lstBooks;
            lstBooks = bookRepository.findAllByName(title);
            response = lstBooks
                .stream()
                .filter(book -> book.getWriter().getName().equalsIgnoreCase(author)).collect(Collectors.toList());
            return response;
        }

        //if we have the author but not the title
        if ((title == null || title == "") && (author != null && title != "")) {
            List<Book> lstBooks = bookRepository.findAll();
            response = lstBooks
                .stream()
                .filter(book -> book.getWriter().getName().equalsIgnoreCase(author)).collect(Collectors.toList());
            return response;
        }
        //if we have the title but not the author
        if ((author == null || author == "") && (title != null && title != "")) {
            response=bookRepository.findByNameContainingIgnoreCase(title);
            if (response.isEmpty()) {
                response=bookRepository.findAll();
            }
        }
        //no title and no author
        else {
            response = bookRepository.findAll();
        }

        return response;
    }

    public ResponseEntity<Book> createBook(Book book) throws URISyntaxException {
        if (book.getId() != null) {
            throw new BadRequestAlertException("A new book cannot already have an ID", "ID", "idexists");
        }
        if (book.getName()==null || book.getName()==""){
            throw new BadRequestAlertException("The Title is required",ENTITY_NAME,"title null");
        }
        if(book.getStock()<0 || book.getPrice()<0){
            throw new BadRequestAlertException("The stock cannot be negative neither the price", "Stock or Price", "stock or price negative");
        }
        Book result = bookRepository.save(book);
        return ResponseEntity.created(new URI("/api/books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    public ResponseEntity<Void> deleteAllByTitle(String title) {
        if (title == null || title == "") {
            bookRepository.deleteAll();
        } else {
            bookRepository.deleteAllByName(title);
        }
        return null;
    }


    public Optional<Book> getBookById(Long id){
        return bookRepository.findById(id);
    }
}
