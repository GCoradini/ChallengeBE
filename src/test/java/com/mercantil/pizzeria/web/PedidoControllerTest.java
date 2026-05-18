package com.mercantil.pizzeria.web;

import com.mercantil.pizzeria.core.model.Pedido;
import com.mercantil.pizzeria.core.model.PedidoRepository;
import com.mercantil.pizzeria.web.controller.PedidoController;
import com.mercantil.pizzeria.web.dto.request.DetalleRequestDto;
import com.mercantil.pizzeria.web.dto.request.PedidoRequestDto;
import com.mercantil.pizzeria.web.dto.response.PedidoResponseDto;
import com.mercantil.pizzeria.web.mapper.MapperWebPedido;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PedidoRepository pedidoRepository;

    @MockitoBean
    private MapperWebPedido mapperWebPedido;

    @Test
    void deberiaCrearPedido() throws Exception {
        PedidoRequestDto requestDto = PedidoRequestDto.builder()
                .direccion("Calle 1")
                .email("juan@juan.com")
                .telefono("12345678")
                .horario(LocalTime.now())
                .detalle(List.of(DetalleRequestDto.builder()
                        .producto(UUID.randomUUID())
                        .cantidad(1)
                        .build()))
                .build();

        Pedido pedido = Pedido.builder().build();

        Pedido pedidoCreado = Pedido.builder().build();
        PedidoResponseDto responseDto = PedidoResponseDto.builder().build();

        given(mapperWebPedido.toDomain(requestDto)).willReturn(pedido);
        given(pedidoRepository.crearPedido(pedido)).willReturn(pedidoCreado);
        given(mapperWebPedido.toDto(pedidoCreado)).willReturn(responseDto);

        ResultActions response = mockMvc.perform(post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        response.andExpect(status().isCreated());
    }

    @Test
    void deberiaRetornar400CuandoCampoEsVacio() throws Exception {
        PedidoRequestDto requestDto = PedidoRequestDto.builder().build();

        ResultActions response = mockMvc.perform(post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores[*].error")
                        .value(hasItems(
                                "La dirección del pedido no puede ser nulo",
                                "El email del pedido no puede ser nulo",
                                "El telefono del pedido no puede ser nulo",
                                "El horario del pedido no puede ser nulo",
                                "El detalle del pedido no puede ser nulo"
                        )));
    }

    @Test
    void deberiaRetornar400CuandoCantidadEsNegativa() throws Exception {
        PedidoRequestDto requestDto = PedidoRequestDto.builder()
                .direccion("Calle 1")
                .email("juan@juan.com")
                .telefono("12345678")
                .horario(LocalTime.now())
                .detalle(List.of(DetalleRequestDto.builder()
                        .producto(UUID.randomUUID())
                        .cantidad(0)
                        .build()))
                .build();

        ResultActions response = mockMvc.perform(post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores[*].error")
                        .value(hasItem("La cantidad del producto debe ser mayor a cero")));
    }

    @Test
    void deberiaObtenerPedidoPorFecha() throws Exception {
        LocalDate fecha = LocalDate.now();
        Pedido pedido = Pedido.builder().build();
        PedidoResponseDto responseDto = PedidoResponseDto.builder().build();

        given(pedidoRepository.listarPedidoPorFecha(fecha)).willReturn(List.of(pedido));
        given(mapperWebPedido.toDto(List.of(pedido))).willReturn(List.of(responseDto));

        ResultActions response = mockMvc.perform(get("/pedidos")
                .param("fecha", fecha.toString())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }
}
