package com.mercantil.pizzeria.core.model;

import java.util.UUID;

public interface ProductoRepository {

    Producto crearProducto(Producto producto);

    Producto obtenerProducto(UUID id);

    void actualizarProducto(UUID id, Producto producto);

    void eliminarProducto(UUID id);

}
