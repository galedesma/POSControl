package com.galedesma.poscontrol.dto.out;

import java.io.Serializable;
import java.util.List;

public record AllConnectedPathsResponse(Integer count, List<ConnectedPOSResponse> connectedPOS) implements Serializable {
}
