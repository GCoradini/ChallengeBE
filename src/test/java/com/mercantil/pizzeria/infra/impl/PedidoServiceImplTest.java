package com.mercantil.pizzeria.infra.impl;

import com.mercantil.pizzeria.core.enums.EstadoPedido;
import com.mercantil.pizzeria.core.model.Pedido;
import com.mercantil.pizzeria.core.model.PedidoDetalle;
import com.mercantil.pizzeria.core.model.Producto;
import com.mercantil.pizzeria.infra.database.entity.PedidoCabeceraEntity;
import com.mercantil.pizzeria.infra.database.entity.PedidoDetalleEntity;
import com.mercantil.pizzeria.infra.database.entity.ProductoEntity;
import com.mercantil.pizzeria.infra.database.mapper.MapperInfraPedidoCabecera;
import com.mercantil.pizzeria.infra.database.mapper.MapperInfraPedidoDetalle;
import com.mercantil.pizzeria.infra.database.mapper.MapperInfraProducto;
import com.mercantil.pizzeria.infra.database.repository.PedidoCabeceraJPARepository;
import com.mercantil.pizzeria.infra.database.repository.ProductoJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @Mock
    private PedidoCabeceraJPARepository pedidoCabeceraJPARepository;

    @Mock
    private ProductoJPARepository productoJPARepository;

    @Mock
    private MapperInfraPedidoCabecera mapperInfraPedidoCabecera;

    @Mock
    private MapperInfraPedidoDetalle mapperInfraPedidoDetalle;

    @Mock
    private MapperInfraProducto mapperInfraProducto;

    @InjectMocks
    private PedidoServiceImpl pedidoServiceImpl;

    private PedidoCabeceraEntity pedidoCabeceraEntity;

    private Pedido pedido;
    private Pedido pedidoACrear;

    private ProductoEntity productoEntity;

    private static final UUID ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    private static final String DIRECCION = "direccion1";
    private static final String EMAIL = "email1";
    private static final String TELEFONO = "1234567890";
    private static final LocalTime HORARIO_ACTUAL = LocalTime.now();
    private static final LocalDate FECHA_ACTUAL = LocalDate.now();
    private static final BigDecimal MONTO_TOTAL = new BigDecimal(10000);
    private static final boolean APLICO_DESCUENTO_SI = true;
    private static final String ESTADO_PEDIDO = "PENDIENTE";

    @BeforeEach
    void setUp() {
        pedidoCabeceraEntity = PedidoCabeceraEntity.builder()
                .id(ID)
                .direccion(DIRECCION)
                .email(EMAIL)
                .telefono(TELEFONO)
                .horario(HORARIO_ACTUAL)
                .fechaAlta(FECHA_ACTUAL)
                .montoTotal(MONTO_TOTAL)
                .aplicoDescuento(APLICO_DESCUENTO_SI)
                .estado(ESTADO_PEDIDO)
                .detalles(getPedidoDetalleEntityStub())
                .build();

        pedido = Pedido.builder()
                .id(ID)
                .direccion(DIRECCION)
                .email(EMAIL)
                .telefono(TELEFONO)
                .horario(HORARIO_ACTUAL)
                .fechaAlta(FECHA_ACTUAL)
                .montoTotal(MONTO_TOTAL)
                .aplicoDescuento(APLICO_DESCUENTO_SI)
                .estado(EstadoPedido.valueOf(ESTADO_PEDIDO))
                .pedidoDetalles(getPedidoDetalleStub())
                .build();

        pedidoACrear = Pedido.builder()
                .direccion(DIRECCION)
                .email(EMAIL)
                .telefono(TELEFONO)
                .horario(HORARIO_ACTUAL)
                .pedidoDetalles(getPedidoDetalleACrearStub())
                .build();

        productoEntity = ProductoEntity.builder()
                .id(ID)
                .nombre("Pizza")
                .precioUnitario(new BigDecimal(10000))
                .build();
    }

    @Test
    void deberiaCrearPedidoSinDescuento() {
        Producto producto = Producto.builder()
                .id(ID)
                .nombre("Pizza")
                .precioUnitario(new BigDecimal(10000))
                .build();

        when(productoJPARepository.findAllById(anyList())).thenReturn(List.of(productoEntity));
        when(mapperInfraProducto.toDomain(any())).thenReturn(producto);
        when(mapperInfraPedidoCabecera.toEntity(any(Pedido.class))).thenReturn(pedidoCabeceraEntity);
        when(mapperInfraPedidoDetalle.toEntity(anyList())).thenReturn(getPedidoDetalleEntityStub());
        when(pedidoCabeceraJPARepository.save(any(PedidoCabeceraEntity.class))).thenReturn(pedidoCabeceraEntity);

        Pedido resultado = pedidoServiceImpl.crearPedido(pedidoACrear);

        assertThat(resultado.getDireccion()).isEqualTo(DIRECCION);
        assertThat(resultado.getEmail()).isEqualTo(EMAIL);
        assertThat(resultado.getTelefono()).isEqualTo(TELEFONO);
        assertThat(resultado.getEstado()).isEqualTo(EstadoPedido.valueOf(ESTADO_PEDIDO));
        assertThat(resultado.getAplicoDescuento()).isFalse();
    }

    @Test
    void deberiaCrearPedidoConDescuento() {
        Producto producto = Producto.builder()
                .id(ID)
                .nombre("Pizza")
                .precioUnitario(new BigDecimal(10000))
                .build();

        when(productoJPARepository.findAllById(anyList())).thenReturn(List.of(productoEntity));
        when(mapperInfraProducto.toDomain(any())).thenReturn(producto);
        when(mapperInfraPedidoCabecera.toEntity(any(Pedido.class))).thenReturn(pedidoCabeceraEntity);
        when(mapperInfraPedidoDetalle.toEntity(anyList())).thenReturn(getPedidoDetalleEntityStub());
        when(pedidoCabeceraJPARepository.save(any(PedidoCabeceraEntity.class))).thenReturn(pedidoCabeceraEntity);

        pedidoACrear.getPedidoDetalles().getFirst().setCantidad(5);
        Pedido resultado = pedidoServiceImpl.crearPedido(pedidoACrear);

        assertThat(resultado.getDireccion()).isEqualTo(DIRECCION);
        assertThat(resultado.getEmail()).isEqualTo(EMAIL);
        assertThat(resultado.getTelefono()).isEqualTo(TELEFONO);
        assertThat(resultado.getEstado()).isEqualTo(EstadoPedido.valueOf(ESTADO_PEDIDO));
        assertThat(resultado.getAplicoDescuento()).isTrue();
    }

    @Test
    void deberiaListarPedidoPorFecha() {
        List<PedidoCabeceraEntity> listaPedidosCabecera = List.of(pedidoCabeceraEntity);
        List<Pedido> listaPedidos = List.of(pedido);

        when(pedidoCabeceraJPARepository.findAllByFechaAlta(FECHA_ACTUAL)).thenReturn(listaPedidosCabecera);
        when(mapperInfraPedidoCabecera.toDomain(listaPedidosCabecera)).thenReturn(listaPedidos);

        List<Pedido> resultado = pedidoServiceImpl.listarPedidoPorFecha(FECHA_ACTUAL);

        assertThat(resultado)
                .isNotEmpty()
                .hasSize(1)
                .isEqualTo(listaPedidos);
    }

    @Test
    void deberiaRetornarListaVaciaCuandoNoHayPedidosEnFecha() {
        LocalDate fecha = LocalDate.now();

        when(pedidoCabeceraJPARepository.findAllByFechaAlta(fecha)).thenReturn(List.of());

        List<Pedido> resultado = pedidoServiceImpl.listarPedidoPorFecha(fecha);

        assertThat(resultado)
                .isEmpty();
    }

    private List<PedidoDetalleEntity> getPedidoDetalleEntityStub() {
        return new ArrayList<>(
                List.of(
                        PedidoDetalleEntity.builder()
                                .id(UUID.randomUUID())
                                .producto(
                                        ProductoEntity.builder()
                                                .nombre("Pizza")
                                                .precioUnitario(new BigDecimal(10000))
                                                .build()
                                )
                                .cantidad(1)
                                .precioUnitario(new BigDecimal(10000))
                                .build()
                )
        );
    }

    private List<PedidoDetalle> getPedidoDetalleStub() {
        return List.of(
                PedidoDetalle.builder()
                        .id(UUID.randomUUID())
                        .producto(
                                Producto.builder()
                                        .nombre("Pizza")
                                        .precioUnitario(new BigDecimal(10000))
                                        .build()
                        )
                        .cantidad(1)
                        .precioUnitario(new BigDecimal(10000))
                        .build()
        );
    }

    private List<PedidoDetalle> getPedidoDetalleACrearStub() {
        return List.of(
                PedidoDetalle.builder()
                        .producto(
                                Producto.builder()
                                        .id(ID)
                                        .build()
                        )
                        .cantidad(1)
                        .build()
        );
    }

}
