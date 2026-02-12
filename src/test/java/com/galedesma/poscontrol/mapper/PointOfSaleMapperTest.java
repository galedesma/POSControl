package com.galedesma.poscontrol.mapper;

import com.galedesma.poscontrol.dto.in.PointOfSaleCreateRequest;
import com.galedesma.poscontrol.dto.out.PointOfSaleResponse;
import com.galedesma.poscontrol.entity.PointOfSale;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.galedesma.poscontrol.util.TestUtils.createPersistedPOS;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PointOfSaleMapperTest {

    PointOfSaleMapper mapper = Mappers.getMapper(PointOfSaleMapper.class);

    @Test
    void toResponse() {
        Integer id = 1;
        String name = "foo";

        PointOfSale persistedPOS = createPersistedPOS(id, name);

        PointOfSaleResponse result = mapper.toResponse(persistedPOS);

        assertAll("POS Entity to ResponseDTO",
                () -> assertEquals(id, result.id()),
                () -> assertEquals(name, result.name())
        );
    }

    @Test
    void toEntity() {
        String name = "foo";

        PointOfSaleCreateRequest request = new PointOfSaleCreateRequest(name);

        PointOfSale result = mapper.toEntity(request);

        assertEquals(name, result.getName());
    }
}