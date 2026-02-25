package com.galedesma.poscontrol.controller;

import com.galedesma.poscontrol.dto.out.AllConnectedPathsResponse;
import com.galedesma.poscontrol.service.PathService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/path")
@AllArgsConstructor
public class PathController {

    private final PathService pathService;

    @GetMapping("/all/{id}")
    public ResponseEntity<AllConnectedPathsResponse> getAllConnectedPathsById(@PathVariable Integer id) {
        return ResponseEntity.ok(pathService.getAllPathsTo(id));
    }
}