package com.galedesma.poscontrol.dto.out;

import java.io.Serializable;

public record PointOfSaleResponse(Integer id, String name) implements Serializable {
}
