package com.urbano.pago.service;
import com.urbano.pago.dto.PagoDTO;
import com.urbano.pago.model.TransaccionPago;
import com.urbano.pago.repository.PagoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
@Service @Transactional @Slf4j
public class PagoService {
    @Autowired private PagoRepository repo;
    @Autowired private WebClient webClientPedido;
    private void actualizarPedido(Long pedidoId, String estado) {
        try { webClientPedido.patch().uri("/api/v1/pedidos/"+pedidoId+"/estado?estado="+estado)
            .retrieve().onStatus(HttpStatusCode::is4xxClientError,r->{throw new RuntimeException("Pedido no encontrado");})
            .bodyToMono(Object.class).block();
        } catch(Exception e) { log.error("Error actualizando pedido: {}", e.getMessage()); }
    }
    public List<TransaccionPago> getByPedido(Long pedidoId) { return repo.findByPedidoId(pedidoId); }
    public TransaccionPago procesar(PagoDTO dto) {
        log.info("Procesando pago pedido:{} monto:{}", dto.getPedidoId(), dto.getMonto());
        TransaccionPago t=new TransaccionPago(); t.setPedidoId(dto.getPedidoId()); t.setMonto(dto.getMonto()); t.setTokenWebpay(dto.getTokenWebpay());
        boolean aprobado = dto.getTokenWebpay() != null && !dto.getTokenWebpay().isEmpty();
        t.setEstadoPago(aprobado ? "APROBADO" : "RECHAZADO");
        TransaccionPago saved=repo.save(t);
        if (aprobado) { log.info("Pago aprobado pedido:{}", dto.getPedidoId()); actualizarPedido(dto.getPedidoId(),"PAGADO"); }
        else { log.warn("Pago rechazado pedido:{}", dto.getPedidoId()); }
        return saved;
    }
}