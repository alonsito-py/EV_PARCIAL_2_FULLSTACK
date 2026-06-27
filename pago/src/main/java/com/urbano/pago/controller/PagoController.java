package com.urbano.pago.controller;
import com.urbano.pago.dto.PagoDTO;
import com.urbano.pago.model.TransaccionPago;
import com.urbano.pago.service.PagoService;
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
@Tag(name = "Pagos", description = "Procesamiento de transacciones de pago")
@RestController @RequestMapping("/api/v1/pagos")
public class PagoController {
    @Autowired private PagoService service;
    @GetMapping("/pedido/{pedidoId}")
    @Operation(summary = "Operación GET")
    public ResponseEntity<List<TransaccionPago>> getByPedido(@PathVariable Long pedidoId) { return ResponseEntity.ok(service.getByPedido(pedidoId)); }
    @PostMapping
    @Operation(summary = "Operación POST")
    public ResponseEntity<TransaccionPago> procesar(@Valid @RequestBody PagoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.procesar(dto)); }
}