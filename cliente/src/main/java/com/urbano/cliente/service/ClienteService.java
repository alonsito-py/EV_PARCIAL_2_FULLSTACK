package com.urbano.cliente.service;
import com.urbano.cliente.dto.ClienteDTO;
import com.urbano.cliente.exception.ClienteNotFoundException;
import com.urbano.cliente.model.Cliente;
import com.urbano.cliente.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @Transactional @Slf4j
public class ClienteService {
    @Autowired private ClienteRepository repo;
    public List<Cliente> getAll() { log.info("Listando clientes"); return repo.findAll(); }
    public Cliente getById(Long id) { return repo.findById(id).orElseThrow(()->new ClienteNotFoundException("Cliente no encontrado id: "+id)); }
    public Cliente create(ClienteDTO dto) {
        log.info("Creando cliente correo: {}", dto.getCorreo());
        if (repo.existsByCorreo(dto.getCorreo())) throw new RuntimeException("Correo ya registrado: "+dto.getCorreo());
        Cliente c=new Cliente(); c.setNombre(dto.getNombre()); c.setCorreo(dto.getCorreo()); c.setTelefono(dto.getTelefono());
        Cliente saved=repo.save(c); log.info("Cliente creado id: {}", saved.getId()); return saved;
    }
    public Cliente update(Long id, ClienteDTO dto) { Cliente c=getById(id); c.setNombre(dto.getNombre()); c.setTelefono(dto.getTelefono()); return repo.save(c); }
    public void verificar(Long id) { log.info("Verificando cliente id: {}",id); Cliente c=getById(id); c.setVerificado(true); repo.save(c); }
    public void delete(Long id) { log.warn("Desactivando cliente id: {}",id); Cliente c=getById(id); c.setActivo(false); repo.save(c); }
}