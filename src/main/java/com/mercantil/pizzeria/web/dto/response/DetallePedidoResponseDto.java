package com.mercantil.pizzeria.web.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
public class DetallePedidoResponseDto {

    private UUID producto;

    private String nombre;

    private Integer cantidad;

    private BigDecimal importe;

}
