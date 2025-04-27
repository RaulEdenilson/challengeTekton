package com.cahellenge.tekton.challengetekton.controller;

import com.cahellenge.tekton.challengetekton.dto.CalculoRequest;
import com.cahellenge.tekton.challengetekton.service.CalculadoraService;
import com.cahellenge.tekton.challengetekton.service.HistorialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
@Validated
public class CalculadoraController {
    @Autowired
    private  CalculadoraService service;
    @Autowired
    private  HistorialService historialService;

    @PostMapping("/calcular")
    public ResponseEntity<BigDecimal> calcular(
            @Valid @RequestBody CalculoRequest req
    ) {
        BigDecimal resultado = service.calcular(req.getNum1(), req.getNum2());
        historialService.registrarAsync(
                "/api/calcular",
                req.getNum1(),
                req.getNum2(),
                resultado,
                null
        );
        return ResponseEntity.ok(resultado);
    }
}
