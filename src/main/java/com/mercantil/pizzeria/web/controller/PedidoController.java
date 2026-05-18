package com.mercantil.pizzeria.web.controller;

import com.mercantil.pizzeria.core.model.Pedido;
import com.mercantil.pizzeria.core.model.PedidoRepository;
import com.mercantil.pizzeria.web.dto.request.PedidoRequestDto;
import com.mercantil.pizzeria.web.dto.response.PedidoResponseDto;
import com.mercantil.pizzeria.web.mapper.MapperWebPedido;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@AllArgsConstructor
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final MapperWebPedido mapperWebPedido;

    @PostMapping
    public ResponseEntity<PedidoResponseDto> crearPedido(@Valid @RequestBody PedidoRequestDto pedidoRequestDto) {
        Pedido pedido = mapperWebPedido.toDomain(pedidoRequestDto);
        Pedido pedidoCreado = pedidoRepository.crearPedido(pedido);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapperWebPedido.toDto(pedidoCreado));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> obtenerPedidoFecha(@RequestParam(name = "fecha") LocalDate fecha) {
        List<Pedido> pedidos = pedidoRepository.listarPedidoPorFecha(fecha);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mapperWebPedido.toDto(pedidos));
    }
}
