package com.mercantil.pizzeria.web.mapper;

import com.mercantil.pizzeria.core.model.PedidoDetalle;
import com.mercantil.pizzeria.core.model.Producto;
import com.mercantil.pizzeria.web.dto.request.DetalleRequestDto;
import com.mercantil.pizzeria.web.dto.response.DetallePedidoResponseDto;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperWebPedidoDetalle {

    public DetallePedidoResponseDto toDto(PedidoDetalle pedidoDetalle) {
        return DetallePedidoResponseDto.builder()
                .producto(pedidoDetalle.getProducto().getId())
                .nombre(pedidoDetalle.getProducto().getNombre())
                .cantidad(pedidoDetalle.getCantidad())
                .importe(pedidoDetalle.getPrecioUnitario())
                .build();
    }

    public List<DetallePedidoResponseDto> toDto(List<PedidoDetalle> pedidoDetalleList) {
        return pedidoDetalleList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PedidoDetalle toDomain(DetalleRequestDto pedidoRequestDto) {

        Producto producto = Producto.builder()
                .id(pedidoRequestDto.getProducto())
                .build();

        return PedidoDetalle.builder()
                .producto(producto)
                .cantidad(pedidoRequestDto.getCantidad())
                .build();
    }

    public List<PedidoDetalle> toDomain(List<DetalleRequestDto> detalleRequestDtoList) {
        return detalleRequestDtoList.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

}
