package com.mercantil.pizzeria.web.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import java.time.LocalTime;
import java.util.List;

@Getter
@Data
public class PedidoRequestDto {

    @NotBlank(message = "La dirección del pedido no puede ser nulo")
    private String direccion;

    @NotBlank(message = "El email del pedido no puede ser nulo")
    private String email;

    @NotBlank(message = "El telefono del pedido no puede ser nulo")
    private String telefono;

    @JsonFormat(pattern = "HH:mm")
    @NotNull(message = "El horario del pedido no puede ser nulo")
    private LocalTime horario;

    @Valid
    @NotNull(message = "El detalle del pedido no puede ser nulo")
    private List<DetalleRequestDto> detalle;
}
