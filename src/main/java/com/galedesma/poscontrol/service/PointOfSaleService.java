package com.galedesma.poscontrol.service;

import com.galedesma.poscontrol.dto.out.GetAllPOSResponse;
import com.galedesma.poscontrol.dto.out.PointOfSaleResponse;
import com.galedesma.poscontrol.entity.PointOfSale;
import com.galedesma.poscontrol.mapper.PointOfSaleMapper;
import com.galedesma.poscontrol.repository.PointOfSaleRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PointOfSaleService {

    private final PointOfSaleRepository repository;
    private final PointOfSaleMapper mapper;

    @Cacheable(value = "points_of_sale")
    public GetAllPOSResponse getAllPOS(){
        List<PointOfSale> pointOfSaleList = this.repository.findAll();
        List<PointOfSaleResponse> responseList = pointOfSaleList.stream().map(mapper::toResponse).toList();
        return new GetAllPOSResponse(responseList.size(), responseList);
    }
}
