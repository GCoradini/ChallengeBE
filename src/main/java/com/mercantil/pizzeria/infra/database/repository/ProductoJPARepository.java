package com.mercantil.pizzeria.infra.database.repository;

import com.mercantil.pizzeria.infra.database.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProductoJPARepository extends JpaRepository<ProductoEntity, UUID> {
}
