package com.readinessit.livraria.service;

import com.readinessit.livraria.domain.Book;
import com.readinessit.livraria.domain.Sales;
import com.readinessit.livraria.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesService {
Sales sale=new Sales();
    @Autowired
    private SalesRepository salesRepository;

    public List<Sales> getAllByClientIdAndBookId(Long client, Long book){
        //search by client and book
        if(client!=null && book!=null ){
            List<Sales> result = null;

            List<Sales> lstSales = salesRepository.getAllByBookId(book);

            result = lstSales
                .stream()
                .filter(sale->sale.getClient().getId().equals(client)).collect(Collectors.toList());
            return result;
        }
        //search only by book
        if(client==null && book!=null){
            return salesRepository.getAllByBookId(book);
        }
        //search only by client
        if(client!=null){
            return salesRepository.getAllByClientId(client);
        }
        //get all
        else {
            return salesRepository.findAll();
        }
    }
}
