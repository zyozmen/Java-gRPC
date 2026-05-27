package com.acme.producto.domain.port.in;

import com.acme.producto.domain.model.Producto;

import java.util.List;

public interface GetProductoUseCase {

    Producto findById(String id);

    List<Producto> findAll();
}
