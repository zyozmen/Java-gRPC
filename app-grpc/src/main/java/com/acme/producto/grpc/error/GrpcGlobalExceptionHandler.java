package com.acme.producto.grpc.error;

import com.acme.producto.domain.exception.DomainValidationException;
import com.acme.producto.domain.exception.ProductoNotFoundException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class GrpcGlobalExceptionHandler {

    @GrpcExceptionHandler(ProductoNotFoundException.class)
    public StatusRuntimeException handleNotFound(ProductoNotFoundException ex) {
        return Status.NOT_FOUND
                .withDescription(ex.getMessage())
                .asRuntimeException();
    }

    @GrpcExceptionHandler(DomainValidationException.class)
    public StatusRuntimeException handleValidation(DomainValidationException ex) {
        return Status.INVALID_ARGUMENT
                .withDescription(ex.getMessage())
                .asRuntimeException();
    }

    @GrpcExceptionHandler(Exception.class)
    public StatusRuntimeException handleGeneric(Exception ex) {
        return Status.INTERNAL
                .withDescription("Error interno procesando la solicitud")
                .asRuntimeException();
    }
}
