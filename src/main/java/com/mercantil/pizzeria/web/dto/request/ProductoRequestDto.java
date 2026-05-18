package com.mercantil.pizzeria.web.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoRequestDto {

    private String nombre;

    private String descripcionCorta;

    private String descripcionLarga;

    private BigDecimal precioUnitario;

}
