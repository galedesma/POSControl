package com.galedesma.poscontrol.dto.out;

public record CreatePathResponse(Integer id, PointOfSaleResponse pos1, PointOfSaleResponse pos2, Integer cost) {
}
