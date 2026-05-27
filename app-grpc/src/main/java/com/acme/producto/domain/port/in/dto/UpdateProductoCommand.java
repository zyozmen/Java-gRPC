package com.acme.producto.domain.port.in.dto;

import java.math.BigDecimal;

public record UpdateProductoCommand(String nombre, String descripcion, BigDecimal precio) {
}