package com.mercantil.pizzeria.core.model;

import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository {

    Pedido crearPedido(Pedido pedido);

    List<Pedido> listarPedidoPorFecha(LocalDate fecha);

}
