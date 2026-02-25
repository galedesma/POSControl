package com.galedesma.poscontrol.repository;

import com.galedesma.poscontrol.entity.Path;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/test-data.sql")
@ActiveProfiles("test")
class PathRepositoryTest {

    @Autowired
    PathRepository repository;

    @Test
    void findAllPathsConnectedTo() {
        // POS 1 está conectado a 2 (costo 2), 3 (costo 3) y 4 (costo 11)
        Integer expectedSize = 3;
        Integer neighbor1Cost = 2;
        Integer neighbor2Cost = 3;
        Integer neighbor3Cost = 11;
        Integer posId = 1;
        List<Path> result = repository.findAllPathsConnectedTo(posId);

        assertAll(
                () -> assertEquals(expectedSize, result.size()),
                () -> assertEquals(neighbor1Cost, result.getFirst().getCost()),
                () -> assertEquals(neighbor2Cost, result.get(1).getCost()),
                () -> assertEquals(neighbor3Cost, result.getLast().getCost())
        );
    }

    @Test
    void findPathBetweenSuccess() {
        Integer pos1Id = 1;
        Integer pos2Id = 2;
        Integer expectedCost = 2;

        Optional<Path> result = repository.findPathBetween(pos1Id, pos2Id);

        assertAll(
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(expectedCost, result.get().getCost())
        );
    }

    @Test
    void findPathBetweenFailure() {
        Integer pos1Id = 1;
        Integer pos2Id = 10;

        Optional<Path> result = repository.findPathBetween(pos1Id, pos2Id);

        assertTrue(result.isEmpty());
    }
}