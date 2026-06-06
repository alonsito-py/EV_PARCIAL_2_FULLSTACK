package com.urbano.carrito.controller;
import com.urbano.carrito.dto.ItemCarritoDTO;
import com.urbano.carrito.model.Carrito;
import com.urbano.carrito.service.CarritoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/carrito")
public class CarritoController {
    @Autowired private CarritoService service;
    @GetMapping("/cliente/{clienteId}") public ResponseEntity<Carrito> getCarrito(@PathVariable Long clienteId) { return ResponseEntity.ok(service.getOrCreate(clienteId)); }
    @PostMapping("/cliente/{clienteId}/items") public ResponseEntity<Carrito> agregar(@PathVariable Long clienteId, @Valid @RequestBody ItemCarritoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.agregarItem(clienteId, dto)); }
    @DeleteMapping("/{carritoId}/vaciar") public ResponseEntity<Void> vaciar(@PathVariable Long carritoId) { service.vaciar(carritoId); return ResponseEntity.noContent().build(); }
    @PatchMapping("/{carritoId}/procesar") public ResponseEntity<Void> procesar(@PathVariable Long carritoId) { service.procesar(carritoId); return ResponseEntity.ok().build(); }
}