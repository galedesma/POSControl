package com.galedesma.poscontrol.controller;

import com.galedesma.poscontrol.dto.in.PointOfSaleUpdateRequest;
import com.galedesma.poscontrol.dto.out.GetAllPOSResponse;
import com.galedesma.poscontrol.dto.out.PointOfSaleResponse;
import com.galedesma.poscontrol.service.PointOfSaleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pos")
@AllArgsConstructor
public class PointOfSaleController {

    private final PointOfSaleService service;

    @GetMapping
    public ResponseEntity<GetAllPOSResponse> getAllPos() {
        return ResponseEntity.ok(service.getAllPOS());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PointOfSaleResponse> getPosById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getPOSById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PointOfSaleResponse> updatePosById(@PathVariable Integer id, @RequestBody PointOfSaleUpdateRequest body) {
        return ResponseEntity.ok(service.updatePOS(id, body));
    }
}
