package com.cahellenge.tekton.challengetekton.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_llamadas")
@Data
@NoArgsConstructor
public class HistorialEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private String endpoint;
    private BigDecimal num1;
    private BigDecimal num2;
    private BigDecimal resultado;

    @Column(length = 500)
    private String error;
}
