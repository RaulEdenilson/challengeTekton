package com.cahellenge.tekton.challengetekton.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cahellenge.tekton.challengetekton.model.HistorialEntryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class HistorialServiceTest {

    @Mock
    private IHistorialRepository repo;

    private HistorialService service;

    @BeforeEach
    void setUp() {
        service = new HistorialService(repo);
    }

    @Test
    void registrarAsync_debeGuardarEntradaConDatosCorrectos() {
        // Datos de entrada
        String endpoint = "/api/test";
        BigDecimal num1 = new BigDecimal("1.23");
        BigDecimal num2 = new BigDecimal("4.56");
        BigDecimal resultado = new BigDecimal("5.79");
        String error = null;

        // Llamada al método
        service.registrarAsync(endpoint, num1, num2, resultado, error);

        // Capturamos el objeto guardado
        ArgumentCaptor<HistorialEntryEntity> captor =
                ArgumentCaptor.forClass(HistorialEntryEntity.class);
        verify(repo).save(captor.capture());

        HistorialEntryEntity saved = captor.getValue();
        assertNotNull(saved.getTimestamp(), "Debe asignar timestamp");
        assertEquals(endpoint, saved.getEndpoint());
        assertEquals(num1, saved.getNum1());
        assertEquals(num2, saved.getNum2());
        assertEquals(resultado, saved.getResultado());
        assertEquals(error, saved.getError());
    }

    @Test
    void obtenerHistorial_debeDelegarEnElRepositorioConPageable() {
        // Preparamos un Pageable de prueba
        Pageable pageable = PageRequest.of(1, 2, Sort.by("timestamp").descending());
        // Preparamos una página de ejemplo
        HistorialEntryEntity entry = new HistorialEntryEntity();
        Page<HistorialEntryEntity> page = new PageImpl<>(List.of(entry), pageable, 1);

        when(repo.findAll(pageable)).thenReturn(page);

        // Llamada al método
        Page<HistorialEntryEntity> result = service.obtenerHistorial(pageable);

        // Verificaciones
        assertSame(page, result, "Debe devolver exactamente la página devuelta por el repositorio");
    }
}
