package com.readinessit.livraria.service;

import com.readinessit.livraria.domain.Basket;
import com.readinessit.livraria.domain.Client;
import com.readinessit.livraria.repository.AuthorRepository;
import com.readinessit.livraria.repository.BasketRepository;
import com.readinessit.livraria.repository.BookRepository;
import com.readinessit.livraria.repository.ClientRepository;
import com.readinessit.livraria.web.rest.errors.BadRequestAlertException;
import com.readinessit.livraria.web.rest.util.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@Service
public class BasketService {

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ClientRepository clientRepository;

    public List<Basket> getAllBaskets(Long client) {
        List<Basket> baskets=basketRepository.findAll();
        List<Basket> response=null;
    if(client==null){
        return baskets;
    }
        response = baskets
            .stream()
            .filter(basket -> basket.getClient().getId().equals(client)).collect(Collectors.toList());
    return response;
    }

    public ResponseEntity<Basket> createBasket(Basket basket) throws URISyntaxException {
        if (basket.getId() != null) {
            throw new BadRequestAlertException("A new basket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (bookRepository.findById(basket.getBook().getId()).isPresent() && clientRepository.findById(basket.getClient().getId()).isPresent()) {
            Basket result = basketRepository.save(basket);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, basket.getId().toString()))
                .body(result);

        }else {
            throw new BadRequestAlertException("Invalid Client or Book Id", ENTITY_NAME, "Bad Client or Book Id");
        }
    }

    public ResponseEntity<Basket> updateBasket(Basket basket) {
        if (basket.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Basket result = basketRepository.save(basket);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, basket.getId().toString()))
            .body(result);

    }

}


