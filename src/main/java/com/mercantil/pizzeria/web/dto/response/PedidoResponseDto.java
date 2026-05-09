package com.mercantil.pizzeria.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
@Getter
public class PedidoResponseDto {

    private LocalDate fecha;

    private String direccion;

    private String email;

    private String telefono;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horario;

    private List<DetallePedidoResponseDto> detalle;

    private BigDecimal total;

    private Boolean descuento;

    private String estado;

}
