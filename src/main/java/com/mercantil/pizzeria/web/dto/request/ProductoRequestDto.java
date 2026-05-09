package com.mercantil.pizzeria.web.dto.request;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Data
public class ProductoRequestDto {

    private String nombre;

    private String descripcionCorta;

    private String descripcionLarga;

    private BigDecimal precioUnitario;

}
