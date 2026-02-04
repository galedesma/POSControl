package com.galedesma.poscontrol.mapper;

import com.galedesma.poscontrol.dto.out.PointOfSaleResponse;
import com.galedesma.poscontrol.entity.PointOfSale;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PointOfSaleMapper {

    PointOfSaleResponse toResponse(PointOfSale entity);
}
