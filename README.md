# Tiendas Urbano — Sistema de E-commerce con Microservicios

Plataforma de comercio electrónico de ropa femenina desarrollada con arquitectura de microservicios.  
Cada servicio es independiente, posee su propia base de datos MySQL y expone una API REST documentada con Swagger/OpenAPI.

---

## Equipo de desarrollo

| Estudiante | Microservicios asignados |
|---|---|
| María Pía Sepúlveda | autenticacion · cliente · direccion |
| Pablo Rojas | producto · inventario · promocion |
| Luis Veas | carrito · pedido · pago |
| Alonso Estay | bodega · despacho · notificacion |
| Andrés Carrasco | facturacion · reporte · marketing |

---

## Microservicios implementados

| # | Microservicio | Puerto | Base de Datos | Descripción |
|---|---|---|---|---|
| 1 | autenticacion | 8081 | db_auth | Registro, login y gestión de usuarios/roles |
| 2 | cliente | 8082 | db_clientes | Gestión de clientes y verificación de cuenta |
| 3 | direccion | 8083 | db_direcciones | Direcciones de entrega por cliente |
| 4 | producto | 8084 | db_productos | Catálogo de productos y categorías |
| 5 | inventario | 8085 | db_inventario | Control de stock por SKU |
| 6 | promocion | 8086 | db_promociones | Promociones y cupones de descuento |
| 7 | carrito | 8087 | db_carrito | Carrito de compras activo por cliente |
| 8 | pedido | 8088 | db_pedidos | Ciclo de vida de pedidos |
| 9 | pago | 8089 | db_pagos | Transacciones de pago (simulación Webpay) |
| 10 | bodega | 8090 | db_bodega | Movimientos de inventario en bodega |
| 11 | despacho | 8091 | db_despachos | Guías de despacho y seguimiento |
| 12 | notificacion | 8092 | db_notificaciones | Envío y registro de notificaciones |
| 13 | facturacion | 8093 | db_facturacion | Emisión de boletas electrónicas con IVA |
| 14 | reporte | 8094 | db_reportes | Cierres de venta diaria por periodo |
| 15 | marketing | 8095 | db_marketing | Campañas en redes sociales |
| — | **gateway** | **8080** | — | **API Gateway central (Spring Cloud Gateway)** |

---

## API Gateway — Rutas principales

El Gateway centraliza todas las peticiones en `http://localhost:8080`.

| Prefijo de ruta | Microservicio destino | Puerto |
|---|---|---|
| `/api/v1/usuarios/**` | autenticacion | 8081 |
| `/api/v1/clientes/**` | cliente | 8082 |
| `/api/v1/direcciones/**` | direccion | 8083 |
| `/api/v1/productos/**` | producto | 8084 |
| `/api/v1/inventario/**` | inventario | 8085 |
| `/api/v1/promociones/**` | promocion | 8086 |
| `/api/v1/carrito/**` | carrito | 8087 |
| `/api/v1/pedidos/**` | pedido | 8088 |
| `/api/v1/pagos/**` | pago | 8089 |
| `/api/v1/bodega/**` | bodega | 8090 |
| `/api/v1/despacho/**` | despacho | 8091 |
| `/api/v1/notificaciones/**` | notificacion | 8092 |
| `/api/v1/facturacion/**` | facturacion | 8093 |
| `/api/v1/reportes/**` | reporte | 8094 |
| `/api/v1/marketing/**` | marketing | 8095 |

Todos los endpoints también disponen de versión V2 con HATEOAS: `/api/v2/{recurso}/**`

---

## Documentación Swagger / OpenAPI

Cada microservicio expone Swagger UI en:

```
http://localhost:{puerto}/doc/swagger-ui/index.html
```

| Microservicio | URL Swagger |
|---|---|
| autenticacion | http://localhost:8081/doc/swagger-ui/index.html |
| cliente | http://localhost:8082/doc/swagger-ui/index.html |
| direccion | http://localhost:8083/doc/swagger-ui/index.html |
| producto | http://localhost:8084/doc/swagger-ui/index.html |
| inventario | http://localhost:8085/doc/swagger-ui/index.html |
| promocion | http://localhost:8086/doc/swagger-ui/index.html |
| carrito | http://localhost:8087/doc/swagger-ui/index.html |
| pedido | http://localhost:8088/doc/swagger-ui/index.html |
| pago | http://localhost:8089/doc/swagger-ui/index.html |
| bodega | http://localhost:8090/doc/swagger-ui/index.html |
| despacho | http://localhost:8091/doc/swagger-ui/index.html |
| notificacion | http://localhost:8092/doc/swagger-ui/index.html |
| facturacion | http://localhost:8093/doc/swagger-ui/index.html |
| reporte | http://localhost:8094/doc/swagger-ui/index.html |
| marketing | http://localhost:8095/doc/swagger-ui/index.html |
| **gateway (agregado)** | **http://localhost:8080/doc/swagger-ui/index.html** |

---

## Tecnologías

- Java 21
- Spring Boot 3.4.5
- Spring Cloud Gateway 2024.0.1
- Spring Data JPA + Hibernate
- Spring Boot Validation (Bean Validation)
- Spring WebFlux (WebClient para comunicación entre microservicios)
- Spring HATEOAS
- SpringDoc OpenAPI (Swagger UI)
- JUnit 5 + Mockito (pruebas unitarias)
- MySQL 8
- Lombok
- Docker + Docker Compose

---

## Estructura del proyecto

```
urbano_version_final/
├── gateway/           → :8080  API Gateway (Spring Cloud Gateway)
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
├── marketing/         → :8095  db_marketing
├── docker-compose.yml
└── init_databases.sql
```

Cada microservicio sigue el patrón **CSR (Controller–Service–Repository/Model)**:
```
src/
├── main/
│   ├── java/com/urbano/{ms}/
│   │   ├── controller/     ← Recibe peticiones HTTP
│   │   ├── service/        ← Lógica de negocio
│   │   ├── repository/     ← Acceso a datos (JPA)
│   │   ├── model/          ← Entidades JPA
│   │   ├── dto/            ← Data Transfer Objects
│   │   ├── exception/      ← Manejo global de errores
│   │   └── config/         ← SwaggerConfig, WebClientConfig
│   └── resources/
│       ├── application.yml         ← Config base + perfil activo
│       ├── application-dev.yml     ← Overrides para desarrollo local
│       └── application-prod.yml    ← Overrides para producción (PaaS)
└── test/
    └── java/com/urbano/{ms}/
        └── service/
            └── {Entidad}ServiceTest.java
```

---

## Ejecución local (IntelliJ IDEA)

### Requisitos

- Java 21 (JDK)
- MySQL 8 corriendo en localhost:3306
- IntelliJ IDEA con plugins Maven
- Maven 3.9+

### 1. Crear bases de datos

```bash
mysql -u root -p < init_databases.sql
```

O ejecutar manualmente en MySQL Workbench:

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

-- Datos iniciales requeridos
INSERT INTO db_auth.roles (nombre) VALUES ('ADMIN'), ('CLIENTE');
```

### 2. Abrir el proyecto

`File → Open` → carpeta raíz del proyecto. IntelliJ detecta los 16 `pom.xml` automáticamente.

### 3. Verificar perfil activo

En cada `application.yml` el perfil activo es `dev` por defecto:

```yaml
spring:
  profiles:
    active: dev
```

### 4. Ejecutar en orden

Lanzar cada `*Application.java` con clic derecho → Run:

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
| 16 | **gateway** | **8080** |

### 5. Ejecutar pruebas unitarias

En IntelliJ: clic derecho sobre la carpeta `src/test` de cada microservicio → **Run All Tests**

O desde terminal:
```bash
cd producto && mvn test
```

---

## Ejecución con Docker Compose

```bash
# Construir y levantar todo el ecosistema
docker-compose up --build

# Solo levantar (si ya fue construido)
docker-compose up

# Detener
docker-compose down

# Ver logs de un servicio
docker-compose logs -f producto
```

El gateway queda disponible en `http://localhost:8080`.

---

## Despliegue en Railway

1. Crear proyecto nuevo en [railway.app](https://railway.app)
2. Por cada microservicio: **New Service → GitHub Repo → seleccionar carpeta**
3. Configurar variables de entorno en el panel:

```env
SPRING_PROFILE=prod
DB_URL=jdbc:mysql://{host_railway}:3306/{db}
DB_USER=root
DB_PASS={password_generado}
PORT=8081
```

4. Railway detecta el `Dockerfile` y despliega automáticamente
5. Copiar las URLs públicas generadas y configurarlas en el Gateway como variables de entorno

---

## Asignatura

**DSY1103 — Desarrollo FullStack 1**  
Evaluación Sumativa 3 — Arquitectura de Microservicios
