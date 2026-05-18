package com.mercantil.pizzeria.web.controller;

import com.mercantil.pizzeria.core.model.Producto;
import com.mercantil.pizzeria.core.model.ProductoRepository;
import com.mercantil.pizzeria.web.dto.request.ProductoRequestDto;
import com.mercantil.pizzeria.web.dto.response.ProductoResponseDto;
import com.mercantil.pizzeria.web.mapper.MapperWebProducto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;


@RestController
@RequestMapping("/productos")
@AllArgsConstructor
public class ProductoController {

    private final ProductoRepository productoRepository;
    private final MapperWebProducto mapperWebProducto;

    @PostMapping
    public ResponseEntity<ProductoResponseDto> crearProducto(@RequestBody ProductoRequestDto productoRequestDto) {
        Producto productoCreado = productoRepository.crearProducto(mapperWebProducto.toDomain(productoRequestDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapperWebProducto.toDto(productoCreado));
    }

    @GetMapping("/{id}")
    public ProductoResponseDto buscarProducto(@PathVariable UUID id) {
        Producto producto = productoRepository.obtenerProducto(id);
        return mapperWebProducto.toDto(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarProducto(@PathVariable UUID id,
                                                   @RequestBody ProductoRequestDto productoRequestDto) {
        productoRepository.actualizarProducto(id, mapperWebProducto.toDomain(productoRequestDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable UUID id) {
        productoRepository.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
