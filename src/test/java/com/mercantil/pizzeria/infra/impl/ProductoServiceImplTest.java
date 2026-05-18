package com.mercantil.pizzeria.infra.impl;

import com.mercantil.pizzeria.core.model.Producto;
import com.mercantil.pizzeria.infra.database.entity.ProductoEntity;
import com.mercantil.pizzeria.infra.database.mapper.MapperInfraProducto;
import com.mercantil.pizzeria.infra.database.repository.ProductoJPARepository;
import com.mercantil.pizzeria.infra.exception.ProductoNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoJPARepository productoJPARepository;

    @Mock
    private MapperInfraProducto productoMapper;

    @InjectMocks
    private ProductoServiceImpl productoServiceImpl;

    private Producto productoAGuardar;
    private Producto producto;
    private ProductoEntity productoEntityAGuardar;
    private ProductoEntity productoEntity;

    private static final UUID ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    private static final String NOMBRE = "Pizza";
    private static final String DESCRIPCION_CORTA = "Es una pizza";
    private static final String DESCRIPCION_LARGA = "Es una pizza de muzzarella";
    private static final BigDecimal PRECIO_UNITARIO = BigDecimal.valueOf(10000.00);

    private static final String MENSAJE_EXCEPCION = "Producto no encontrado. ID producto: ";

    @BeforeEach
    void setUp() {
        productoAGuardar = Producto.builder()
                .nombre(NOMBRE)
                .descripcionCorta(DESCRIPCION_CORTA)
                .descripcionLarga(DESCRIPCION_LARGA)
                .precioUnitario(PRECIO_UNITARIO)
                .build();

        producto = Producto.builder()
                .id(ID)
                .nombre(NOMBRE)
                .descripcionCorta(DESCRIPCION_CORTA)
                .descripcionLarga(DESCRIPCION_LARGA)
                .precioUnitario(PRECIO_UNITARIO)
                .build();

        productoEntityAGuardar =
                ProductoEntity.builder()
                        .nombre(NOMBRE)
                        .descripcionCorta(DESCRIPCION_CORTA)
                        .descripcionLarga(DESCRIPCION_LARGA)
                        .precioUnitario(PRECIO_UNITARIO)
                        .build();

        productoEntity =
                ProductoEntity.builder()
                        .id(ID)
                        .nombre(NOMBRE)
                        .descripcionCorta(DESCRIPCION_CORTA)
                        .descripcionLarga(DESCRIPCION_LARGA)
                        .precioUnitario(PRECIO_UNITARIO)
                        .build();
    }

    @Test
    void deberiaCrearProducto() {
        when(productoMapper.toEntity(productoAGuardar)).thenReturn(productoEntityAGuardar);
        when(productoJPARepository.save(productoEntityAGuardar)).thenReturn(productoEntity);
        when(productoMapper.toDomain(productoEntity)).thenReturn(producto);

        Producto resultado = productoServiceImpl.crearProducto(productoAGuardar);

        assertThat(resultado).isNotNull().isEqualTo(producto);
    }

    @Test
    void deberiaObtenerProducto() {
        when(productoJPARepository.findById(ID)).thenReturn(Optional.of(productoEntity));
        when(productoMapper.toDomain(productoEntity)).thenReturn(producto);

        Producto resultado = productoServiceImpl.obtenerProducto(ID);

        assertThat(resultado).isNotNull().isEqualTo(producto);
    }

    @Test
    void deberiaLanzarExcepcionCuandoNoEncuentraProductoAlObtener() {
        when(productoJPARepository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productoServiceImpl.obtenerProducto(ID))
                .isInstanceOfAny(ProductoNotFoundException.class)
                .hasMessage(MENSAJE_EXCEPCION + ID);
    }

    @Test
    void deberiaActualizarProducto() {
        when(productoJPARepository.findById(ID)).thenReturn(Optional.of(productoEntity));

        String nombreActualizado = "Producto actualizado";
        String descripcionCortaActualizada = "Descripcion corta actualizada";
        String descripcionLargaActualizada = "Descripcion larga actualizada";
        BigDecimal precioUnitarioActualizado = BigDecimal.valueOf(20000.00);

        Producto productoActualizar =
                Producto.builder()
                        .nombre(nombreActualizado)
                        .descripcionCorta(descripcionCortaActualizada)
                        .descripcionLarga(descripcionLargaActualizada)
                        .precioUnitario(precioUnitarioActualizado)
                        .build();

        productoServiceImpl.actualizarProducto(ID, productoActualizar);

        assertThat(productoEntity.getNombre()).isEqualTo(nombreActualizado);
        assertThat(productoEntity.getDescripcionCorta()).isEqualTo(descripcionCortaActualizada);
        assertThat(productoEntity.getDescripcionLarga()).isEqualTo(descripcionLargaActualizada);
        assertThat(productoEntity.getPrecioUnitario()).isEqualTo(precioUnitarioActualizado);

        verify(productoJPARepository).findById(ID);
        verify(productoJPARepository).save(productoEntity);
    }

    @Test
    void deberiaLanzarExcepcionCuandoNoEncuentraProductoAlActualizar() {
        when(productoJPARepository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productoServiceImpl.actualizarProducto(ID, productoAGuardar))
                .isInstanceOfAny(ProductoNotFoundException.class)
                .hasMessage(MENSAJE_EXCEPCION + ID);
    }

    @Test
    void deberiaEliminarProducto() {
        when(productoJPARepository.findById(ID)).thenReturn(Optional.of(productoEntity));

        productoServiceImpl.eliminarProducto(ID);

        verify(productoJPARepository).findById(ID);
        verify(productoJPARepository).delete(productoEntity);
    }

    @Test
    void deberiaLanzarExcepcionCuandoNoEncuentraProductoAlEliminar() {
        when(productoJPARepository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productoServiceImpl.eliminarProducto(ID))
                .isInstanceOfAny(ProductoNotFoundException.class)
                .hasMessage(MENSAJE_EXCEPCION + ID);
    }
}
