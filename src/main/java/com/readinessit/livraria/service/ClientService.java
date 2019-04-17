package com.readinessit.livraria.service;

import com.readinessit.livraria.domain.Client;
import com.readinessit.livraria.repository.ClientRepository;
import com.readinessit.livraria.web.rest.errors.BadRequestAlertException;
import com.readinessit.livraria.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    /**
     * GET Client By ID
     * @Param ID
     */
    public ResponseEntity<Client> getClient(Long id){
        Optional<Client> client = clientRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(client);
    }

    public List<Client> getClients(String email){
        if(email==null){
            return clientRepository.findAll();
        }
        List<Client> clients=clientRepository.getByEmail(email);
        if (clients.isEmpty()){
            throw new BadRequestAlertException("The email you searched for is not registered",ENTITY_NAME,"Email not present");
        }
        return clients;
    }

    public ResponseEntity<Client> createClient(Client client) throws URISyntaxException {
        if (client.getId() != null) {
            throw new BadRequestAlertException("A new client cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(client.getEmail()==null || client.getName()==null || client.getPassword()==null){
            throw new BadRequestAlertException("Name Email and password are required",ENTITY_NAME,"Required Fields");
        }
        if(client.getPassword().length()<5){
            throw new BadRequestAlertException("The password must be at least 5 caracters long",ENTITY_NAME,"Bad password");
        }
        if (isEmailRepeated(client.getEmail())==true){
            throw new BadRequestAlertException("Email is already in use",ENTITY_NAME,"Bad Email");
        }
        Client result = clientRepository.save(client);
        return ResponseEntity.created(new URI("/api/clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    public ResponseEntity<Client> updateClient(Client client){
        if (client.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (client.getId()<0){
            throw new BadRequestAlertException("Id must be a positive integer",ENTITY_NAME,"negative id");
        }
        if(isEmailRepeated(client.getEmail())==true){
            Long novoCliente=client.getId();
            Long antigoCliente=clientRepository.getByEmail(client.getEmail()).get(0).getId();
            if(novoCliente!=antigoCliente){
            throw new BadRequestAlertException("Email already in use",ENTITY_NAME,"repeated email");
        }}
        if(client.getPassword().length()<5){
            throw new BadRequestAlertException("The password must be at least 5 caracters long",ENTITY_NAME,"Bad password");
        }
        Client result = clientRepository.save(client);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, client.getId().toString()))
            .body(result);
    }

    //Check if email is already registered
    public boolean isEmailRepeated(String email){
        boolean notRepeatedEmail=true;
        notRepeatedEmail=clientRepository.getByEmail(email).isEmpty();
        if (notRepeatedEmail==false){//se o email for repetido
            return true;
        }
        return false;

    }

}
