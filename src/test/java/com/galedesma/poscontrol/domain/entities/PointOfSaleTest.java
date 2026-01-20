package com.galedesma.poscontrol.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointOfSaleTest {

    @Test
    void pointOfSaleCreatedSuccessfully() {
        Integer id = 0;
        String name = "San Miguel";
        Integer expectedPathSize = 0;

        PointOfSale pos = new PointOfSale(id, name);

        assertAll("PointOfSale creation success assertions",
                () -> assertEquals(id, pos.getId()),
                () -> assertEquals(name, pos.getName()),
                () -> assertEquals(expectedPathSize, pos.getPaths().size())
        );
    }

    @Test
    void pointOfSaleWithInvalidId() {
        assertAll("PointOfSale invalid Id assertions",
                () -> assertThrows(IllegalArgumentException.class, () -> new PointOfSale(null, "foo")),
                () -> assertThrows(IllegalArgumentException.class, () -> new PointOfSale(-1, "foo"))
        );
    }

    @Test
    void pointOfSaleWithInvalidName() {
        assertAll("PointOfSale invalid name assertions",
                () -> assertThrows(IllegalArgumentException.class, () -> new PointOfSale(0, null)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PointOfSale(0, "    "))
        );
    }

    @Test
    void pointOfSaleAddsPathSuccessfully() {
        Integer id = 0;
        String name = "San Miguel";
        Integer neighborId = 1;
        Integer cost = 100;
        Integer expectedPathSize = 1;

        PointOfSale pos = new PointOfSale(id, name);
        pos.addPath(neighborId, cost);

        assertAll("PointOfSale Path addition success assertions",
                () -> assertEquals(id, pos.getId()),
                () -> assertEquals(name, pos.getName()),
                () -> assertEquals(expectedPathSize, pos.getPaths().size())
        );
    }

    @Test
    void pointOfSaleDeletesPathSuccessfully() {
        Integer id = 0;
        String name = "San Miguel";
        Integer neighborId1 = 1;
        Integer neighborId2 = 2;
        Integer neighborId3 = 3;
        Integer cost = 100;
        Integer expectedPathSize = 2;

        PointOfSale pos = new PointOfSale(id, name);
        pos.addPath(neighborId1, cost);
        pos.addPath(neighborId2, cost);
        pos.addPath(neighborId3, cost);
        pos.addPath(neighborId1, cost);
        pos.deletePath(neighborId1);

        assertAll("PointOfSale Path deletion success assertions",
                () -> assertEquals(id, pos.getId()),
                () -> assertEquals(name, pos.getName()),
                () -> assertEquals(expectedPathSize, pos.getPaths().size())
        );
    }
}