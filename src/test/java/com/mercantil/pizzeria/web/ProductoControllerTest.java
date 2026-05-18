package com.mercantil.pizzeria.web;

import com.mercantil.pizzeria.core.model.Producto;
import com.mercantil.pizzeria.core.model.ProductoRepository;
import com.mercantil.pizzeria.web.controller.ProductoController;
import com.mercantil.pizzeria.web.dto.request.ProductoRequestDto;
import com.mercantil.pizzeria.web.dto.response.ProductoResponseDto;
import com.mercantil.pizzeria.web.mapper.MapperWebProducto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductoRepository productoRepository;

    @MockitoBean
    private MapperWebProducto mapperWebProducto;

    @Test
    void deberiaCrearProducto() throws Exception {
        ProductoRequestDto requestDto = ProductoRequestDto.builder().build();
        Producto producto = Producto.builder().build();
        Producto productoCreado = Producto.builder().build();
        ProductoResponseDto responseDto = ProductoResponseDto.builder().build();

        given(mapperWebProducto.toDomain(requestDto)).willReturn(producto);
        given(productoRepository.crearProducto(producto)).willReturn(productoCreado);
        given(mapperWebProducto.toDto(productoCreado)).willReturn(responseDto);

        ResultActions response = mockMvc.perform(post("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        response.andExpect(status().isCreated());
    }

    @Test
    void deberiaObtenerProductoPorId() throws Exception {
        UUID id = UUID.randomUUID();
        Producto producto = Producto.builder().build();

        given(productoRepository.obtenerProducto(id)).willReturn(producto);

        ResultActions response = mockMvc.perform(get("/productos/{id}", id));
        response.andExpect(status().isOk());
    }

    @Test
    void deberiaActualizarProducto() throws Exception {
        UUID id = UUID.randomUUID();
        ProductoRequestDto requestDto = ProductoRequestDto.builder().build();
        Producto producto = Producto.builder().build();

        given(mapperWebProducto.toDomain(requestDto)).willReturn(producto);
        doNothing().when(productoRepository).actualizarProducto(id, producto);

        ResultActions response = mockMvc.perform(put("/productos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        response.andExpect(status().isNoContent());
    }

    @Test
    void deberiaEliminarProducto() throws Exception{
        UUID id = UUID.randomUUID();

        doNothing().when(productoRepository).eliminarProducto(id);

        ResultActions response = mockMvc.perform(delete("/productos/{id}", id));
        response.andExpect(status().isNoContent());
    }

}
