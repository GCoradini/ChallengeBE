package com.mercantil.pizzeria.web.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import java.util.UUID;

@JsonPropertyOrder({"id", "nombre", "descripcionCorta", "descripcionLarga", "precioUnitario"})
@Builder
@Getter
public class ProductoResponseDto {
    private UUID id;

    private String nombre;

    private String descripcionCorta;

    private String descripcionLarga;

    private Double precioUnitario;

}
