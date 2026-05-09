package com.mercantil.pizzeria.infra.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "pedidos_detalle")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDetalleEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "pedido_cabecera_id", nullable = false)
    private PedidoCabeceraEntity pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private ProductoEntity producto;

    private Integer cantidad;

    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;

}
