package com.mercantil.pizzeria.web.mapper;

import com.mercantil.pizzeria.core.model.Pedido;
import com.mercantil.pizzeria.web.dto.request.PedidoRequestDto;
import com.mercantil.pizzeria.web.dto.response.PedidoResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class MapperWebPedido {

    private final MapperWebPedidoDetalle mapperWebPedidoDetalle;

    public PedidoResponseDto toDto(Pedido pedido) {
        return PedidoResponseDto.builder()
                .fecha(pedido.getFechaAlta())
                .direccion(pedido.getDireccion())
                .email(pedido.getEmail())
                .telefono(pedido.getTelefono())
                .horario(pedido.getHorario())
                .detalle(mapperWebPedidoDetalle.toDto(pedido.getPedidoDetalles()))
                .total(pedido.getMontoTotal())
                .descuento(pedido.getAplicoDescuento())
                .estado(pedido.getEstado().toString())
                .build();
    }

    public List<PedidoResponseDto> toDto(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::toDto)
                .toList();
    }

    public Pedido toDomain(PedidoRequestDto pedidoRequestDto) {
        return Pedido.builder()
                .direccion(pedidoRequestDto.getDireccion())
                .email(pedidoRequestDto.getEmail())
                .telefono(pedidoRequestDto.getTelefono())
                .horario(pedidoRequestDto.getHorario())
                .pedidoDetalles(mapperWebPedidoDetalle.toDomain(pedidoRequestDto.getDetalle()))
                .build();
    }

}
