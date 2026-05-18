package com.mercantil.pizzeria.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@Setter
public class Producto {

    private UUID id;

    private String nombre;

    private String descripcionCorta;

    private String descripcionLarga;

    private BigDecimal precioUnitario;

}
