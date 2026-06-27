package com.urbano.producto.controller;

import com.urbano.producto.dto.ProductoDTO;
import com.urbano.producto.model.Producto;
import com.urbano.producto.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "Gestión del catálogo de productos Urbano")
public class ProductoController {

    @Autowired private ProductoService service;

    @GetMapping
    @Operation(summary = "Listar productos activos", description = "Retorna todos los productos con estado activo")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public ResponseEntity<List<Producto>> getAll() { return ResponseEntity.ok(service.getAll()); }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Producto> getById(@Parameter(description = "ID del producto") @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/sku/{sku}")
    @Operation(summary = "Obtener producto por SKU")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "SKU no encontrado")
    })
    public ResponseEntity<Producto> getBySku(@Parameter(description = "SKU del producto") @PathVariable String sku) {
        return ResponseEntity.ok(service.getBySku(sku));
    }

    @GetMapping("/categoria/{catId}")
    @Operation(summary = "Listar productos por categoría")
    public ResponseEntity<List<Producto>> getByCategoria(@Parameter(description = "ID de la categoría") @PathVariable Long catId) {
        return ResponseEntity.ok(service.getByCategoria(catId));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo producto")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Producto creado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o SKU duplicado")
    })
    public ResponseEntity<Producto> create(@Valid @RequestBody ProductoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto")
    @ApiResponse(responseCode = "200", description = "Producto actualizado")
    public ResponseEntity<Producto> update(@PathVariable Long id, @Valid @RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar producto (soft delete)")
    @ApiResponse(responseCode = "204", description = "Producto desactivado")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
