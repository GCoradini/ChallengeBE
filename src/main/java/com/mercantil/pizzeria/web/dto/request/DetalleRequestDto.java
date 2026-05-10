package com.mercantil.pizzeria.web.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import java.util.UUID;

@Getter
@Data
public class DetalleRequestDto {

    @NotNull(message = "El id del producto no puede ser nulo")
    private UUID producto;

    @NotNull(message = "La cantidad del producto no puede ser nulo")
    @Positive(message = "La cantidad del producto debe ser mayor a cero")
    private Integer cantidad;

}
