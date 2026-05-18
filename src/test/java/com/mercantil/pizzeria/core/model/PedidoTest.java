package com.mercantil.pizzeria.core.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PedidoTest {

    PedidoDetalle pedidoDetalleCantidadUno;
    PedidoDetalle pedidoDetalleCantidadDos;
    PedidoDetalle pedidoDetalleCantidadTres;
    Producto producto;

    @BeforeEach
    void setUp() {
        producto = Producto.builder()
                .nombre("Pizza")
                .build();

        pedidoDetalleCantidadUno = PedidoDetalle.builder()
                .producto(producto)
                .cantidad(1)
                .build();

        pedidoDetalleCantidadDos = PedidoDetalle.builder()
                .producto(producto)
                .cantidad(2)
                .build();

        pedidoDetalleCantidadTres = PedidoDetalle.builder()
                .producto(producto)
                .cantidad(3)
                .build();
    }

    @Test
    void noDeberiaAplicarDescuentoCuandoNoHayProductos() {
        Pedido pedido = Pedido.builder()
                .pedidoDetalles(List.of())
                .build();

        assertThat(pedido.verificarSiAplicaDescuento()).isFalse();
    }

    @Test
    void noDeberiaAplicarDescuentoCuandoHayMenosDeTresProductos() {
        Pedido pedido = Pedido.builder()
                .pedidoDetalles(List.of(pedidoDetalleCantidadDos))
                .build();

        assertThat(pedido.verificarSiAplicaDescuento()).isFalse();
    }

    @Test
    void noDeberiaAplicarDescuentoCuandoHayTresProductos() {
        Pedido pedido = Pedido.builder()
                .pedidoDetalles(List.of(pedidoDetalleCantidadUno, pedidoDetalleCantidadDos))
                .build();

        assertThat(pedido.verificarSiAplicaDescuento()).isFalse();
    }

    @Test
    void deberiaAplicarDescuentoCuandoHayMasDeTresProductos() {
        Pedido pedido = Pedido.builder()
                .pedidoDetalles(List.of(pedidoDetalleCantidadUno, pedidoDetalleCantidadTres))
                .build();

        assertThat(pedido.verificarSiAplicaDescuento()).isTrue();
    }
}
