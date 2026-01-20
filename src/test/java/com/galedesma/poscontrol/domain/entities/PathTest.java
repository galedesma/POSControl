package com.galedesma.poscontrol.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathTest {

    @Test
    void pathCreatedSuccessfully() {
        Path path = new Path(0, 1, 10);

        Integer expectedOrigin = 0;
        Integer expectedDestination = 1;
        Integer expectedCost = 10;

        assertAll("Success assertions",
                () -> assertEquals(expectedOrigin, path.getOrigin()),
                () -> assertEquals(expectedDestination, path.getDestination()),
                () -> assertEquals(expectedCost, path.getCost())
        );
    }

    @Test
    void pathCreatedWithNullValue() {
        assertThrows(IllegalArgumentException.class, ()-> new Path(null, 1, 10));
    }

    @Test
    void pathCreatedWithNegativeValue() {
        assertThrows(IllegalArgumentException.class, ()-> new Path(0, -1, 10));
    }

    @Test
    void pathCreatedWithSameOriginAndDestination() {
        Path path = new Path(0, 0, 10);

        Integer expectedOrigin = 0;
        Integer expectedDestination = 0;
        Integer expectedCost = 0;

        assertAll("Should override cost",
                () -> assertEquals(expectedOrigin, path.getOrigin()),
                () -> assertEquals(expectedDestination, path.getDestination()),
                () -> assertEquals(expectedCost, path.getCost())
        );
    }
}