package com.acme.producto.infrastructure.config;

import com.acme.producto.domain.port.out.ProductoRepositoryPort;
import com.acme.producto.domain.usecase.ProductoUseCaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainUseCaseConfig {

    @Bean
    public ProductoUseCaseService productoUseCaseService(ProductoRepositoryPort productoRepositoryPort) {
        return new ProductoUseCaseService(productoRepositoryPort);
    }
}