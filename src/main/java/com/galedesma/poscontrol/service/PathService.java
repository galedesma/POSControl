package com.galedesma.poscontrol.service;

import com.galedesma.poscontrol.dto.out.AllConnectedPathsResponse;
import com.galedesma.poscontrol.dto.out.ConnectedPOSResponse;
import com.galedesma.poscontrol.entity.Path;
import com.galedesma.poscontrol.entity.PointOfSale;
import com.galedesma.poscontrol.mapper.PathMapper;
import com.galedesma.poscontrol.repository.PathRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PathService {

    private final PathRepository repository;
    private final PathMapper mapper;

    public AllConnectedPathsResponse getAllPathsTo(Integer posId){
        List<Path> allPathsConnected = repository.findAllPathsConnectedTo(posId);

        List<ConnectedPOSResponse> connectedPOSResponseList = allPathsConnected.stream()
                .map(path -> {
                    PointOfSale neighbor = path.getPos1().getId().equals(posId) ? path.getPos2() : path.getPos1();
                    return mapper.toResponse(path, neighbor);
                })
                .toList();
        return new AllConnectedPathsResponse(connectedPOSResponseList.size(), connectedPOSResponseList);
    }
}
