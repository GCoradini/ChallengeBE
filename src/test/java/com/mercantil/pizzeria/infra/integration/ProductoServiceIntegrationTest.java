package com.mercantil.pizzeria.infra.integration;

import com.mercantil.pizzeria.core.model.Producto;
import com.mercantil.pizzeria.infra.database.entity.ProductoEntity;
import com.mercantil.pizzeria.infra.database.repository.ProductoJPARepository;
import com.mercantil.pizzeria.infra.impl.ProductoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductoServiceIntegrationTest {

    @Autowired
    private ProductoServiceImpl productoService;

    @Autowired
    private ProductoJPARepository productoJPARepository;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = Producto.builder()
                .nombre("Pizza")
                .descripcionCorta("Es una pizza")
                .descripcionLarga("Es una pizza de muzzarella")
                .precioUnitario(BigDecimal.valueOf(10000))
                .build();
    }

    @Test
    void deberiaCrearProductoCorrectamente() {
        Producto resultado = productoService.crearProducto(producto);

        ProductoEntity productoBuscado = productoJPARepository.findById(resultado.getId()).orElseThrow();

        assertThat(productoBuscado.getId()).isNotNull();
        assertThat(productoBuscado.getNombre()).isEqualTo(producto.getNombre());
        assertThat(productoBuscado.getDescripcionCorta()).isEqualTo(producto.getDescripcionCorta());
        assertThat(productoBuscado.getDescripcionLarga()).isEqualTo(producto.getDescripcionLarga());
        assertThat(productoBuscado.getPrecioUnitario()).isEqualByComparingTo(producto.getPrecioUnitario());
    }

    @Test
    void deberiaObtenerProductoCorrectamente() {
        Producto productoCreado = productoService.crearProducto(producto);
        Producto productoBuscado = productoService.obtenerProducto(productoCreado.getId());

        assertThat(productoBuscado).isNotNull();
        assertThat(productoBuscado.getId()).isNotNull();
        assertThat(productoBuscado.getNombre()).isEqualTo(producto.getNombre());
        assertThat(productoBuscado.getDescripcionCorta()).isEqualTo(producto.getDescripcionCorta());
        assertThat(productoBuscado.getDescripcionLarga()).isEqualTo(producto.getDescripcionLarga());
        assertThat(productoBuscado.getPrecioUnitario()).isEqualByComparingTo(producto.getPrecioUnitario());
    }

    @Test
    void deberiaActualizarProductoCorrectamente() {
        Producto productoCreado = productoService.crearProducto(producto);

        Producto productoActualizar = Producto.builder()
                .id(productoCreado.getId())
                .nombre("Pizza actualizada")
                .descripcionCorta("Es una pizza actualizada")
                .descripcionLarga("Es una pizza de muzzarella actualizada")
                .precioUnitario(BigDecimal.valueOf(20000))
                .build();

        productoService.actualizarProducto(productoCreado.getId(), productoActualizar);

        ProductoEntity productoBuscado =
                productoJPARepository
                        .findById(productoCreado.getId())
                        .orElseThrow();

        assertThat(productoBuscado.getId()).isEqualTo(productoActualizar.getId());
        assertThat(productoBuscado.getNombre()).isEqualTo(productoActualizar.getNombre());
        assertThat(productoBuscado.getDescripcionCorta()).isEqualTo(productoActualizar.getDescripcionCorta());
        assertThat(productoBuscado.getDescripcionLarga()).isEqualTo(productoActualizar.getDescripcionLarga());
        assertThat(productoBuscado.getPrecioUnitario()).isEqualByComparingTo(productoActualizar.getPrecioUnitario());
    }

    @Test
    void deberiaEliminarProductoCorrectamente() {
        Producto productoCreado = productoService.crearProducto(producto);

        productoService.eliminarProducto(productoCreado.getId());

        assertThat(productoJPARepository.findById(productoCreado.getId())).isEmpty();
    }
}
