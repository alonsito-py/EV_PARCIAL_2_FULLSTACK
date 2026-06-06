package com.urbano.despacho.service;
import com.urbano.despacho.dto.GuiaDespachoDTO;
import com.urbano.despacho.exception.DespachoNotFoundException;
import com.urbano.despacho.model.GuiaDespacho;
import com.urbano.despacho.repository.DespachoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.UUID;
@Service @Transactional @Slf4j
public class DespachoService {
    @Autowired private DespachoRepository repo;
    @Autowired private WebClient webClientPedido;
    public List<GuiaDespacho> getAll() { return repo.findAll(); }
    public GuiaDespacho getById(Long id) { return repo.findById(id).orElseThrow(()->new DespachoNotFoundException("Despacho no encontrado id: "+id)); }
    public GuiaDespacho crear(GuiaDespachoDTO dto) {
        log.info("Creando guía despacho pedido: {}", dto.getPedidoId());
        GuiaDespacho g=new GuiaDespacho(); g.setPedidoId(dto.getPedidoId()); g.setDireccionDestino(dto.getDireccionDestino());
        g.setEstado("PREPARANDO"); g.setFechaEstimada(dto.getFechaEstimada()); g.setCodigoSeguimiento(UUID.randomUUID().toString().substring(0,8).toUpperCase());
        GuiaDespacho saved=repo.save(g);
        try { webClientPedido.patch().uri("/api/v1/pedidos/"+dto.getPedidoId()+"/estado?estado=DESPACHADO").retrieve().bodyToMono(Object.class).block(); } catch(Exception e) { log.error("Error actualizando pedido: {}",e.getMessage()); }
        return saved;
    }
    public GuiaDespacho cambiarEstado(Long id, String estado) { GuiaDespacho g=getById(id); g.setEstado(estado); return repo.save(g); }
}
