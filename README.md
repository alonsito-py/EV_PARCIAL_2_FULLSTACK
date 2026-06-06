# Tiendas Urbano — Sistema de E-commerce

Plataforma de comercio electrónico de ropa femenina desarrollada con arquitectura de microservicios. Cada microservicio es independiente, tiene su propia base de datos MySQL y expone una API REST.

---

## Equipo de desarrollo

| Estudiante | Microservicios |
|---|---|
| María Pía Sepúlveda | autenticacion · cliente · direccion |
| Pablo Rojas | producto · inventario · promocion |
| Luis Veas | carrito · pedido · pago |
| Alonso Estay | bodega · despacho · notificacion |
| Andrés Carrasco | facturacion · reporte · marketing |

---

## Tecnologías

- Java 21
- Spring Boot 3.4.5
- Spring Data JPA + Hibernate
- Spring Boot Validation (Bean Validation)
- Spring WebFlux (WebClient)
- MySQL 8
- Lombok

---

## Funcionalidades implementadas

### Autenticación y usuarios
- Registro de usuarios con rol asignado (ADMIN / CLIENTE)
- Login por username y password
- Desactivación de usuarios con borrado lógico

### Clientes
- Registro, actualización y desactivación de clientes
- Verificación de email
- Validación de correo único y formato válido

### Direcciones
- Gestión de múltiples direcciones por cliente
- Validación de existencia del cliente mediante WebClient

### Productos y categorías
- Catálogo de productos con talla, color, precio y categoría
- Búsqueda por ID, SKU o categoría
- Desactivación de productos con borrado lógico

### Inventario
- Control de stock por SKU
- Reducción y aumento de stock
- Alerta automática al alcanzar stock mínimo

### Promociones y cupones
- Gestión de promociones por rango de fechas
- Cupones con código único y límite de usos
- Validación y consumo de cupón en una sola operación transaccional

### Carrito de compras
- Carrito activo por cliente con ítems asociados
- Verificación de stock antes de agregar ítems (WebClient a inventario)
- Vaciado y procesado de carrito

### Pedidos
- Creación de pedido con múltiples líneas de detalle
- Cálculo automático del total
- Ciclo de estados: PENDIENTE → PAGADO → DESPACHADO → ENTREGADO

### Pagos
- Registro de transacciones con simulación de Webpay (Transbank)
- Actualización automática del estado del pedido al aprobar (WebClient)
- Trazabilidad de intentos aprobados y rechazados

### Bodega
- Registro de movimientos de inventario (ENTRADA / SALIDA)
- Actualización automática del stock (WebClient a inventario)
- Historial de movimientos por SKU

### Despacho
- Creación de guías de despacho con código de seguimiento automático
- Actualización automática del pedido a DESPACHADO (WebClient)
- Ciclo de estados: PREPARANDO → EN_RUTA → ENTREGADO

### Notificaciones
- Envío y registro de notificaciones por tipo de plantilla
- Historial de notificaciones por destinatario
- Tipos: REGISTRO, COMPRA_APROBADA, DESPACHO

### Facturación
- Emisión de boletas electrónicas con folio único generado automáticamente
- Cálculo automático de IVA (19%) y total
- Consulta de boletas por pedido

### Reportes
- Registro de cierres de venta diarios
- Consulta de reportes por rango de fechas

### Marketing
- Gestión de campañas en redes sociales (Instagram, Facebook, TikTok)
- Ciclo de estados: PROGRAMADA → PUBLICADA

---

## Estructura del proyecto

```
tienda_urbano_15microservicios/
├── autenticacion/     → :8081  db_auth
├── cliente/           → :8082  db_clientes
├── direccion/         → :8083  db_direcciones
├── producto/          → :8084  db_productos
├── inventario/        → :8085  db_inventario
├── promocion/         → :8086  db_promociones
├── carrito/           → :8087  db_carrito
├── pedido/            → :8088  db_pedidos
├── pago/              → :8089  db_pagos
├── bodega/            → :8090  db_bodega
├── despacho/          → :8091  db_despachos
├── notificacion/      → :8092  db_notificaciones
├── facturacion/       → :8093  db_facturacion
├── reporte/           → :8094  db_reportes
└── marketing/         → :8095  db_marketing
```

Cada microservicio contiene los paquetes: `controller`, `service`, `repository`, `model`, `dto`, `exception`, `config`.

---

## Pasos para ejecutar

### Requisitos previos

- Java 21
- MySQL 8
- IntelliJ IDEA
- Maven

### 1. Crear las bases de datos

Ejecutar el archivo `init_databases.sql` en MySQL Workbench, DBeaver o cualquier cliente MySQL:

```sql
CREATE DATABASE IF NOT EXISTS db_auth;
CREATE DATABASE IF NOT EXISTS db_clientes;
CREATE DATABASE IF NOT EXISTS db_direcciones;
CREATE DATABASE IF NOT EXISTS db_productos;
CREATE DATABASE IF NOT EXISTS db_inventario;
CREATE DATABASE IF NOT EXISTS db_promociones;
CREATE DATABASE IF NOT EXISTS db_carrito;
CREATE DATABASE IF NOT EXISTS db_pedidos;
CREATE DATABASE IF NOT EXISTS db_pagos;
CREATE DATABASE IF NOT EXISTS db_bodega;
CREATE DATABASE IF NOT EXISTS db_despachos;
CREATE DATABASE IF NOT EXISTS db_notificaciones;
CREATE DATABASE IF NOT EXISTS db_facturacion;
CREATE DATABASE IF NOT EXISTS db_reportes;
CREATE DATABASE IF NOT EXISTS db_marketing;
```

### 2. Configurar credenciales de MySQL

En el archivo `src/main/resources/application.properties` de cada microservicio ajustar si es necesario:

```properties
spring.datasource.username=root
spring.datasource.password=
```

### 3. Insertar roles iniciales

Ejecutar en la base de datos `db_auth`:

```sql
INSERT INTO roles (nombre) VALUES ('ADMIN');
INSERT INTO roles (nombre) VALUES ('CLIENTE');
```

### 4. Abrir los proyectos en IntelliJ

`File → Open` → seleccionar la carpeta `tienda_urbano_15microservicios`. IntelliJ detecta los 15 `pom.xml` automáticamente.

### 5. Ejecutar los microservicios

Ejecutar en el siguiente orden usando clic derecho sobre la clase `*Application.java` → Run:

| Orden | Microservicio | Puerto |
|---|---|---|
| 1 | autenticacion | 8081 |
| 2 | cliente | 8082 |
| 3 | producto | 8084 |
| 4 | inventario | 8085 |
| 5 | direccion | 8083 |
| 6 | promocion | 8086 |
| 7 | carrito | 8087 |
| 8 | pedido | 8088 |
| 9 | pago | 8089 |
| 10 | bodega | 8090 |
| 11 | despacho | 8091 |
| 12 | notificacion | 8092 |
| 13 | facturacion | 8093 |
| 14 | reporte | 8094 |
| 15 | marketing | 8095 |

Spring Boot crea las tablas automáticamente al arrancar cada microservicio (`ddl-auto=update`).

### 6. Probar con Postman

Los endpoints siguen la convención `/api/v1/{recurso}`. Ejemplo:

```
GET  http://localhost:8082/api/v1/clientes
POST http://localhost:8082/api/v1/clientes
```

---

## Asignatura
DSY1103-001V — Desarrollo FullStack 1  
