package com.galedesma.poscontrol.service;

import com.galedesma.poscontrol.dto.in.PathCreateRequest;
import com.galedesma.poscontrol.dto.out.AllConnectedPathsResponse;
import com.galedesma.poscontrol.dto.out.CreatePathResponse;
import com.galedesma.poscontrol.entity.Path;
import com.galedesma.poscontrol.entity.PointOfSale;
import com.galedesma.poscontrol.exception.PathAlreadyExistentException;
import com.galedesma.poscontrol.exception.PointOfSaleNotFoundException;
import com.galedesma.poscontrol.mapper.PathMapper;
import com.galedesma.poscontrol.repository.PathRepository;
import com.galedesma.poscontrol.repository.PointOfSaleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.galedesma.poscontrol.util.TestUtils.createPersistedPOS;
import static com.galedesma.poscontrol.util.TestUtils.createPersistedPath;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PathServiceTest {

    @Mock
    PathRepository pathRepository;

    @Mock
    PointOfSaleRepository posRepository;

    @Spy
    PathMapper mapper = Mappers.getMapper(PathMapper.class);

    @InjectMocks
    PathService service;

    @Test
    void getAllPathsTo() {
        String posName1 = "foo";
        String posName2 = "bar";
        Integer posId1 = 1;
        Integer posId2 = 2;

        Integer pathId = 1;
        Integer cost = 10;

        Integer expectedCount = 1;

        PointOfSale pos1 = createPersistedPOS(posId1, posName1);
        PointOfSale pos2 = createPersistedPOS(posId2, posName2);

        Path path = createPersistedPath(pathId, pos1, pos2, cost);

        when(pathRepository.findAllPathsConnectedTo(posId1)).thenReturn(List.of(path));

        AllConnectedPathsResponse allPathsToPOS1 = service.getAllPathsTo(posId1);

        assertAll(
                () -> assertEquals(expectedCount, allPathsToPOS1.count()),
                () -> assertEquals(pos2.getId(), allPathsToPOS1.connectedPOS().getFirst().id()),
                () -> assertEquals(pos2.getName(), allPathsToPOS1.connectedPOS().getFirst().name())
        );
    }


    @Test
    void createPathSuccess() {
        String pos1Name = "foo";
        String pos2Name = "bar";
        Integer pos1Id = 1;
        Integer pos2Id = 2;

        Integer pathId = 1;
        Integer cost = 10;

        PathCreateRequest request = new PathCreateRequest(pos1Id, pos2Id, cost);

        PointOfSale pos1 = createPersistedPOS(pos1Id, pos1Name);
        PointOfSale pos2 = createPersistedPOS(pos2Id, pos2Name);

        Path path = createPersistedPath(pathId, pos1, pos2, cost);

        when(pathRepository.findPathBetween(pos1Id, pos2Id)).thenReturn(Optional.empty());
        when(posRepository.findById(pos1Id)).thenReturn(Optional.of(pos1));
        when(posRepository.findById(pos2Id)).thenReturn(Optional.of(pos2));
        when(pathRepository.save(any(Path.class))).thenReturn(path);

        CreatePathResponse result = service.createPath(request);

        assertAll(
                () -> assertEquals(cost, result.cost()),
                () -> assertEquals(pos1Id, result.pos1().id()),
                () -> assertEquals(pos1Name, result.pos1().name()),
                () -> assertEquals(pos2Id, result.pos2().id()),
                () -> assertEquals(pos2Name, result.pos2().name())
        );
    }

    @Test
    void createPathAlreadyExistent() {
        String pos1Name = "foo";
        String pos2Name = "bar";
        Integer pos1Id = 1;
        Integer pos2Id = 2;

        Integer pathId = 1;
        Integer cost = 10;

        PathCreateRequest request = new PathCreateRequest(pos1Id, pos2Id, cost);

        PointOfSale pos1 = createPersistedPOS(pos1Id, pos1Name);
        PointOfSale pos2 = createPersistedPOS(pos2Id, pos2Name);

        Path path = createPersistedPath(pathId, pos1, pos2, cost);

        when(pathRepository.findPathBetween(pos1Id, pos2Id)).thenReturn(Optional.of(path));

        assertThrows(PathAlreadyExistentException.class, () -> service.createPath(request));
    }

    @Test
    void createPathPOSNotFound() {
        Integer pos1Id = 1;
        Integer pos2Id = 2;

        Integer cost = 10;

        PathCreateRequest request = new PathCreateRequest(pos1Id, pos2Id, cost);

        when(pathRepository.findPathBetween(pos1Id, pos2Id)).thenReturn(Optional.empty());

        assertThrows(PointOfSaleNotFoundException.class, () -> service.createPath(request));
    }
}