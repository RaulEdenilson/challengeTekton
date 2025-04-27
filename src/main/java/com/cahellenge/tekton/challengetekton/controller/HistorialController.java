package com.cahellenge.tekton.challengetekton.controller;

import com.cahellenge.tekton.challengetekton.model.HistorialEntryEntity;
import com.cahellenge.tekton.challengetekton.service.HistorialService;

import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HistorialController {
    private final HistorialService historialService;

    public HistorialController(HistorialService historialService) {
        this.historialService = historialService;
    }

    @GetMapping("/history")
    public ResponseEntity<Page<HistorialEntryEntity>> history(
            @Parameter(description = "Número de página (0-based)", example = "0")
            @RequestParam(name = "page", defaultValue = "0") int page,

            @Parameter(description = "Tamaño de página", example = "20")
            @RequestParam(name = "size", defaultValue = "20") int size,

            @Parameter(
                    description = "Orden, p.e. 'timestamp,asc' o 'num1,desc'",
                    example = "timestamp,desc"
            )
            @RequestParam(name = "sort", defaultValue = "timestamp,desc") String sort
    ) {
        String[] parts = sort.split(",", 2);
        Sort.Direction dir = parts.length>1 && parts[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, parts[0]));
        Page<HistorialEntryEntity> result = historialService.obtenerHistorial(pageable);
        return ResponseEntity.ok(result);
    }
}
