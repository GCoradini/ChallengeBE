package com.mercantil.pizzeria.infra.database.mapper;

import com.mercantil.pizzeria.core.enums.EstadoPedido;
import com.mercantil.pizzeria.core.model.Pedido;
import com.mercantil.pizzeria.infra.database.entity.PedidoCabeceraEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MapperInfraPedidoCabecera {

    private final MapperInfraPedidoDetalle mapperInfraPedidoDetalle;

    public PedidoCabeceraEntity toEntity(Pedido pedido) {
        return PedidoCabeceraEntity.builder()
                .direccion(pedido.getDireccion())
                .email(pedido.getEmail())
                .telefono(pedido.getTelefono())
                .horario(pedido.getHorario())
                .fechaAlta(pedido.getFechaAlta())
                .montoTotal(pedido.getMontoTotal())
                .aplicoDescuento(pedido.getAplicoDescuento())
                .estado(pedido.getEstado().toString())
                .build();
    }

    public Pedido toDomain(PedidoCabeceraEntity pedidoCabeceraEntity) {
        return Pedido.builder()
                .direccion(pedidoCabeceraEntity.getDireccion())
                .email(pedidoCabeceraEntity.getEmail())
                .telefono(pedidoCabeceraEntity.getTelefono())
                .horario(pedidoCabeceraEntity.getHorario())
                .fechaAlta(pedidoCabeceraEntity.getFechaAlta())
                .montoTotal(pedidoCabeceraEntity.getMontoTotal())
                .aplicoDescuento(pedidoCabeceraEntity.getAplicoDescuento())
                .estado(EstadoPedido.valueOf(pedidoCabeceraEntity.getEstado()))
                .pedidoDetalles(mapperInfraPedidoDetalle.toDomain(pedidoCabeceraEntity.getDetalles()))
                .build();
    }

    public List<Pedido> toDomain(List<PedidoCabeceraEntity> pedidoCabeceraEntities) {
        return pedidoCabeceraEntities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }



}
