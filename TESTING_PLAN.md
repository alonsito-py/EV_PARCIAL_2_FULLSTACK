# Plan de Pruebas Unitarias — Tiendas Urbano

## Objetivo

Validar la lógica de negocio de cada microservicio mediante pruebas unitarias aisladas,
utilizando JUnit 5 + Mockito, sin dependencias externas (sin base de datos real, sin red).

## Estructura

Cada microservicio contiene su clase de test en:
```
src/test/java/com/urbano/{ms}/service/{Entidad}ServiceTest.java
```

## Convención: Given–When–Then (AAA)

```java
@Test
@DisplayName("descripción legible del caso")
void testNombreMetodo_escenario() {
    // Given (Arrange) — configurar mocks y datos de entrada
    when(repo.findById(1L)).thenReturn(Optional.of(entidad));

    // When (Act) — ejecutar el método bajo prueba
    Entidad result = service.getById(1L);

    // Then (Assert) — verificar el resultado
    assertNotNull(result);
    assertEquals("valor esperado", result.getCampo());
    verify(repo).findById(1L);
}
```

## Cobertura por microservicio

| Microservicio | Clase de test | Casos cubiertos |
|---|---|---|
| autenticacion | UsuarioServiceTest | getAll, getById (existe/no existe), delete (soft) |
| bodega | BodegaServiceTest | getAll, getBySku, registrar |
| carrito | CarritoServiceTest | getOrCreate (existente/nuevo), vaciar, vaciar (no existe), procesar |
| cliente | ClienteServiceTest | getAll, getById (existe/no existe), create (éxito/correo duplicado), verificar, delete |
| despacho | DespachoServiceTest | getAll, getById (existe/no existe), cambiarEstado |
| direccion | DireccionServiceTest | getByCliente, getById (existe/no existe), create, delete |
| facturacion | FacturacionServiceTest | getAll, getByPedido, emitir (IVA 19%), emitir (total correcto) |
| inventario | InventarioServiceTest | getAll, getBySku (existe/no existe), reducirStock, aumentarStock |
| marketing | MarketingServiceTest | getAll, getById (existe/no existe), publicar |
| notificacion | NotificacionServiceTest | getAll, getByDestinatario, enviar |
| pago | PagoServiceTest | getByPedido, procesar (aprobado), procesar (rechazado) |
| pedido | PedidoServiceTest | getAll, getById (existe/no existe), getByCliente, cambiarEstado |
| producto | ProductoServiceTest | getAll, getById (existe/no existe), getBySku (existe/no existe), create (éxito/SKU duplicado), delete |
| promocion | PromocionServiceTest | getAll, createCupon (éxito/duplicado), validarCupon (válido/no existe/agotado) |
| reporte | ReporteServiceTest | getAll, getPorPeriodo, registrar |

## Reglas de negocio críticas validadas

- **Producto**: SKU único — lanzar excepción si ya existe
- **Cliente**: correo único — lanzar excepción si ya está registrado
- **Cupón**: validar que no esté agotado (usosActuales >= usosMaximos)
- **Facturación**: IVA calculado correctamente (19%) y total = neto + IVA
- **Pago**: token nulo → estado RECHAZADO; token presente → estado APROBADO
- **Soft delete**: producto, cliente y usuario se desactivan sin eliminar de BD
- **Carrito**: crear nuevo si no existe uno activo para el cliente

## Ejecución

```bash
# Un microservicio específico
cd cliente && mvn test

# Con reporte de cobertura
mvn test jacoco:report
# Reporte en: target/site/jacoco/index.html
```
