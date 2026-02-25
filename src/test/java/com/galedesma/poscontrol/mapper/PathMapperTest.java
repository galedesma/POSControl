package com.galedesma.poscontrol.mapper;

import com.galedesma.poscontrol.dto.out.ConnectedPOSResponse;
import com.galedesma.poscontrol.dto.out.CreatePathResponse;
import com.galedesma.poscontrol.entity.Path;
import com.galedesma.poscontrol.entity.PointOfSale;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.galedesma.poscontrol.util.TestUtils.createPersistedPOS;
import static com.galedesma.poscontrol.util.TestUtils.createPersistedPath;
import static org.junit.jupiter.api.Assertions.*;

class PathMapperTest {

    PathMapper mapper = Mappers.getMapper(PathMapper.class);

    @Test
    void toConnectedPOSResponse() {
        Integer pathId = 1;

        Integer selfId = 1;
        String selfName = "bar";

        Integer neighborId = 2;
        String neighborName = "foo";

        Integer cost = 10;

        PointOfSale selfPOS = createPersistedPOS(selfId, selfName);
        PointOfSale neighborPOS = createPersistedPOS(neighborId, neighborName);

        Path path = createPersistedPath(pathId, selfPOS, neighborPOS, cost);

        ConnectedPOSResponse response = mapper.toConnectedPOSResponse(path, neighborPOS);

        assertAll(
                () -> assertEquals(cost, response.cost()),
                () -> assertEquals(neighborId, response.id()),
                () -> assertEquals(neighborName, response.name())
        );
    }

    @Test
    void toEntity() {
        Integer selfId = 1;
        String selfName = "bar";

        Integer neighborId = 2;
        String neighborName = "foo";

        Integer cost = 10;

        PointOfSale selfPOS = createPersistedPOS(selfId, selfName);
        PointOfSale neighborPOS = createPersistedPOS(neighborId, neighborName);

        Path result = mapper.toEntity(selfPOS, neighborPOS, cost);

        assertAll(
                () -> assertEquals(selfPOS, result.getPos1()),
                () -> assertEquals(neighborPOS, result.getPos2()),
                () -> assertEquals(cost, result.getCost())
        );
    }

    @Test
    void toCreatePathResponse() {
        Integer pathId = 1;

        Integer selfId = 1;
        String selfName = "bar";

        Integer neighborId = 2;
        String neighborName = "foo";

        Integer cost = 10;

        PointOfSale selfPOS = createPersistedPOS(selfId, selfName);
        PointOfSale neighborPOS = createPersistedPOS(neighborId, neighborName);

        Path path = createPersistedPath(pathId, selfPOS, neighborPOS, cost);

        CreatePathResponse result = mapper.toCreatePathResponse(path);

        assertAll(
                () -> assertEquals(pathId, result.id()),
                () -> assertEquals(selfId, result.pos1().id()),
                () -> assertEquals(selfName, result.pos1().name()),
                () -> assertEquals(neighborId, result.pos2().id()),
                () -> assertEquals(neighborName, result.pos2().name()),
                () -> assertEquals(cost, result.cost())
        );
    }
}