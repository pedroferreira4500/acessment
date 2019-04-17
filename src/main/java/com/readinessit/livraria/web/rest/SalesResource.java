package com.readinessit.livraria.web.rest;
import com.readinessit.livraria.domain.Sales;
import com.readinessit.livraria.repository.SalesRepository;
import com.readinessit.livraria.service.SalesService;
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

/**
 * REST controller for managing Sales.
 */
@RestController
@RequestMapping("/api")
public class SalesResource {

    private final Logger log = LoggerFactory.getLogger(SalesResource.class);

    private static final String ENTITY_NAME = "sales";

    private final SalesRepository salesRepository;

    @Autowired
    private SalesService salesService;


    public SalesResource(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    /**
     * POST  /sales : Create a new sales.
     *
     * @param sales the sales to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sales, or with status 400 (Bad Request) if the sales has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sales")
    public ResponseEntity<Sales> createSales(@Valid @RequestBody Sales sales) throws URISyntaxException {
        log.debug("REST request to save Sales : {}", sales);
        if (sales.getId() != null) {
            throw new BadRequestAlertException("A new sales cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sales result = salesRepository.save(sales);
        return ResponseEntity.created(new URI("/api/sales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sales : Updates an existing sales.
     *
     * @param sales the sales to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sales,
     * or with status 400 (Bad Request) if the sales is not valid,
     * or with status 500 (Internal Server Error) if the sales couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sales")
    public ResponseEntity<Sales> updateSales(@Valid @RequestBody Sales sales) throws URISyntaxException {
        log.debug("REST request to update Sales : {}", sales);
        if (sales.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sales result = salesRepository.save(sales);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sales.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sales : get all the sales.
     * GET /sales/{client}
     * GET /sales/{book}
     * @return the ResponseEntity with status 200 (OK) and the list of sales in body
     */
    @GetMapping("/sales")
    public List<Sales> getAllSales(
        @RequestParam(required = false, name = "client") Long client,
        @RequestParam(required = false, name = "book") Long book)
    {
        return salesService.getAllByClientIdAndBookId(client,book);
    }

    /**
     * GET  /sales/:id : get the "id" sales.
     *
     * @param id the id of the sales to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sales, or with status 404 (Not Found)
     */
    @GetMapping("/sales/{id}")
    public ResponseEntity<Sales> getSales(@PathVariable Long id) {
        log.debug("REST request to get Sales : {}", id);
        Optional<Sales> sales = salesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sales);
    }



    /**
     * DELETE  /sales/:id : delete the "id" sales.
     *
     * @param id the id of the sales to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sales/{id}")
    public ResponseEntity<Void> deleteSales(@PathVariable Long id) {
        log.debug("REST request to delete Sales : {}", id);
        salesRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
