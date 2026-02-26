package com.galedesma.poscontrol.service;

import com.galedesma.poscontrol.dto.in.PathCreateRequest;
import com.galedesma.poscontrol.dto.out.AllConnectedPathsResponse;
import com.galedesma.poscontrol.dto.out.ConnectedPOSResponse;
import com.galedesma.poscontrol.dto.out.CreatePathResponse;
import com.galedesma.poscontrol.entity.Path;
import com.galedesma.poscontrol.entity.PointOfSale;
import com.galedesma.poscontrol.exception.PathAlreadyExistentException;
import com.galedesma.poscontrol.exception.PointOfSaleNotFoundException;
import com.galedesma.poscontrol.mapper.PathMapper;
import com.galedesma.poscontrol.repository.PathRepository;
import com.galedesma.poscontrol.repository.PointOfSaleRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PathService {

    private final PathRepository pathRepository;
    private final PointOfSaleRepository posRepository;
    private final PathMapper mapper;

    @Cacheable(value = "paths_connected_to", key = "#posId")
    public AllConnectedPathsResponse getAllPathsTo(Integer posId) {
        List<Path> allPathsConnected = pathRepository.findAllPathsConnectedTo(posId);

        List<ConnectedPOSResponse> connectedPOSResponseList = allPathsConnected.stream()
                .map(path -> {
                    PointOfSale neighbor = path.getPos1().getId().equals(posId) ? path.getPos2() : path.getPos1();
                    return mapper.toConnectedPOSResponse(path, neighbor);
                })
                .toList();
        return new AllConnectedPathsResponse(connectedPOSResponseList.size(), connectedPOSResponseList);
    }

    public CreatePathResponse createPath(PathCreateRequest request) {
        Optional<Path> pathOptional = pathRepository.findPathBetween(request.pos1Id(), request.pos2Id());

        if (pathOptional.isPresent()) {
            throw new PathAlreadyExistentException(String.format("Path between POS with ID's %d and %d already exist", request.pos1Id(), request.pos2Id()));
        }

        PointOfSale pos1 = posRepository.findById(request.pos1Id()).orElseThrow(() -> new PointOfSaleNotFoundException(String.format("Point of Sale with ID %d not found", request.pos1Id())));
        PointOfSale pos2 = posRepository.findById(request.pos2Id()).orElseThrow(() -> new PointOfSaleNotFoundException(String.format("Point of Sale with ID %d not found", request.pos2Id())));

        Path savedPath = pathRepository.save(mapper.toEntity(pos1, pos2, request.cost()));

        return mapper.toCreatePathResponse(savedPath);
    }
}
