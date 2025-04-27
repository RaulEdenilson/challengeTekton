package com.cahellenge.tekton.challengetekton.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.math.BigDecimal;


@Data
public class CalculoRequest {
    @NotNull(message = "num1 es obligatorio")
    @Digits(integer = 20, fraction = 10, message = "num1 debe ser un número válido")
    private BigDecimal num1;

    @NotNull(message = "num2 es obligatorio")
    @Digits(integer = 20, fraction = 10, message = "num2 debe ser un número válido")
    private BigDecimal num2;
}