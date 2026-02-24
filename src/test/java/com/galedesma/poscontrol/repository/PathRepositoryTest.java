package com.galedesma.poscontrol.repository;

import com.galedesma.poscontrol.entity.Path;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

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
        Integer expectedSize = 3;
        Integer posId = 1;
        List<Path> result = repository.findAllPathsConnectedTo(posId);

        assertAll("All paths that connect to POS 1 assertions",
                () -> assertEquals(expectedSize, result.size())
        );
    }
}