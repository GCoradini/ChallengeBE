package com.mercantil.pizzeria.web.dto.request;

import lombok.Data;
import lombok.Getter;
import java.util.UUID;

@Getter
@Data
public class DetalleRequestDto {

    private UUID producto;

    private int cantidad;

}
