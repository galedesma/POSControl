package com.galedesma.poscontrol.dto.out;

import java.util.List;

public record GetAllPOSResponse(Integer count, List<PointOfSaleResponse> pointsOfSale) {
}
