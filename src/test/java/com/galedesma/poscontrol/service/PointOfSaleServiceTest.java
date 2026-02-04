package com.galedesma.poscontrol.service;

import com.galedesma.poscontrol.entity.PointOfSale;
import com.galedesma.poscontrol.repository.PointOfSaleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.galedesma.poscontrol.util.TestUtils.createPersistedPOS;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointOfSaleServiceTest {

    @Mock
    PointOfSaleRepository repository;

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

        List<PointOfSale> results = service.getAllPOS();

        assertAll("Find all Point Of Sale assertions",
                () -> assertEquals(expectedSize, results.size()),
                () -> assertEquals(name1, results.getFirst().getName()),
                () -> assertEquals(name2, results.get(1).getName())

        );
    }
}