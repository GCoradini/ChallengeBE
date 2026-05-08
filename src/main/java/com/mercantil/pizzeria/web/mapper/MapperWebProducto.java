package com.mercantil.pizzeria.web.mapper;

import com.mercantil.pizzeria.core.model.Producto;
import com.mercantil.pizzeria.web.dto.request.ProductoRequestDto;
import com.mercantil.pizzeria.web.dto.response.ProductoResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MapperWebProducto {

    public Producto toDomain(ProductoRequestDto productoRequestDto) {
        return Producto.builder()
                .nombre(productoRequestDto.getNombre())
                .descripcionCorta(productoRequestDto.getDescripcionCorta())
                .descripcionLarga(productoRequestDto.getDescripcionLarga())
                .precioUnitario(productoRequestDto.getPrecioUnitario())
                .build();
    }

    public ProductoResponseDto toDto(Producto producto) {
        return ProductoResponseDto.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcionCorta(producto.getDescripcionCorta())
                .descripcionLarga(producto.getDescripcionLarga())
                .precioUnitario(producto.getPrecioUnitario())
                .build();
    }

}
