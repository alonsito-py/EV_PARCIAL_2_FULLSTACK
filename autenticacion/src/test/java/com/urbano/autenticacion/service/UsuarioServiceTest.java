package com.urbano.autenticacion.service;

import com.urbano.autenticacion.dto.UsuarioDTO;
import com.urbano.autenticacion.exception.AutenticacionNotFoundException;
import com.urbano.autenticacion.model.Rol;
import com.urbano.autenticacion.model.Usuario;
import com.urbano.autenticacion.repository.RolRepository;
import com.urbano.autenticacion.repository.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - UsuarioService")
class UsuarioServiceTest {

    @Mock private UsuarioRepository usuarioRepo;
    @Mock private RolRepository rolRepo;
    @InjectMocks private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        Rol rol = new Rol(1L, "ADMIN");
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("admin");
        usuario.setActivo(true);
        usuario.setRol(rol);
    }

    @Test
    @DisplayName("getAll - debería retornar todos los usuarios")
    void testGetAll() {
        when(usuarioRepo.findAll()).thenReturn(List.of(usuario));
        List<Usuario> result = usuarioService.getAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(usuarioRepo).findAll();
    }

    @Test
    @DisplayName("getById - debería retornar usuario existente")
    void testGetById_existe() {
        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(usuario));
        Usuario result = usuarioService.getById(1L);
        assertEquals("admin", result.getUsername());
    }

    @Test
    @DisplayName("getById - debería lanzar excepción cuando no existe")
    void testGetById_noExiste() {
        when(usuarioRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(AutenticacionNotFoundException.class, () -> usuarioService.getById(99L));
    }

    @Test
    @DisplayName("delete - debería desactivar usuario")
    void testDelete() {
        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepo.save(any())).thenReturn(usuario);
        usuarioService.delete(1L);
        assertFalse(usuario.isActivo());
        verify(usuarioRepo).save(usuario);
    }
}
