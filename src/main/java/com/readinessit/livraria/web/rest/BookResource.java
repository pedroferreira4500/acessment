package com.readinessit.livraria.web.rest;
import com.readinessit.livraria.domain.Book;
import com.readinessit.livraria.domain.InvalidRequestException;
import com.readinessit.livraria.repository.BookRepository;
import com.readinessit.livraria.security.SecurityUtils;
import com.readinessit.livraria.service.AuthorService;
import com.readinessit.livraria.service.BookService;
import com.readinessit.livraria.web.rest.errors.BadRequestAlertException;
import com.readinessit.livraria.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

import static com.readinessit.livraria.security.AuthoritiesConstants.ADMIN;

/**
 * REST controller for managing Book.
 */
@RestController
@RequestMapping("/api")
public class BookResource {

    private final Logger log = LoggerFactory.getLogger(BookResource.class);

    private static final String ENTITY_NAME = "book";

    private final BookRepository bookRepository;
    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    public BookResource(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * POST  /books : Create a new book.
     *
     * @param book the book to create
     * @return the ResponseEntity with status 201 (Created) and with body the new book, or with status 400 (Bad Request) if the book has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) throws URISyntaxException {

        if(!SecurityUtils.isCurrentUserInRole(ADMIN)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"Not_Authenthicated","You need to be logged in to perform this action")).body(null);
        }
        log.debug("REST request to save Book : {}", book);
        return bookService.createBook(book);
    }

    /**
     * PUT  /books : Updates an existing book.
     *
     * @param book the book to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated book,
     * or with status 400 (Bad Request) if the book is not valid,
     * or with status 500 (Internal Server Error) if the book couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/books")
    public ResponseEntity<Book> updateBook(@Valid @RequestBody Book book) throws URISyntaxException {
        log.debug("REST request to update Book : {}", book);
        if(!SecurityUtils.isCurrentUserInRole(ADMIN)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"Not_Authenthicated","You need to be logged in to perform this action")).body(null);
        }
        if (book.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Book result = bookRepository.save(book);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, book.getId().toString()))
            .body(result);
    }

    /**
     * GET  /books : get all the books.
     * GET  /books/{title}{author} : get books by title and author
     * GET  /books/{title} : get books by title
     * GET  /books/{author}: get books by author
     * @return the ResponseEntity with status 200 (OK) and the list of books in body
     */
    @GetMapping("/books")
    public List<Book> getAllBooks(
        @RequestParam(required = false, name = "title") String title,
        @RequestParam(required = false, name="author" ) String author
    ) {
        return bookService.findAllByTitleAndAuthor(title,author);
    }

    /**  /books/{id} get a book by id
     * @param id
     */
    @GetMapping("/books/{id}")
    public Optional<Book> getBookById(@PathVariable Long id){
        if(id<1){
            throw new BadRequestAlertException("The ID must be an integer larger than 0","ID","Bad ID Request");
        }
        return bookService.getBookById(id);
    }

    /**
     * Attempt to delete a book object
     *
     */
    @DeleteMapping("/books")
    public ResponseEntity<String> deleteBooks() throws InvalidRequestException {
            throw new BadRequestAlertException("Not possible to delete Book due to dependency issues", "Books","Dependency Issues");
        }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook() throws InvalidRequestException {
        throw new BadRequestAlertException("Not possible to delete Book due to dependency issues", "Books","Dependency Issues");
    }
}
