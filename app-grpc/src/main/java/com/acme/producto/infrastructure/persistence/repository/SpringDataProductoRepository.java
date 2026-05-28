package com.acme.producto.infrastructure.persistence.repository;

import com.acme.producto.infrastructure.persistence.entity.ProductoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataProductoRepository extends JpaRepository<ProductoJpaEntity, String> {
}
