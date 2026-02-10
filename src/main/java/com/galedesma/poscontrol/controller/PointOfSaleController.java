package com.galedesma.poscontrol.controller;

import com.galedesma.poscontrol.dto.out.GetAllPOSResponse;
import com.galedesma.poscontrol.dto.out.PointOfSaleResponse;
import com.galedesma.poscontrol.service.PointOfSaleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pos")
@AllArgsConstructor
public class PointOfSaleController {

    private final PointOfSaleService service;

    @GetMapping
    public ResponseEntity<GetAllPOSResponse> getAllPos(){
        return ResponseEntity.ok(service.getAllPOS());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PointOfSaleResponse> getPosById(@PathVariable Integer id){
        return ResponseEntity.ok(service.getPOSById(id));
    }
}
