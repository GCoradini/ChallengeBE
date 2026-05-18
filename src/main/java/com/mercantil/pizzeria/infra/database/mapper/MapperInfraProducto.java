package com.mercantil.pizzeria.infra.database.mapper;

import com.mercantil.pizzeria.core.model.Producto;
import com.mercantil.pizzeria.infra.database.entity.ProductoEntity;
import org.springframework.stereotype.Component;

@Component
public class MapperInfraProducto {

    public ProductoEntity toEntity(Producto producto) {
        return ProductoEntity.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcionCorta(producto.getDescripcionCorta())
                .descripcionLarga(producto.getDescripcionLarga())
                .precioUnitario(producto.getPrecioUnitario())
                .build();
    }

    public Producto toDomain(ProductoEntity productoEntity) {
        return Producto.builder()
                .id(productoEntity.getId())
                .nombre(productoEntity.getNombre())
                .descripcionCorta(productoEntity.getDescripcionCorta())
                .descripcionLarga(productoEntity.getDescripcionLarga())
                .precioUnitario(productoEntity.getPrecioUnitario())
                .build();
    }
}
