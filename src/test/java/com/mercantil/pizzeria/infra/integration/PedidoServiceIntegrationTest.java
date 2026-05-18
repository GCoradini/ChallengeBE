package com.mercantil.pizzeria.infra.integration;

import com.mercantil.pizzeria.core.model.Pedido;
import com.mercantil.pizzeria.core.model.PedidoDetalle;
import com.mercantil.pizzeria.core.model.Producto;
import com.mercantil.pizzeria.infra.database.entity.PedidoCabeceraEntity;
import com.mercantil.pizzeria.infra.database.repository.PedidoCabeceraJPARepository;
import com.mercantil.pizzeria.infra.impl.PedidoServiceImpl;
import com.mercantil.pizzeria.infra.impl.ProductoServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PedidoServiceIntegrationTest {

    @Autowired
    private PedidoServiceImpl pedidoService;

    @Autowired
    private PedidoCabeceraJPARepository pedidoCabeceraJPARepository;

    @Autowired
    private ProductoServiceImpl productoService;

    private Pedido pedidoACrear;
    private PedidoDetalle pedidoDetalleACrear;
    private Producto producto;

    private static final String DIRECCION = "Calle 123";
    private static final String EMAIL = "email@gmail.com";
    private static final String TELEFONO = "12345678";
    private static final LocalTime HORARIO = LocalTime.now();
    private static final int CANTIDAD = 1;

    private static final String NOMBRE = "Pizza";
    private static final String DESCRIPCION_CORTA = "Es una pizza";
    private static final String DESCRIPCION_LARGA = "Es una pizza de muzzarela";
    private static final BigDecimal PRECIO_UNITARIO = BigDecimal.valueOf(0);

    @BeforeEach
    void setUp() {
        producto = Producto.builder()
                .nombre(NOMBRE)
                .descripcionCorta(DESCRIPCION_CORTA)
                .descripcionLarga(DESCRIPCION_LARGA)
                .precioUnitario(PRECIO_UNITARIO)
                .build();

        pedidoDetalleACrear = PedidoDetalle.builder()
                .cantidad(CANTIDAD)
                .build();

        pedidoACrear = Pedido.builder()
                .direccion(DIRECCION)
                .email(EMAIL)
                .telefono(TELEFONO)
                .horario(HORARIO)
                .pedidoDetalles(List.of(pedidoDetalleACrear))
                .build();
    }

    @Test
    void deberiaCrearProductoCorrectamente() {
        Producto productoCreado = productoService.crearProducto(producto);
        pedidoDetalleACrear.setProducto(productoCreado);

        pedidoService.crearPedido(pedidoACrear);
        List<PedidoCabeceraEntity> pedidoBuscado = pedidoCabeceraJPARepository.findAllByFechaAlta(LocalDate.now());

        assertThat(pedidoBuscado).hasSize(1);
        assertThat(pedidoBuscado.getFirst().getId()).isNotNull();
        assertThat(pedidoBuscado.getFirst().getDireccion()).isEqualTo(pedidoACrear.getDireccion());
        assertThat(pedidoBuscado.getFirst().getEmail()).isEqualTo(pedidoACrear.getEmail());
        assertThat(pedidoBuscado.getFirst().getTelefono()).isEqualTo(pedidoACrear.getTelefono());
        assertThat(pedidoBuscado.getFirst().getHorario().truncatedTo(ChronoUnit.MINUTES))
                .isEqualTo(pedidoACrear.getHorario().truncatedTo(ChronoUnit.MINUTES));
        assertThat(pedidoBuscado.getFirst().getEstado()).isEqualTo("PENDIENTE");
        assertThat(pedidoBuscado.getFirst().getFechaAlta()).isEqualTo(LocalDate.now());
    }

    @Test
    void deberiaObtenerPedidosPorFechaCorrectamente() {
        LocalDate fecha = LocalDate.now();

        Producto productoCreado = productoService.crearProducto(producto);
        pedidoDetalleACrear.setProducto(productoCreado);

        pedidoService.crearPedido(pedidoACrear);

        List<PedidoCabeceraEntity> pedidoBuscado = pedidoCabeceraJPARepository.findAllByFechaAlta(fecha);

        assertThat(pedidoBuscado).hasSize(1);
        assertThat(pedidoBuscado.getFirst().getFechaAlta()).isEqualTo(fecha);
    }

    @Test
    void deberiaDevolverListaVaciaCuandoNoHayPedidosEnFecha() {
        List<PedidoCabeceraEntity> pedidoBuscado = pedidoCabeceraJPARepository.findAllByFechaAlta(LocalDate.now());

        assertThat(pedidoBuscado).isEmpty();
    }

}
