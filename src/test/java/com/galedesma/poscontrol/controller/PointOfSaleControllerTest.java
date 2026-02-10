package com.galedesma.poscontrol.controller;

import com.galedesma.poscontrol.dto.out.GetAllPOSResponse;
import com.galedesma.poscontrol.dto.out.PointOfSaleResponse;
import com.galedesma.poscontrol.exception.PointOfSaleNotFoundException;
import com.galedesma.poscontrol.service.PointOfSaleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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

    @Test
    void getAllPos() throws Exception {
        String name1 = "foo";
        String name2 = "bar";
        Integer id1 = 1;
        Integer id2 = 2;
        Integer expectedSize = 2;

        PointOfSaleResponse pos1 = new PointOfSaleResponse(id1, name1);
        PointOfSaleResponse pos2 = new PointOfSaleResponse(id2, name2);

        List<PointOfSaleResponse> responseList = List.of(pos1, pos2);

        when(service.getAllPOS()).thenReturn(new GetAllPOSResponse(responseList.size(), responseList));

        mockMvc.perform(get("/pos"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.count").value(expectedSize),
                        jsonPath("$.pointsOfSale").isArray()
                );
    }

    @Test
    void getPOSById200() throws Exception {
        Integer id = 1;
        String name = "foo";

        when(service.getPOSById(id)).thenReturn(new PointOfSaleResponse(id, name));

        mockMvc.perform(get("/pos/{id}", id))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(id),
                        jsonPath("$.name").value(name)
                );
    }

    @Test
    void getPOSById404() throws Exception {
        Integer id = 1000;
        String expectedMessage = String.format("Point of Sale with ID %d not found", id);

        when(service.getPOSById(id)).thenThrow(new PointOfSaleNotFoundException(expectedMessage));

        mockMvc.perform(get("/pos/{id}", id))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value(expectedMessage)
                );
    }
}