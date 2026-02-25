package com.galedesma.poscontrol.mapper;

import com.galedesma.poscontrol.dto.out.ConnectedPOSResponse;
import com.galedesma.poscontrol.dto.out.CreatePathResponse;
import com.galedesma.poscontrol.entity.Path;
import com.galedesma.poscontrol.entity.PointOfSale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PathMapper {

    @Mapping(target = "id", source = "pointOfSale.id")
    @Mapping(target = "name", source = "pointOfSale.name")
    @Mapping(target = "cost", source = "path.cost")
    ConnectedPOSResponse toConnectedPOSResponse(Path path, PointOfSale pointOfSale);

    Path toEntity(PointOfSale pos1, PointOfSale pos2, Integer cost);

    CreatePathResponse toCreatePathResponse(Path path);
}
