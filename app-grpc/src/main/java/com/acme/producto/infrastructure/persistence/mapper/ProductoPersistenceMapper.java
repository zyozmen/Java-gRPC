package com.acme.producto.infrastructure.persistence.mapper;

import com.acme.producto.domain.model.Producto;
import com.acme.producto.infrastructure.persistence.entity.ProductoJpaEntity;

public final class ProductoPersistenceMapper {

    private ProductoPersistenceMapper() {
    }

    public static ProductoJpaEntity toJpaEntity(Producto producto) {
        return new ProductoJpaEntity(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio()
        );
    }

    public static Producto toDomain(ProductoJpaEntity entity) {
        return new Producto(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getPrecio()
        );
    }
}
