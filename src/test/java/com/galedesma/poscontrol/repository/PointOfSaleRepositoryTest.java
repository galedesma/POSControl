package com.galedesma.poscontrol.repository;

import com.galedesma.poscontrol.entity.PointOfSale;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@DataJpaTest
class PointOfSaleRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18.1-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withCopyFileToContainer(
                    MountableFile.forClasspathResource("init_db.sql"),
                    "/docker-entrypoint-initdb.d/"
            );

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private PointOfSaleRepository repository;

    @Test
    //TODO: Resolver >Hibernate: insert into point_of_sale (name) values (?)
    void testPointOfSaleSave() {
        String name1 = "foo";
        String name2 = "bar";
        Integer id1 = 1;
        Integer id2 = 2;

        PointOfSale pos1 = new PointOfSale();
        pos1.setName(name1);

        PointOfSale pos2 = new PointOfSale();
        pos2.setName(name2);

        PointOfSale result1 = repository.save(pos1);
        PointOfSale result2 = repository.save(pos2);

        assertAll("Save Success assertions",
                () -> assertEquals(id1, result1.getId()),
                () -> assertEquals(name1, result1.getName()),
                () -> assertEquals(id2, result2.getId()),
                () -> assertEquals(name2, result2.getName())
        );
    }
}