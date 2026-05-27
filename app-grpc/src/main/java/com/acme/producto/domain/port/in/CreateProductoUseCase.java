package com.acme.producto.domain.port.in;

import com.acme.producto.domain.model.Producto;
import com.acme.producto.domain.port.in.dto.CreateProductoCommand;

public interface CreateProductoUseCase {

    Producto create(CreateProductoCommand command);
}