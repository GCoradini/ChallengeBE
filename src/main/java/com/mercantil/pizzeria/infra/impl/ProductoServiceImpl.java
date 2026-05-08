package com.mercantil.pizzeria.infra.impl;

import com.mercantil.pizzeria.core.model.Producto;
import com.mercantil.pizzeria.core.model.ProductoRepository;
import com.mercantil.pizzeria.infra.database.entity.ProductoEntity;
import com.mercantil.pizzeria.infra.database.repository.ProductoJPARepository;
import com.mercantil.pizzeria.infra.exception.ProductoNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoRepository {

    private final ProductoJPARepository productoJPARepository;

    @Override
    public Producto crearProducto(Producto producto) {
        ProductoEntity productoCreado = productoJPARepository.save(ProductoEntity.toEntity(producto));
        log.info("Se creo el producto con id: {}", productoCreado.getId());
        return ProductoEntity.toDomain(productoCreado);
    }

    @Override
    public Producto obtenerProducto(UUID id) {
        ProductoEntity productoBuscado = productoJPARepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto con id: " + id + "no encontrado"));
        return ProductoEntity.toDomain(productoBuscado);
    }

    @Override
    public void actualizarProducto(UUID id, Producto producto) {
        ProductoEntity productoActualizar = productoJPARepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto con id: " + id + "no encontrado"));
        productoActualizar.setNombre(producto.getNombre());
        productoActualizar.setDescripcionCorta(producto.getDescripcionCorta());
        productoActualizar.setDescripcionLarga(producto.getDescripcionLarga());
        productoActualizar.setPrecioUnitario(producto.getPrecioUnitario());
        log.info("Se actualiza el producto con id: {}", productoActualizar.getId());
        productoJPARepository.save(productoActualizar);
    }

    @Override
    public void eliminarProducto(UUID id) {
        ProductoEntity productoEliminar = productoJPARepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto con id: " + id + "no encontrado"));
        productoJPARepository.delete(productoEliminar);
        log.info("Se elimina el producto con id: {}", id);
    }
}
