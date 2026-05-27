package com.acme.producto.infrastructure.persistence.repository;

import com.acme.producto.infrastructure.persistence.entity.ProductoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductoRepository extends JpaRepository<ProductoJpaEntity, String> {
}
