package com.galedesma.poscontrol.mapper;

import com.galedesma.poscontrol.dto.out.ConnectedPOSResponse;
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
    void toResponse() {
        Integer pathId = 1;

        Integer selfId = 1;
        String selfName = "bar";

        Integer neighborId = 2;
        String neighborName = "foo";

        Integer cost = 10;

        PointOfSale selfPOS = createPersistedPOS(selfId, selfName);
        PointOfSale neighborPOS = createPersistedPOS(neighborId, neighborName);

        Path path = createPersistedPath(pathId, selfPOS, neighborPOS, cost);

        ConnectedPOSResponse response = mapper.toResponse(path, neighborPOS);

        assertAll(
                "Path Mapper assertions",
                () -> assertEquals(cost, response.cost()),
                () -> assertEquals(neighborId, response.id()),
                () -> assertEquals(neighborName, response.name())
        );
    }
}