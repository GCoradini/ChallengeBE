package com.mercantil.pizzeria.web.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
public class ProductoResponseDto {
    private UUID id;

    private String nombre;

    private String descripcionCorta;

    private String descripcionLarga;

    private BigDecimal precioUnitario;

}
