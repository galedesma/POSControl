package com.galedesma.poscontrol.dto.out;

import java.io.Serializable;

public record ConnectedPOSResponse(Integer id, String name, Integer cost) implements Serializable {
}
