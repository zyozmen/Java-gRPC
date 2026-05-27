package com.acme.producto.domain.usecase;

import com.acme.producto.domain.exception.DomainValidationException;
import com.acme.producto.domain.exception.ProductoNotFoundException;
import com.acme.producto.domain.model.Producto;
import com.acme.producto.domain.port.in.CreateProductoUseCase;
import com.acme.producto.domain.port.in.DeleteProductoUseCase;
import com.acme.producto.domain.port.in.GetProductoUseCase;
import com.acme.producto.domain.port.in.UpdateProductoUseCase;
import com.acme.producto.domain.port.in.dto.CreateProductoCommand;
import com.acme.producto.domain.port.in.dto.UpdateProductoCommand;
import com.acme.producto.domain.port.out.ProductoRepositoryPort;

import java.math.BigDecimal;
import java.util.List;

public class ProductoUseCaseService implements CreateProductoUseCase, GetProductoUseCase, UpdateProductoUseCase, DeleteProductoUseCase {

    private final ProductoRepositoryPort productoRepositoryPort;

    public ProductoUseCaseService(ProductoRepositoryPort productoRepositoryPort) {
        this.productoRepositoryPort = productoRepositoryPort;
    }

    @Override
    public Producto create(CreateProductoCommand command) {
        validate(command.nombre(), command.precio());
        Producto producto = Producto.crearNuevo(command.nombre(), command.descripcion(), command.precio());
        return productoRepositoryPort.save(producto);
    }

    @Override
    public Producto findById(String id) {
        return productoRepositoryPort.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
    }

    @Override
    public List<Producto> findAll() {
        return productoRepositoryPort.findAll();
    }

    @Override
    public Producto update(String id, UpdateProductoCommand command) {
        validate(command.nombre(), command.precio());

        Producto producto = productoRepositoryPort.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        producto.setNombre(command.nombre());
        producto.setDescripcion(command.descripcion());
        producto.setPrecio(command.precio());

        return productoRepositoryPort.save(producto);
    }

    @Override
    public void delete(String id) {
        if (!productoRepositoryPort.existsById(id)) {
            throw new ProductoNotFoundException(id);
        }
        productoRepositoryPort.deleteById(id);
    }

    private void validate(String nombre, BigDecimal precio) {
        if (nombre == null || nombre.isBlank()) {
            throw new DomainValidationException("El nombre del producto es obligatorio");
        }
        if (precio == null) {
            throw new DomainValidationException("El precio del producto es obligatorio");
        }
        if (precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainValidationException("El precio del producto no puede ser negativo");
        }
    }
}