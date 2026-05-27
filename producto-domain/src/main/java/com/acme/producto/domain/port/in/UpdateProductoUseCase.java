package com.acme.producto.domain.port.in;

import com.acme.producto.domain.model.Producto;
import com.acme.producto.domain.port.in.dto.UpdateProductoCommand;

public interface UpdateProductoUseCase {

    Producto update(String id, UpdateProductoCommand command);
}
