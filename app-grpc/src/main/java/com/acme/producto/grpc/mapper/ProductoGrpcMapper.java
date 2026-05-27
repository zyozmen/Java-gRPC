package com.acme.producto.grpc.mapper;

import com.acme.producto.domain.exception.DomainValidationException;
import com.acme.producto.domain.model.Producto;
import com.acme.producto.grpc.proto.ProductoMessage;

import java.math.BigDecimal;

public final class ProductoGrpcMapper {

    private ProductoGrpcMapper() {
    }

    public static ProductoMessage toProto(Producto producto) {
        return ProductoMessage.newBuilder()
                .setId(producto.getId())
                .setNombre(producto.getNombre())
                .setDescripcion(producto.getDescripcion() == null ? "" : producto.getDescripcion())
                .setPrecio(producto.getPrecio().toPlainString())
                .build();
    }

    public static BigDecimal parsePrecio(String precio) {
        if (precio == null || precio.isBlank()) {
            throw new DomainValidationException("El precio del producto es obligatorio");
        }
        try {
            return new BigDecimal(precio);
        } catch (NumberFormatException ex) {
            throw new DomainValidationException("Formato de precio inválido");
        }
    }
}
