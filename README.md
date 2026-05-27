# Java gRPC — CRUD de Productos con gRPC y Arquitectura Hexagonal

Servicio backend construido con **Spring Boot** y **gRPC** que expone un CRUD completo de productos. Aplica **arquitectura hexagonal (Ports & Adapters)** para mantener el dominio de negocio independiente del framework, la base de datos y el protocolo de comunicación.

---

## Stack Tecnológico

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 17 |
| Framework | Spring Boot 3.3.5 |
| Protocolo | gRPC + Protocol Buffers 3.25.5 |
| Servidor gRPC | grpc-server-spring-boot-starter 3.1.0 |
| Persistencia | Spring Data JPA + Hibernate |
| Base de datos | MySQL 8 |
| Build | Maven 3 |

---

## Arquitectura

El proyecto sigue la **arquitectura hexagonal** dentro de un único módulo Maven (`app-grpc`):

```
com.acme.producto
├── domain
│   ├── model/          → Entidad de dominio: Producto
│   ├── port/in/        → Interfaces de casos de uso (CreateProductoUseCase, etc.)
│   ├── port/out/       → Puerto de repositorio: ProductoRepositoryPort
│   ├── usecase/        → Implementación de los casos de uso
│   └── exception/      → Excepciones de dominio
├── infrastructure
│   ├── persistence/    → Entidad JPA, mapper, Spring Data repository
│   ├── adapter/        → Adaptador que implementa ProductoRepositoryPort
│   └── config/         → Configuración Spring: registra los casos de uso como beans
└── grpc
    ├── GrpcApplication.java         → Entry point Spring Boot
    ├── service/                     → ProductoGrpcService (@GrpcService)
    ├── mapper/                      → Mapeo proto ↔ dominio
    └── error/                       → Manejo global de excepciones gRPC
```

**Flujo de una petición:**
```
Cliente gRPC → ProductoGrpcService → UseCase → ProductoRepositoryPort
                                                        ↓
                                          ProductoRepositoryAdapter (JPA)
                                                        ↓
                                                      MySQL
```

---

## Requisitos previos

- Java 17+
- Maven 3.8+
- MySQL 8 corriendo en `localhost:3306`

---

## Configuración

Edita `app-grpc/src/main/resources/application.yml` con tus credenciales de MySQL:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/products_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&createDatabaseIfNotExist=true
    username: TU_USUARIO
    password: TU_PASSWORD
  jpa:
    hibernate:
      ddl-auto: update

grpc:
  server:
    port: 9090
```

> La base de datos `products_db` se crea automáticamente si no existe.

---

## Cómo correr el proyecto

```bash
# 1. Compilar
mvn clean compile -pl app-grpc

# 2. Ejecutar
mvn spring-boot:run -pl app-grpc
```

El servidor gRPC queda escuchando en el puerto **9090**.

---

## Ejemplos de ejecución

Los ejemplos usan [`grpcurl`](https://github.com/fullstorydev/grpcurl). Asegúrate de tenerlo instalado.

### Crear un producto
```bash
grpcurl -plaintext -d '{
  "nombre": "Laptop",
  "descripcion": "Laptop gaming 16GB RAM",
  "precio": "1299.99"
}' localhost:9090 producto.ProductoService/CreateProducto
```
**Respuesta:**
```json
{
  "id": "a1b2c3d4-...",
  "nombre": "Laptop",
  "descripcion": "Laptop gaming 16GB RAM",
  "precio": "1299.99"
}
```

---

### Obtener todos los productos
```bash
grpcurl -plaintext -d '{}' localhost:9090 producto.ProductoService/GetAllProductos
```
**Respuesta:**
```json
{
  "productos": [
    { "id": "a1b2c3d4-...", "nombre": "Laptop", "descripcion": "Laptop gaming 16GB RAM", "precio": "1299.99" }
  ]
}
```

---

### Obtener un producto por ID
```bash
grpcurl -plaintext -d '{"id": "a1b2c3d4-..."}' localhost:9090 producto.ProductoService/GetProductoById
```

---

### Actualizar un producto
```bash
grpcurl -plaintext -d '{
  "id": "a1b2c3d4-...",
  "nombre": "Laptop Pro",
  "descripcion": "Laptop gaming 32GB RAM",
  "precio": "1599.99"
}' localhost:9090 producto.ProductoService/UpdateProducto
```

---

### Eliminar un producto
```bash
grpcurl -plaintext -d '{"id": "a1b2c3d4-..."}' localhost:9090 producto.ProductoService/DeleteProducto
```
**Respuesta:**
```json
{ "deleted": true }
```

---

## Manejo de errores

| Excepción de dominio | Código gRPC |
|---|---|
| `ProductoNotFoundException` | `NOT_FOUND` |
| `DomainValidationException` | `INVALID_ARGUMENT` |
| Cualquier otra excepción | `INTERNAL` |

---

## Estructura del proyecto

```
Java gRPC/
├── pom.xml                  → POM padre
└── app-grpc/
    ├── pom.xml
    └── src/main/
        ├── java/com/acme/producto/   → Código fuente (dominio + infra + gRPC)
        ├── proto/producto.proto      → Contrato gRPC
        └── resources/application.yml
```
