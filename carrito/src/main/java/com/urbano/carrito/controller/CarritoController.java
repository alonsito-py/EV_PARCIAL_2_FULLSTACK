package com.urbano.carrito.controller;
import com.urbano.carrito.dto.ItemCarritoDTO;
import com.urbano.carrito.model.Carrito;
import com.urbano.carrito.service.CarritoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "Carrito", description = "Gestión del carrito de compras")
@RestController @RequestMapping("/api/v1/carrito")
public class CarritoController {
    @Autowired private CarritoService service;
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Operación GET")
    public ResponseEntity<Carrito> getCarrito(@PathVariable Long clienteId) { return ResponseEntity.ok(service.getOrCreate(clienteId)); }
    @PostMapping("/cliente/{clienteId}/items")
    @Operation(summary = "Operación POST")
    public ResponseEntity<Carrito> agregar(@PathVariable Long clienteId, @Valid @RequestBody ItemCarritoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.agregarItem(clienteId, dto)); }
    @DeleteMapping("/{carritoId}/vaciar")
    @Operation(summary = "Operación DELETE")
    public ResponseEntity<Void> vaciar(@PathVariable Long carritoId) { service.vaciar(carritoId); return ResponseEntity.noContent().build(); }
    @PatchMapping("/{carritoId}/procesar")
    @Operation(summary = "Operación PATCH")
    public ResponseEntity<Void> procesar(@PathVariable Long carritoId) { service.procesar(carritoId); return ResponseEntity.ok().build(); }
}