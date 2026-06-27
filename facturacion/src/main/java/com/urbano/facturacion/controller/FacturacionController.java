package com.urbano.facturacion.controller;
import com.urbano.facturacion.dto.BoletaDTO;
import com.urbano.facturacion.model.BoletaElectronica;
import com.urbano.facturacion.service.FacturacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "Facturación", description = "Emisión de boletas electrónicas")
@RestController @RequestMapping("/api/v1/facturacion")
public class FacturacionController {
    @Autowired private FacturacionService service;
    @GetMapping
    @Operation(summary = "Operación GET")
    public ResponseEntity<List<BoletaElectronica>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/pedido/{pedidoId}")
    @Operation(summary = "Operación GET")
    public ResponseEntity<List<BoletaElectronica>> getByPedido(@PathVariable Long pedidoId) { return ResponseEntity.ok(service.getByPedido(pedidoId)); }
    @PostMapping
    @Operation(summary = "Operación POST")
    public ResponseEntity<BoletaElectronica> emitir(@Valid @RequestBody BoletaDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.emitir(dto)); }
}