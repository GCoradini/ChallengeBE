package com.mercantil.pizzeria.core.model;

import com.mercantil.pizzeria.core.enums.EstadoPedido;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class Pedido {

    private UUID id;

    private String direccion;

    private String email;

    private String telefono;

    private LocalTime horario;

    private LocalDate fechaAlta;

    private BigDecimal montoTotal;

    private Boolean aplicoDescuento;

    private EstadoPedido estado;

    private List<PedidoDetalle> pedidoDetalles;

    public Boolean verificarSiAplicaDescuento() {
        int totalPedidos = pedidoDetalles.stream()
                .mapToInt(PedidoDetalle::getCantidad)
                .sum();

        return totalPedidos > 3;
    }
}
