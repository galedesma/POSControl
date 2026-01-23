package com.galedesma.poscontrol.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    @Test
    void mapCreatedSuccessfully() {
        Map map = new Map();

        assertEquals(0, map.getPos().size());
    }

    @Test
    void addsPointOfSaleSuccessfully() {
        Map map = new Map();
        String posName = "foo";
        Integer expectedSize = 1;
        PointOfSale result = map.addPos("foo");

        assertAll("POS addition success",
                () -> assertEquals(expectedSize, map.getPos().size()),
                () -> assertEquals(posName, result.getName())
        );
    }

    @Test
    void addsPathBetweenTwoPOSSuccessfully() {
        Map map = new Map();
        String posName1 = "foo";
        String posName2 = "bar";
        Integer expectedSize = 2;
        Integer expectedPathSize = 1;
        Integer expectedPathCost = 10;

        PointOfSale result1 = map.addPos(posName1);
        PointOfSale result2 = map.addPos(posName2);

        map.addPath(result1.getId(), result2.getId(), expectedPathCost);

        assertAll("Path addition between two POS success",
                () -> assertEquals(expectedSize, map.getPos().size()),
                () -> assertEquals(posName1, result1.getName()),
                () -> assertEquals(posName2, result2.getName()),
                () -> assertEquals(result2.getId(), result1.getPaths().get(result2.getId()).getDestination()),
                () -> assertEquals(expectedPathSize, result1.getPaths().size()),
                () -> assertEquals(expectedPathCost, result1.getPaths().get(result2.getId()).getCost()),
                () -> assertEquals(result1.getId(), result2.getPaths().get(result1.getId()).getDestination()),
                () -> assertEquals(expectedPathSize, result2.getPaths().size()),
                () -> assertEquals(expectedPathCost, result2.getPaths().get(result1.getId()).getCost())
        );
    }

    @Test
    void addPathWithInvalidIndexes() {
        Map map = new Map();
        String posName1 = "foo";
        String posName2 = "bar";

        Integer expectedPathCost = 10;

        PointOfSale result1 = map.addPos(posName1);
        PointOfSale result2 = map.addPos(posName2);

        assertAll("Path addition between two POS with invalid indexes assertions",
                () -> assertThrows(IndexOutOfBoundsException.class, () -> map.addPath(-1, result2.getId(), expectedPathCost)),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> map.addPath(result1.getId(), 1000, expectedPathCost))
        );
    }

    @Test
    void removePathSuccessfully() {
        Map map = new Map();
        String posName1 = "foo";
        String posName2 = "bar";
        Integer expectedSize = 2;
        Integer expectedPathSize = 0;
        Integer expectedPathCost = 10;

        PointOfSale result1 = map.addPos(posName1);
        PointOfSale result2 = map.addPos(posName2);

        map.addPath(result1.getId(), result2.getId(), expectedPathCost);
        map.removePath(result1.getId(), result2.getId());

        assertAll("Path removal between two POS success",
                () -> assertEquals(expectedSize, map.getPos().size()),
                () -> assertEquals(posName1, result1.getName()),
                () -> assertEquals(posName2, result2.getName()),
                () -> assertNull(result1.getPaths().get(result2.getId())),
                () -> assertEquals(expectedPathSize, result1.getPaths().size()),
                () -> assertNull(result2.getPaths().get(result1.getId())),
                () -> assertEquals(expectedPathSize, result2.getPaths().size())
        );
    }

    @Test
    void removePathWithInvalidIndexes() {
        Map map = new Map();
        String posName1 = "foo";
        String posName2 = "bar";
        Integer expectedPathCost = 10;

        PointOfSale result1 = map.addPos(posName1);
        PointOfSale result2 = map.addPos(posName2);

        map.addPath(result1.getId(), result2.getId(), expectedPathCost);

        assertAll("Path removal between two POS with invalid indexes assertions",
                () -> assertThrows(IndexOutOfBoundsException.class, () -> map.removePath(-1, result2.getId())),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> map.removePath(result1.getId(), 1000))
        );
    }
}