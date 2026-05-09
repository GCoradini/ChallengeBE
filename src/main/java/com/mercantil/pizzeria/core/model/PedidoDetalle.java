package com.mercantil.pizzeria.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@Setter
public class PedidoDetalle {

    private UUID id;

    private Producto producto;

    private Integer cantidad;

    private BigDecimal precioUnitario;
}
