package com.galedesma.poscontrol.service;

import com.galedesma.poscontrol.entity.PointOfSale;
import com.galedesma.poscontrol.repository.PointOfSaleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PointOfSaleService {

    private final PointOfSaleRepository repository;

    public List<PointOfSale> getAllPOS(){
        return this.repository.findAll();
    }
}
