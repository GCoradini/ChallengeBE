package com.mercantil.pizzeria.web.dto.request;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class ProductoRequestDto {

    private String nombre;

    private String descripcionCorta;

    private String descripcionLarga;

    private Double precioUnitario;

}
