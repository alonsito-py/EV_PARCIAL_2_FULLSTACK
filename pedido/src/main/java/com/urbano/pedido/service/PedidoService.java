package com.urbano.pedido.service;
import com.urbano.pedido.dto.*;
import com.urbano.pedido.exception.PedidoNotFoundException;
import com.urbano.pedido.model.*;
import com.urbano.pedido.repository.PedidoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import java.math.BigDecimal;
import java.util.List;
@Service @Transactional @Slf4j
public class PedidoService {
    @Autowired private PedidoRepository repo;
    @Autowired private WebClient webClientCliente;
    private void verificarCliente(Long id) {
        try { webClientCliente.get().uri("/api/v1/clientes/"+id).retrieve()
            .onStatus(HttpStatusCode::is4xxClientError,r->{throw new RuntimeException("Cliente no existe");}).bodyToMono(Object.class).block();
        } catch(Exception e) { throw new RuntimeException("Cliente no valido id: "+id); }
    }
    public List<Pedido> getAll() { return repo.findAll(); }
    public Pedido getById(Long id) { return repo.findById(id).orElseThrow(()->new PedidoNotFoundException("Pedido no encontrado id: "+id)); }
    public List<Pedido> getByCliente(Long clienteId) { return repo.findByClienteId(clienteId); }
    public Pedido create(PedidoDTO dto) {
        log.info("Creando pedido cliente: {}", dto.getClienteId());
        verificarCliente(dto.getClienteId());
        Pedido p=new Pedido(); p.setClienteId(dto.getClienteId()); p.setDireccionId(dto.getDireccionId()); p.setEstado("PENDIENTE");
        BigDecimal total=BigDecimal.ZERO;
        for (DetallePedidoDTO d:dto.getDetalles()) {
            DetallePedido det=new DetallePedido(); det.setPedido(p); det.setSku(d.getSku());
            det.setCantidad(d.getCantidad()); det.setPrecioUnitario(d.getPrecioUnitario());
            total=total.add(d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad())));
            p.getDetalles().add(det);
        }
        p.setTotal(total); Pedido saved=repo.save(p); log.info("Pedido creado id:{} total:{}", saved.getId(), saved.getTotal()); return saved;
    }
    public Pedido cambiarEstado(Long id, String estado) { log.info("Cambiando estado pedido {} a {}", id, estado); Pedido p=getById(id); p.setEstado(estado); return repo.save(p); }
}