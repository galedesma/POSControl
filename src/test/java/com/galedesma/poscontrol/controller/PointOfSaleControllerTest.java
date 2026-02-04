package com.galedesma.poscontrol.controller;

import com.galedesma.poscontrol.entity.PointOfSale;
import com.galedesma.poscontrol.mapper.PointOfSaleMapper;
import com.galedesma.poscontrol.service.PointOfSaleService;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.galedesma.poscontrol.util.TestUtils.createPersistedPOS;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PointOfSaleController.class)
class PointOfSaleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PointOfSaleService service;

    @TestConfiguration
    static class MapperTestConfig {
        @Bean
        public PointOfSaleMapper getMapper() {
            return Mappers.getMapper(PointOfSaleMapper.class);
        }
    }

    @Test
    void getAllPos() throws Exception {
        String name1 = "foo";
        String name2 = "bar";
        Integer id1 = 1;
        Integer id2 = 2;

        PointOfSale pos1 = createPersistedPOS(id1, name1);
        PointOfSale pos2 = createPersistedPOS(id2, name2);

        when(service.getAllPOS()).thenReturn(List.of(pos1, pos2));

        mockMvc.perform(get("/pos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.pointsOfSale").isArray());
    }
}