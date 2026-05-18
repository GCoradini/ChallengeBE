package com.mercantil.pizzeria.infra.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "productos")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    private String nombre;

    @Column(name = "descripcion_corta")
    private String descripcionCorta;

    @Column(name = "descripcion_larga")
    private String descripcionLarga;

    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoDetalleEntity> detalles = new ArrayList<>();
}
