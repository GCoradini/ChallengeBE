package com.mercantil.pizzeria.infra.database.mapper;

import com.mercantil.pizzeria.core.model.PedidoDetalle;
import com.mercantil.pizzeria.infra.database.entity.PedidoDetalleEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class MapperInfraPedidoDetalle {

    MapperInfraProducto productoMapper;

    public PedidoDetalleEntity toEntity(PedidoDetalle pedidoDetalle) {
        return PedidoDetalleEntity.builder()
                .producto(productoMapper.toEntity(pedidoDetalle.getProducto()))
                .cantidad(pedidoDetalle.getCantidad())
                .precioUnitario(pedidoDetalle.getPrecioUnitario())
                .build();
    }

    public List<PedidoDetalleEntity> toEntity(List<PedidoDetalle> pedidoDetalles) {
        return pedidoDetalles.stream()
                .map(this::toEntity)
                .toList();
    }

    public PedidoDetalle toDomain(PedidoDetalleEntity pedidoDetalleEntity) {
        return PedidoDetalle.builder()
                .producto(productoMapper.toDomain(pedidoDetalleEntity.getProducto()))
                .cantidad(pedidoDetalleEntity.getCantidad())
                .precioUnitario(pedidoDetalleEntity.getPrecioUnitario())
                .build();
    }

    public List<PedidoDetalle> toDomain(List<PedidoDetalleEntity> pedidoDetalleEntities) {
        return pedidoDetalleEntities.stream()
                .map(this::toDomain)
                .toList();
    }

}
