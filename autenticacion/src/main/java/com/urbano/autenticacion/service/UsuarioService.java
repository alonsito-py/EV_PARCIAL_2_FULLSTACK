package com.urbano.autenticacion.service;
import com.urbano.autenticacion.dto.UsuarioDTO;
import com.urbano.autenticacion.exception.AutenticacionNotFoundException;
import com.urbano.autenticacion.model.Rol;
import com.urbano.autenticacion.model.Usuario;
import com.urbano.autenticacion.repository.RolRepository;
import com.urbano.autenticacion.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @Transactional @Slf4j
public class UsuarioService {
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private RolRepository rolRepo;
    public List<Usuario> getAll() { log.info("Listando usuarios"); return usuarioRepo.findAll(); }
    public Usuario getById(Long id) { return usuarioRepo.findById(id).orElseThrow(()->new AutenticacionNotFoundException("Usuario no encontrado id: "+id)); }
    public Usuario create(UsuarioDTO dto) {
        log.info("Creando usuario: {}", dto.getUsername());
        if (usuarioRepo.existsByUsername(dto.getUsername())) throw new RuntimeException("Username ya existe: "+dto.getUsername());
        Rol rol = rolRepo.findById(dto.getRolId()).orElseThrow(()->new RuntimeException("Rol no encontrado id: "+dto.getRolId()));
        Usuario u = new Usuario(); u.setUsername(dto.getUsername()); u.setPassword(dto.getPassword()); u.setRol(rol);
        Usuario saved = usuarioRepo.save(u); log.info("Usuario creado id: {}", saved.getId()); return saved;
    }
    public Usuario login(String username, String password) {
        log.info("Login intento para: {}", username);
        Usuario u = usuarioRepo.findByUsername(username).orElseThrow(()->new AutenticacionNotFoundException("Usuario no encontrado: "+username));
        if (!u.getPassword().equals(password)) { log.warn("Password incorrecto para: {}", username); throw new RuntimeException("Credenciales inválidas"); }
        log.info("Login exitoso para: {}", username); return u;
    }
    public void delete(Long id) { log.warn("Desactivando usuario id: {}",id); Usuario u=getById(id); u.setActivo(false); usuarioRepo.save(u); }
}
