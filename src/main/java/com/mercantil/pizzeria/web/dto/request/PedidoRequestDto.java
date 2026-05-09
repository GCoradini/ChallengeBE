package com.mercantil.pizzeria.web.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import java.time.LocalTime;
import java.util.List;

@Getter
@Data
public class PedidoRequestDto {

    private String direccion;

    private String email;

    private String telefono;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horario;

    private List<DetalleRequestDto> detalle;
}
