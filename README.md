# Tiendas Urbano - Sistema de Microservicios

Sistema de e-commerce basado en arquitectura de microservicios con Spring Boot + MySQL.

## Integrantes del Equipo
- Integrante 1: MS1 (autenticacion) + MS2 (cliente) + MS3 (direccion)
- Integrante 2: MS4 (producto) + MS5 (inventario) + MS6 (promocion)
- Integrante 3: MS7 (carrito) + MS8 (pedido) + MS9 (pago)
- Integrante 4: MS10 (bodega) + MS11 (despacho) + MS12 (notificacion)
- Integrante 5: MS13 (facturacion) + MS14 (reporte) + MS15 (marketing)

## Microservicios y Puertos

| # | Servicio | Puerto | Base de datos |
|---|---|---|---|
| 1 | autenticacion | 8081 | db_auth |
| 2 | cliente | 8082 | db_clientes |
| 3 | direccion | 8083 | db_direcciones |
| 4 | producto | 8084 | db_productos |
| 5 | inventario | 8085 | db_inventario |
| 6 | promocion | 8086 | db_promociones |
| 7 | carrito | 8087 | db_carrito |
| 8 | pedido | 8088 | db_pedidos |
| 9 | pago | 8089 | db_pagos |
| 10 | bodega | 8090 | db_bodega |
| 11 | despacho | 8091 | db_despachos |
| 12 | notificacion | 8092 | db_notificaciones |
| 13 | facturacion | 8093 | db_facturacion |
| 14 | reporte | 8094 | db_reportes |
| 15 | marketing | 8095 | db_marketing |

## Pasos para ejecutar

1. Iniciar MySQL
2. Ejecutar `init_databases.sql` en MySQL Workbench
3. Abrir cada proyecto en IntelliJ IDEA (File > Open)
4. Ejecutar en orden: autenticacion → cliente → direccion → producto → inventario → luego el resto
5. Probar endpoints con Postman

## Tecnologías
- Java 21
- Spring Boot 3.4.5
- Spring Data JPA + Hibernate
- Spring Validation
- Spring WebFlux (WebClient)
- MySQL 8
- Lombok
