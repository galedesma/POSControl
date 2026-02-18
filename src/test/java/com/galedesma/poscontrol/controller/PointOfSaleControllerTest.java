package com.galedesma.poscontrol.controller;

import com.galedesma.poscontrol.configuration.RedisTestConfig;
import com.galedesma.poscontrol.dto.in.PointOfSaleCreateRequest;
import com.galedesma.poscontrol.dto.in.PointOfSaleUpdateRequest;
import com.galedesma.poscontrol.repository.PointOfSaleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Import(RedisTestConfig.class)
class PointOfSaleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoSpyBean
    private PointOfSaleRepository repository;

    ObjectMapper mapper = new ObjectMapper();

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18.1-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withCopyFileToContainer(
                    MountableFile.forClasspathResource("init_db.sql"),
                    "/docker-entrypoint-initdb.d/"
            );

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:8.4-alpine")
            .withExposedPorts(6379)
            .waitingFor(Wait.forListeningPort());

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
    }

    @Test
    void getAllPosIs200Empty() throws Exception {
        Integer expectedSize = 0;

        mockMvc.perform(get("/pos"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.count").value(expectedSize),
                        jsonPath("$.pointsOfSale").isArray()
                );
    }

    @Test
    void getAllPOSIs200CallsCache() throws Exception {
        String name = "foo";
        PointOfSaleCreateRequest request = new PointOfSaleCreateRequest(name);
        String json = mapper.writeValueAsString(request);
        Integer expectedSize = 1;

        mockMvc.perform(post("/pos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isCreated(),
                        header().exists("Location")
                );

        Thread.sleep(Duration.ofSeconds(10L));

        mockMvc.perform(get("/pos"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.count").value(expectedSize),
                        jsonPath("$.pointsOfSale").isArray()
                );

        verify(repository, times(1)).findAll();

        mockMvc.perform(get("/pos"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.count").value(expectedSize),
                        jsonPath("$.pointsOfSale").isArray()
                );

        verify(repository, times(1)).findAll();
    }

    @Test
    void createPOSIs201() throws Exception {
        PointOfSaleCreateRequest request = new PointOfSaleCreateRequest("foo");
        String json = mapper.writeValueAsString(request);

        mockMvc.perform(post("/pos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isCreated(),
                        header().exists("Location")
                );
    }

    @Test
    void createPOSIs400() throws Exception {
        mockMvc.perform(post("/pos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest()
                );
    }

    @Test
    void getPOSByIdIs200() throws Exception {
        String name = "foo";
        PointOfSaleCreateRequest request = new PointOfSaleCreateRequest(name);
        String json = mapper.writeValueAsString(request);

        MvcResult response = mockMvc.perform(post("/pos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isCreated(),
                        header().exists("Location")
                )
                .andReturn();

        String[] splitUrl = response.getResponse().getHeader("Location").split("/");
        String id = splitUrl[4];

        mockMvc.perform(get("/pos/{id}", id))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(id),
                        jsonPath("$.name").value(name)
                );

    }

    @Test
    void getPOSByIdIs404() throws Exception {
        Integer id = 1000;
        String expectedMessage = String.format("Point of Sale with ID %d not found", id);

        mockMvc.perform(get("/pos/{id}", id))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value(expectedMessage)
                );
    }

    @Test
    void updatePOSByIdIs200() throws Exception {
        String oldName = "foo";
        String newName = "bar";
        PointOfSaleCreateRequest createRequest = new PointOfSaleCreateRequest(oldName);
        PointOfSaleUpdateRequest updateRequest = new PointOfSaleUpdateRequest(newName);
        String createJson = mapper.writeValueAsString(createRequest);
        String updateJson = mapper.writeValueAsString(updateRequest);

        MvcResult response = mockMvc.perform(post("/pos")
                        .content(createJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isCreated(),
                        header().exists("Location")
                )
                .andReturn();

        String[] splitUrl = response.getResponse().getHeader("Location").split("/");
        String id = splitUrl[4];

        //Thread.sleep(Duration.ofSeconds(1L));

        mockMvc.perform(get("/pos/{id}", id))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(id),
                        jsonPath("$.name").value(oldName)
                );

        //Thread.sleep(Duration.ofSeconds(1L));

        mockMvc.perform(put("/pos/{id}", id)
                        .content(updateJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(id),
                        jsonPath("$.name").value(newName)
                );
    }

    @Test
    void updatePOSByIdIs404() throws Exception {
        Integer id = 1000;
        String name = "bar";
        String expectedMessage = String.format("Point of Sale with ID %d not found", id);
        PointOfSaleUpdateRequest request = new PointOfSaleUpdateRequest(name);
        String json = mapper.writeValueAsString(request);

        mockMvc.perform(put("/pos/{id}", id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value(expectedMessage)
                );
    }

    @Test
    void deletePOSIs200() throws Exception {
        String name = "foo";
        PointOfSaleCreateRequest request = new PointOfSaleCreateRequest(name);
        String json = mapper.writeValueAsString(request);

        MvcResult response = mockMvc.perform(post("/pos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isCreated(),
                        header().exists("Location")
                )
                .andReturn();

        String[] splitUrl = response.getResponse().getHeader("Location").split("/");
        String id = splitUrl[4];
        String expectedMessage = String.format("Point of Sale with ID %s not found", id);

        mockMvc.perform(delete("/pos/{id}", id))
                .andExpectAll(
                        status().isNoContent()
                );

        mockMvc.perform(get("/pos/{id}", id))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value(expectedMessage)
                );
    }

    @Test
    void deletePOSIs404() throws Exception {
        Integer id = 1000;
        String expectedMessage = String.format("Point of Sale with ID %d not found", id);

        mockMvc.perform(delete("/pos/{id}", id))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value(expectedMessage)
                );
    }
}