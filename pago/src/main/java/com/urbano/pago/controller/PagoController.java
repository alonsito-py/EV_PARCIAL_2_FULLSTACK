package com.urbano.pago.controller;
import com.urbano.pago.dto.PagoDTO;
import com.urbano.pago.model.TransaccionPago;
import com.urbano.pago.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/pagos")
public class PagoController {
    @Autowired private PagoService service;
    @GetMapping("/pedido/{pedidoId}") public ResponseEntity<List<TransaccionPago>> getByPedido(@PathVariable Long pedidoId) { return ResponseEntity.ok(service.getByPedido(pedidoId)); }
    @PostMapping public ResponseEntity<TransaccionPago> procesar(@Valid @RequestBody PagoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.procesarPago(dto)); }
}
