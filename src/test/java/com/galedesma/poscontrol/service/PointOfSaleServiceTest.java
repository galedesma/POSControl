package com.galedesma.poscontrol.service;

import com.galedesma.poscontrol.dto.out.GetAllPOSResponse;
import com.galedesma.poscontrol.dto.out.PointOfSaleResponse;
import com.galedesma.poscontrol.entity.PointOfSale;
import com.galedesma.poscontrol.exception.PointOfSaleNotFoundException;
import com.galedesma.poscontrol.mapper.PointOfSaleMapper;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointOfSaleServiceTest {

    @Mock
    PointOfSaleRepository repository;

    @Spy
    PointOfSaleMapper mapper = Mappers.getMapper(PointOfSaleMapper.class);

    @InjectMocks
    PointOfSaleService service;

    @Test
    void getAllPOS() {
        String name1 = "foo";
        String name2 = "bar";
        Integer id1 = 1;
        Integer id2 = 2;
        Integer expectedSize = 2;

        PointOfSale pos1 = createPersistedPOS(id1, name1);
        PointOfSale pos2 = createPersistedPOS(id2, name2);

        when(repository.findAll()).thenReturn(List.of(pos1, pos2));

        GetAllPOSResponse results = service.getAllPOS();

        assertAll("Find all Point Of Sale assertions",
                () -> assertEquals(expectedSize, results.count()),
                () -> assertEquals(name1, results.pointsOfSale().getFirst().name()),
                () -> assertEquals(name2, results.pointsOfSale().get(1).name())

        );
    }

    @Test
    void getPOSByIdSuccessfully() {
        Integer id = 1;
        String name = "foo";

        PointOfSale pos = createPersistedPOS(id, name);

        when(repository.findById(id)).thenReturn(Optional.of(pos));

        PointOfSaleResponse result = service.getPOSById(id);

        assertAll("Find Point of Sale by Id success",
                () -> assertEquals(id, result.id()),
                () -> assertEquals(name, result.name())
        );
    }

    @Test
    void getPOSByIdFails() {
        Integer id = 10000;

        assertThrows(PointOfSaleNotFoundException.class, () -> service.getPOSById(id));
    }
}