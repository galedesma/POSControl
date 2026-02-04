package com.galedesma.poscontrol.controller;

import com.galedesma.poscontrol.dto.out.GetAllPOSResponse;
import com.galedesma.poscontrol.dto.out.PointOfSaleResponse;
import com.galedesma.poscontrol.entity.PointOfSale;
import com.galedesma.poscontrol.mapper.PointOfSaleMapper;
import com.galedesma.poscontrol.service.PointOfSaleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pos")
@AllArgsConstructor
public class PointOfSaleController {

    private final PointOfSaleService service;
    private final PointOfSaleMapper mapper;

    @GetMapping
    public ResponseEntity<GetAllPOSResponse> getAllPos(){
        List<PointOfSale> allPOS = service.getAllPOS();
        List<PointOfSaleResponse> list = allPOS.stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(new GetAllPOSResponse(list.size(), list));
    }
}
