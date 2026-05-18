package com.mercantil.pizzeria.infra.database.repository;

import com.mercantil.pizzeria.infra.database.entity.PedidoCabeceraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PedidoCabeceraJPARepository extends JpaRepository<PedidoCabeceraEntity, UUID> {
    List<PedidoCabeceraEntity> findAllByFechaAlta(LocalDate fecha);
}
