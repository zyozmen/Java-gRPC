package com.acme.producto.infrastructure.adapter;

import com.acme.producto.domain.model.Producto;
import com.acme.producto.domain.port.out.ProductoRepositoryPort;
import com.acme.producto.infrastructure.persistence.mapper.ProductoPersistenceMapper;
import com.acme.producto.infrastructure.persistence.repository.SpringDataProductoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepositoryAdapter implements ProductoRepositoryPort {

    private final SpringDataProductoRepository springDataProductoRepository;

    public ProductoRepositoryAdapter(SpringDataProductoRepository springDataProductoRepository) {
        this.springDataProductoRepository = springDataProductoRepository;
    }

    @Override
    public Producto save(Producto producto) {
        var saved = springDataProductoRepository.save(ProductoPersistenceMapper.toJpaEntity(producto));
        return ProductoPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Producto> findById(String id) {
        return springDataProductoRepository.findById(id).map(ProductoPersistenceMapper::toDomain);
    }

    @Override
    public List<Producto> findAll() {
        return springDataProductoRepository.findAll().stream().map(ProductoPersistenceMapper::toDomain).toList();
    }

    @Override
    public boolean existsById(String id) {
        return springDataProductoRepository.existsById(id);
    }

    @Override
    public void deleteById(String id) {
        springDataProductoRepository.deleteById(id);
    }
}
