package com.cahellenge.tekton.challengetekton.service;


import com.cahellenge.tekton.challengetekton.model.HistorialEntryEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class HistorialService {

    private final IHistorialRepository repo;

    public HistorialService(IHistorialRepository repo) {
        this.repo = repo;
    }

    @Async
    public void registrarAsync(String endpoint,
                               BigDecimal num1,
                               BigDecimal num2,
                               BigDecimal resultado,
                               String error) {
        HistorialEntryEntity entry = new HistorialEntryEntity();
        entry.setTimestamp(LocalDateTime.now());
        entry.setEndpoint(endpoint);
        entry.setNum1(num1);
        entry.setNum2(num2);
        entry.setResultado(resultado);
        entry.setError(error);
        repo.save(entry);
    }

    public Page<HistorialEntryEntity> obtenerHistorial(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
