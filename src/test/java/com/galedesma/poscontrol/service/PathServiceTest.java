package com.galedesma.poscontrol.service;

import com.galedesma.poscontrol.dto.out.AllConnectedPathsResponse;
import com.galedesma.poscontrol.entity.Path;
import com.galedesma.poscontrol.entity.PointOfSale;
import com.galedesma.poscontrol.mapper.PathMapper;
import com.galedesma.poscontrol.repository.PathRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.galedesma.poscontrol.util.TestUtils.createPersistedPOS;
import static com.galedesma.poscontrol.util.TestUtils.createPersistedPath;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PathServiceTest {

    @Mock
    PathRepository repository;

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

        when(repository.findAllPathsConnectedTo(posId1)).thenReturn(List.of(path));

        AllConnectedPathsResponse allPathsToPOS1 = service.getAllPathsTo(posId1);

        assertAll(
                () -> assertEquals(expectedCount, allPathsToPOS1.count()),
                () -> assertEquals(pos2.getId(), allPathsToPOS1.connectedPOS().getFirst().id()),
                () -> assertEquals(pos2.getName(), allPathsToPOS1.connectedPOS().getFirst().name())
        );
    }
}