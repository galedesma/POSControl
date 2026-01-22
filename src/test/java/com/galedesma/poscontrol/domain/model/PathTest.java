package com.galedesma.poscontrol.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathTest {

    @Test
    void pathCreatedSuccessfully() {
        Integer origin = 0;
        Integer destination = 1;
        Integer cost = 10;

        Path path = new Path(origin, destination, cost);

        assertAll("Path creation success assertions",
                () -> assertEquals(origin, path.getOrigin()),
                () -> assertEquals(destination, path.getDestination()),
                () -> assertEquals(cost, path.getCost())
        );
    }

    @Test
    void pathCreatedWithInvalidValue() {
        assertAll("Path creation with invalid values assertions",
                () -> assertThrows(IllegalArgumentException.class, ()-> new Path(null, 1, 10)),
                () -> assertThrows(IllegalArgumentException.class, ()-> new Path(0, -1, 10))
        );
    }

    @Test
    void pathCreatedWithSameOriginAndDestination() {
        Integer origin = 0;
        Integer destination = 0;
        Integer cost = 10;
        Integer expectedCost = 0;

        Path path = new Path(origin, destination, cost);

        assertAll("Path created with same origin and destination assertions",
                () -> assertEquals(origin, path.getOrigin()),
                () -> assertEquals(destination, path.getDestination()),
                () -> assertEquals(expectedCost, path.getCost())
        );
    }
}