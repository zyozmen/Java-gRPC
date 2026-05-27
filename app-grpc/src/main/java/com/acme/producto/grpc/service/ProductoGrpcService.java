package com.acme.producto.grpc.service;

import com.acme.producto.domain.port.in.CreateProductoUseCase;
import com.acme.producto.domain.port.in.DeleteProductoUseCase;
import com.acme.producto.domain.port.in.GetProductoUseCase;
import com.acme.producto.domain.port.in.UpdateProductoUseCase;
import com.acme.producto.domain.port.in.dto.CreateProductoCommand;
import com.acme.producto.domain.port.in.dto.UpdateProductoCommand;
import com.acme.producto.grpc.mapper.ProductoGrpcMapper;
import com.acme.producto.grpc.proto.CreateProductoRequest;
import com.acme.producto.grpc.proto.DeleteProductoRequest;
import com.acme.producto.grpc.proto.DeleteProductoResponse;
import com.acme.producto.grpc.proto.GetAllProductosRequest;
import com.acme.producto.grpc.proto.GetAllProductosResponse;
import com.acme.producto.grpc.proto.GetProductoByIdRequest;
import com.acme.producto.grpc.proto.ProductoMessage;
import com.acme.producto.grpc.proto.ProductoServiceGrpc;
import com.acme.producto.grpc.proto.UpdateProductoRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ProductoGrpcService extends ProductoServiceGrpc.ProductoServiceImplBase {

    private final CreateProductoUseCase createProductoUseCase;
    private final GetProductoUseCase getProductoUseCase;
    private final UpdateProductoUseCase updateProductoUseCase;
    private final DeleteProductoUseCase deleteProductoUseCase;

    public ProductoGrpcService(
            CreateProductoUseCase createProductoUseCase,
            GetProductoUseCase getProductoUseCase,
            UpdateProductoUseCase updateProductoUseCase,
            DeleteProductoUseCase deleteProductoUseCase
    ) {
        this.createProductoUseCase = createProductoUseCase;
        this.getProductoUseCase = getProductoUseCase;
        this.updateProductoUseCase = updateProductoUseCase;
        this.deleteProductoUseCase = deleteProductoUseCase;
    }

    @Override
    public void createProducto(CreateProductoRequest request, StreamObserver<ProductoMessage> responseObserver) {
        var created = createProductoUseCase.create(new CreateProductoCommand(
                request.getNombre(),
                request.getDescripcion(),
                ProductoGrpcMapper.parsePrecio(request.getPrecio())
        ));
        responseObserver.onNext(ProductoGrpcMapper.toProto(created));
        responseObserver.onCompleted();
    }

    @Override
    public void getProductoById(GetProductoByIdRequest request, StreamObserver<ProductoMessage> responseObserver) {
        var producto = getProductoUseCase.findById(request.getId());
        responseObserver.onNext(ProductoGrpcMapper.toProto(producto));
        responseObserver.onCompleted();
    }

    @Override
    public void getAllProductos(GetAllProductosRequest request, StreamObserver<GetAllProductosResponse> responseObserver) {
        var productos = getProductoUseCase.findAll().stream().map(ProductoGrpcMapper::toProto).toList();
        var response = GetAllProductosResponse.newBuilder().addAllProductos(productos).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateProducto(UpdateProductoRequest request, StreamObserver<ProductoMessage> responseObserver) {
        var updated = updateProductoUseCase.update(
                request.getId(),
                new UpdateProductoCommand(
                        request.getNombre(),
                        request.getDescripcion(),
                        ProductoGrpcMapper.parsePrecio(request.getPrecio())
                )
        );
        responseObserver.onNext(ProductoGrpcMapper.toProto(updated));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteProducto(DeleteProductoRequest request, StreamObserver<DeleteProductoResponse> responseObserver) {
        deleteProductoUseCase.delete(request.getId());
        responseObserver.onNext(DeleteProductoResponse.newBuilder().setDeleted(true).build());
        responseObserver.onCompleted();
    }
}
