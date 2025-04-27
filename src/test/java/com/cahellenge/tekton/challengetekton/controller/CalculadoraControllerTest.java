package com.cahellenge.tekton.challengetekton.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import com.cahellenge.tekton.challengetekton.dto.CalculoRequest;
import com.cahellenge.tekton.challengetekton.service.CalculadoraService;
import com.cahellenge.tekton.challengetekton.service.HistorialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class CalculadoraControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CalculadoraService calculadoraService;

    @Mock
    private HistorialService historialService;

    @InjectMocks
    private CalculadoraController calculadoraController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(calculadoraController).build();
    }

    @Test
    void testCalcular_Success() throws Exception {
        // Arrange
        BigDecimal num1 = new BigDecimal("5");
        BigDecimal num2 = new BigDecimal("3");
        BigDecimal resultadoEsperado = new BigDecimal("10");

        when(calculadoraService.calcular(num1, num2)).thenReturn(resultadoEsperado);

        CalculoRequest request = new CalculoRequest();
        request.setNum1(num1);
        request.setNum2(num2);

        // Act & Assert
        mockMvc.perform(post("/api/calcular")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(resultadoEsperado));

        verify(calculadoraService, times(1)).calcular(num1, num2);
        verify(historialService, times(1))
                .registrarAsync("/api/calcular", num1, num2, resultadoEsperado, null);
    }
}
