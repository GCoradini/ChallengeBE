package com.mercantil.pizzeria.infra.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pedidos_cabecera")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCabeceraEntity {

    @Id
    @GeneratedValue(generator = "pedidoCabeceraUuid")
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    private String direccion;

    private String email;

    private String telefono;

    private LocalTime horario;

    @Column(name = "fecha_alta")
    private LocalDate fechaAlta;

    @Column(name = "monto_total")
    private BigDecimal montoTotal;

    @Column(name = "aplico_descuento")
    private Boolean aplicoDescuento;

    private String estado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PedidoDetalleEntity> detalles = new ArrayList<>();

    public void addDetalle(PedidoDetalleEntity detalle) {
        detalles.add(detalle);
        detalle.setPedido(this);
    }

}
