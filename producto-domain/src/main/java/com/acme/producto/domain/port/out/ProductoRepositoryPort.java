package com.acme.producto.domain.port.out;

import com.acme.producto.domain.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepositoryPort {

    Producto save(Producto producto);

    Optional<Producto> findById(String id);

    List<Producto> findAll();

    boolean existsById(String id);

    void deleteById(String id);
}
