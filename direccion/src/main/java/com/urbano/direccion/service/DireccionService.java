package com.urbano.direccion.service;
import com.urbano.direccion.dto.DireccionDTO;
import com.urbano.direccion.exception.DireccionNotFoundException;
import com.urbano.direccion.model.Direccion;
import com.urbano.direccion.repository.DireccionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
@Service @Transactional @Slf4j
public class DireccionService {
    @Autowired private DireccionRepository repo;
    @Autowired private WebClient webClientCliente;
    private boolean clienteExiste(Long clienteId) {
        try { webClientCliente.get().uri("/api/v1/clientes/"+clienteId).retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, r->{throw new RuntimeException("No existe");}).bodyToMono(Object.class).block(); return true;
        } catch(Exception e) { return false; }
    }
    public List<Direccion> getByCliente(Long clienteId) { log.info("Direcciones cliente: {}", clienteId); return repo.findByClienteId(clienteId); }
    public Direccion getById(Long id) { return repo.findById(id).orElseThrow(()->new DireccionNotFoundException("Direccion no encontrada id: "+id)); }
    public Direccion create(DireccionDTO dto) {
        log.info("Creando direccion para cliente: {}", dto.getClienteId());
        if (!clienteExiste(dto.getClienteId())) throw new RuntimeException("Cliente no existe id: "+dto.getClienteId());
        Direccion d=new Direccion(); d.setClienteId(dto.getClienteId()); d.setCalle(dto.getCalle());
        d.setComuna(dto.getComuna()); d.setRegion(dto.getRegion()); d.setReferencias(dto.getReferencias()); d.setPrincipal(dto.isPrincipal());
        return repo.save(d);
    }
    public void delete(Long id) { log.warn("Eliminando direccion id: {}",id); repo.deleteById(id); }
}
