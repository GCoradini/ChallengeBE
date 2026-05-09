package com.mercantil.pizzeria.infra.impl;

import com.mercantil.pizzeria.core.enums.EstadoPedido;
import com.mercantil.pizzeria.core.model.Pedido;
import com.mercantil.pizzeria.core.model.PedidoDetalle;
import com.mercantil.pizzeria.core.model.PedidoRepository;
import com.mercantil.pizzeria.infra.database.entity.PedidoCabeceraEntity;
import com.mercantil.pizzeria.infra.database.entity.PedidoDetalleEntity;
import com.mercantil.pizzeria.infra.database.entity.ProductoEntity;
import com.mercantil.pizzeria.infra.database.mapper.MapperInfraPedidoCabecera;
import com.mercantil.pizzeria.infra.database.mapper.MapperInfraPedidoDetalle;
import com.mercantil.pizzeria.infra.database.mapper.MapperInfraProducto;
import com.mercantil.pizzeria.infra.database.repository.PedidoCabeceraJPARepository;
import com.mercantil.pizzeria.infra.database.repository.ProductoJPARepository;
import com.mercantil.pizzeria.infra.exception.ProductoNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoRepository {

    private final PedidoCabeceraJPARepository pedidoCabeceraJPARepository;
    private final ProductoJPARepository productoJPARepository;

    private final MapperInfraPedidoCabecera mapperInfraPedidoCabecera;
    private final MapperInfraPedidoDetalle mapperInfraPedidoDetalle;
    private final MapperInfraProducto mapperInfraProducto;

    @Override
    public Pedido crearPedido(Pedido pedido) {

        List<PedidoDetalle> pedidoDetalles = obtenerDetalleProductos(pedido.getPedidoDetalles());

        Boolean aplicaDescuento = pedido.verificarSiAplicaDescuento();
        BigDecimal montoTotal = calcularMontoTotal(pedidoDetalles);

        Pedido pedidoACrear = Pedido.builder()
                .direccion(pedido.getDireccion())
                .email(pedido.getEmail())
                .telefono(pedido.getTelefono())
                .horario(pedido.getHorario())
                .fechaAlta(LocalDate.now())
                .montoTotal(aplicaDescuento
                        ? montoTotal
                          .multiply(BigDecimal.valueOf(0.7))
                          .setScale(2, RoundingMode.HALF_UP)
                        : montoTotal)
                .aplicoDescuento(aplicaDescuento)
                .estado(EstadoPedido.PENDIENTE)
                .pedidoDetalles(pedidoDetalles)
                .build();

        PedidoCabeceraEntity pedidoEntity = mapperInfraPedidoCabecera.toEntity(pedidoACrear);
        mapperInfraPedidoDetalle.toEntity(pedidoACrear.getPedidoDetalles())
                .forEach(pedidoEntity::addDetalle);

        PedidoCabeceraEntity pedidoEntityCreado = pedidoCabeceraJPARepository.save(pedidoEntity);
        log.info("Se creo el pedido con id: {}", pedidoEntityCreado.getId());
        return pedidoACrear;
    }

    private List<PedidoDetalle> obtenerDetalleProductos(List<PedidoDetalle> pedidoDetalles) {
        List<UUID> productoIds = pedidoDetalles.stream()
                .map(detalle -> detalle.getProducto().getId())
                .collect(Collectors.toList());

        Map<UUID, ProductoEntity> productosMap = productoJPARepository.findAllById(productoIds)
                .stream()
                .collect(Collectors.toMap(ProductoEntity::getId, producto -> producto));

        return pedidoDetalles.stream()
                .map(detalle -> {
                    ProductoEntity productoEntity = productosMap.get(detalle.getProducto().getId());

                    if (productoEntity == null) {
                        throw new ProductoNotFoundException("Producto con id: " + detalle.getProducto().getId() + " no encontrado");
                    }

                    return PedidoDetalle.builder()
                            .producto(mapperInfraProducto.toDomain(productoEntity))
                            .cantidad(detalle.getCantidad())
                            .precioUnitario(productoEntity.getPrecioUnitario())
                            .build();
                })
                .collect(Collectors.toList());
    }

    private BigDecimal calcularMontoTotal(List<PedidoDetalle> pedidoDetalles) {
        return pedidoDetalles.stream()
                .map(detalle -> detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<Pedido> listarPedidoPorFecha(LocalDate fecha) {
        log.info("Se recuperan los pedidos con fecha: {}", fecha);
        List<PedidoCabeceraEntity> pedidoCabeceraEntities = pedidoCabeceraJPARepository.findAllByFechaAlta(fecha);

        log.info("Se recuperaron {} pedidos", pedidoCabeceraEntities.size());
        return mapperInfraPedidoCabecera.toDomain(pedidoCabeceraEntities);
    }
}
