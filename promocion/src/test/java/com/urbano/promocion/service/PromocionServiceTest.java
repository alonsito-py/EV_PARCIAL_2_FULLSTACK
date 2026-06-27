package com.urbano.promocion.service;

import com.urbano.promocion.dto.CuponDTO;
import com.urbano.promocion.exception.PromocionNotFoundException;
import com.urbano.promocion.model.Cupon;
import com.urbano.promocion.model.Promocion;
import com.urbano.promocion.repository.CuponRepository;
import com.urbano.promocion.repository.PromocionRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - PromocionService")
class PromocionServiceTest {

    @Mock private PromocionRepository promoRepo;
    @Mock private CuponRepository cuponRepo;
    @InjectMocks private PromocionService promocionService;

    private Cupon cupon;

    @BeforeEach
    void setUp() {
        cupon = new Cupon(1L, "URBANO20", new BigDecimal("20"), 100, 5, true);
    }

    @Test
    @DisplayName("getAll - debería retornar promociones activas")
    void testGetAll() {
        when(promoRepo.findByActivoTrue()).thenReturn(List.of());
        List<Promocion> result = promocionService.getAll();
        assertNotNull(result);
        verify(promoRepo).findByActivoTrue();
    }

    @Test
    @DisplayName("createCupon - debería crear cupón con código único")
    void testCreateCupon_exitoso() {
        CuponDTO dto = new CuponDTO("NUEVO10", new BigDecimal("10"), 50);
        when(cuponRepo.findByCodigo("NUEVO10")).thenReturn(Optional.empty());
        when(cuponRepo.save(any())).thenReturn(cupon);
        Cupon result = promocionService.createCupon(dto);
        assertNotNull(result);
        verify(cuponRepo).save(any(Cupon.class));
    }

    @Test
    @DisplayName("createCupon - debería lanzar excepción con código duplicado")
    void testCreateCupon_duplicado() {
        CuponDTO dto = new CuponDTO("URBANO20", new BigDecimal("20"), 100);
        when(cuponRepo.findByCodigo("URBANO20")).thenReturn(Optional.of(cupon));
        assertThrows(RuntimeException.class, () -> promocionService.createCupon(dto));
        verify(cuponRepo, never()).save(any());
    }

    @Test
    @DisplayName("validarCupon - debería retornar cupón válido y aumentar uso")
    void testValidarCupon_valido() {
        when(cuponRepo.findByCodigo("URBANO20")).thenReturn(Optional.of(cupon));
        when(cuponRepo.save(any())).thenReturn(cupon);
        Cupon result = promocionService.validarCupon("URBANO20");
        assertEquals(6, cupon.getUsosActuales());
        verify(cuponRepo).save(cupon);
    }

    @Test
    @DisplayName("validarCupon - debería lanzar excepción cuando cupón no existe")
    void testValidarCupon_noExiste() {
        when(cuponRepo.findByCodigo("FAKE")).thenReturn(Optional.empty());
        assertThrows(PromocionNotFoundException.class, () -> promocionService.validarCupon("FAKE"));
    }

    @Test
    @DisplayName("validarCupon - debería lanzar excepción cuando cupón está agotado")
    void testValidarCupon_agotado() {
        cupon.setUsosActuales(100);
        when(cuponRepo.findByCodigo("URBANO20")).thenReturn(Optional.of(cupon));
        assertThrows(RuntimeException.class, () -> promocionService.validarCupon("URBANO20"));
    }
}
