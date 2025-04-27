package com.cahellenge.tekton.challengetekton.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cahellenge.tekton.challengetekton.model.HistorialEntryEntity;
import com.cahellenge.tekton.challengetekton.service.HistorialService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@WebMvcTest(HistorialController.class)
class HistorialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HistorialService historialService;

    @Test
    @DisplayName("GET /api/history devuelve página vacía si no hay registros")
    void historyReturnsEmptyPage() throws Exception {
        when(historialService.obtenerHistorial(any()))
                .thenReturn(Page.empty());

        mockMvc.perform(get("/api/history")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "timestamp,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(0))
                .andExpect(jsonPath("$.totalElements").value(0));
    }

    @Test
    @DisplayName("GET /api/history devuelve página con registros existentes")
    void historyReturnsPageWithEntries() throws Exception {
        HistorialEntryEntity entry = new HistorialEntryEntity();
        entry.setId(1L);
        entry.setTimestamp(LocalDateTime.of(2025, 4, 27, 1, 23, 45));
        entry.setEndpoint("/api/calcular");
        entry.setNum1(new BigDecimal("3"));
        entry.setNum2(new BigDecimal("2"));
        entry.setResultado(new BigDecimal("5.75"));
        entry.setError(null);

        Page<HistorialEntryEntity> page = new PageImpl<>(List.of(entry));
        when(historialService.obtenerHistorial(any()))
                .thenReturn(page);

        mockMvc.perform(get("/api/history")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "timestamp,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].endpoint").value("/api/calcular"))
                .andExpect(jsonPath("$.content[0].num1").value(3))
                .andExpect(jsonPath("$.content[0].num2").value(2))
                .andExpect(jsonPath("$.content[0].resultado").value(5.75))
                .andExpect(jsonPath("$.content[0].error").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(1));
    }
}
