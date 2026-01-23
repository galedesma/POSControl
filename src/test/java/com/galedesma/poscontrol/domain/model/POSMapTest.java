package com.galedesma.poscontrol.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class POSMapTest {

    @Test
    void mapCreatedSuccessfully() {
        POSMap POSMap = new POSMap();

        assertEquals(0, POSMap.getPos().size());
    }

    @Test
    void addsPointOfSaleSuccessfully() {
        POSMap POSMap = new POSMap();
        Integer posId = 1;
        String posName = "foo";
        Integer expectedSize = 1;
        PointOfSale result = POSMap.addPos(posId, posName);

        assertAll("POS addition success",
                () -> assertEquals(expectedSize, POSMap.getPos().size()),
                () -> assertEquals(posId, result.getId()),
                () -> assertEquals(posName, result.getName())
        );
    }

    @Test
    void addsPathBetweenTwoPOSSuccessfully() {
        POSMap POSMap = new POSMap();
        Integer posId1 = 1;
        String posName1 = "foo";
        Integer posId2 = 2;
        String posName2 = "bar";
        Integer expectedSize = 2;
        Integer expectedPathSize = 1;
        Integer expectedPathCost = 10;

        PointOfSale result1 = POSMap.addPos(posId1, posName1);
        PointOfSale result2 = POSMap.addPos(posId2, posName2);

        POSMap.addPath(result1.getId(), result2.getId(), expectedPathCost);

        assertAll("Path addition between two POS success",
                () -> assertEquals(expectedSize, POSMap.getPos().size()),
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
        POSMap POSMap = new POSMap();
        Integer posId1 = 1;
        String posName1 = "foo";
        Integer posId2 = 2;
        String posName2 = "bar";

        Integer expectedPathCost = 10;

        PointOfSale result1 = POSMap.addPos(posId1, posName1);
        PointOfSale result2 = POSMap.addPos(posId2, posName2);

        assertAll("Path addition between two POS with invalid indexes assertions",
                () -> assertThrows(IndexOutOfBoundsException.class, () -> POSMap.addPath(-1, result2.getId(), expectedPathCost)),
                () -> assertThrows(NullPointerException.class, () -> POSMap.addPath(result1.getId(), 1000, expectedPathCost))
        );
    }

    @Test
    void removePathSuccessfully() {
        POSMap POSMap = new POSMap();
        Integer posId1 = 1;
        String posName1 = "foo";
        Integer posId2 = 2;
        String posName2 = "bar";
        Integer expectedSize = 2;
        Integer expectedPathSize = 0;
        Integer expectedPathCost = 10;

        PointOfSale result1 = POSMap.addPos(posId1, posName1);
        PointOfSale result2 = POSMap.addPos(posId2, posName2);

        POSMap.addPath(result1.getId(), result2.getId(), expectedPathCost);
        POSMap.removePath(result1.getId(), result2.getId());

        assertAll("Path removal between two POS success",
                () -> assertEquals(expectedSize, POSMap.getPos().size()),
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
        POSMap POSMap = new POSMap();
        Integer posId1 = 1;
        String posName1 = "foo";
        Integer posId2 = 2;
        String posName2 = "bar";
        Integer expectedPathCost = 10;

        PointOfSale result1 = POSMap.addPos(posId1, posName1);
        PointOfSale result2 = POSMap.addPos(posId2, posName2);

        POSMap.addPath(result1.getId(), result2.getId(), expectedPathCost);

        assertAll("Path removal between two POS with invalid indexes assertions",
                () -> assertThrows(IndexOutOfBoundsException.class, () -> POSMap.removePath(-1, result2.getId())),
                () -> assertThrows(NullPointerException.class, () -> POSMap.removePath(result1.getId(), 1000))
        );
    }
}