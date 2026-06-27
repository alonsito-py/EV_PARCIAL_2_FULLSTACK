package com.urbano.despacho.service;

import com.urbano.despacho.dto.GuiaDespachoDTO;
import com.urbano.despacho.exception.DespachoNotFoundException;
import com.urbano.despacho.model.GuiaDespacho;
import com.urbano.despacho.repository.DespachoRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - DespachoService")
class DespachoServiceTest {

    @Mock private DespachoRepository despachoRepository;
    @InjectMocks private DespachoService despachoService;

    private GuiaDespacho guia;
    private GuiaDespachoDTO guiaDTO;

    @BeforeEach
    void setUp() {
        guia = new GuiaDespacho(1L, 10L, "PENDIENTE", "Av. Principal 123, Santiago", LocalDate.now().plusDays(2), "TRK-001");
        guiaDTO = new GuiaDespachoDTO(10L, "Av. Principal 123, Santiago", LocalDate.now().plusDays(2));
    }

    @Test
    @DisplayName("getAll - debería retornar todas las guías de despacho")
    void testGetAll() {
        when(despachoRepository.findAll()).thenReturn(List.of(guia));
        List<GuiaDespacho> result = despachoService.getAll();
        assertEquals(1, result.size());
        verify(despachoRepository).findAll();
    }

    @Test
    @DisplayName("getById - debería retornar guía existente")
    void testGetById_existe() {
        when(despachoRepository.findById(1L)).thenReturn(Optional.of(guia));
        GuiaDespacho result = despachoService.getById(1L);
        assertEquals("PENDIENTE", result.getEstado());
    }

    @Test
    @DisplayName("getById - debería lanzar excepción cuando no existe")
    void testGetById_noExiste() {
        when(despachoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(DespachoNotFoundException.class, () -> despachoService.getById(99L));
    }

    @Test
    @DisplayName("cambiarEstado - debería actualizar el estado de la guía")
    void testCambiarEstado() {
        when(despachoRepository.findById(1L)).thenReturn(Optional.of(guia));
        when(despachoRepository.save(any())).thenReturn(guia);
        GuiaDespacho result = despachoService.cambiarEstado(1L, "EN_CAMINO");
        assertEquals("EN_CAMINO", guia.getEstado());
        verify(despachoRepository).save(guia);
    }
}
