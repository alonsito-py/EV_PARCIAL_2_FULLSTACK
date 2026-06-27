package com.urbano.producto.service;

import com.urbano.producto.dto.ProductoDTO;
import com.urbano.producto.exception.ProductoNotFoundException;
import com.urbano.producto.model.Categoria;
import com.urbano.producto.model.Producto;
import com.urbano.producto.repository.CategoriaRepository;
import com.urbano.producto.repository.ProductoRepository;
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
@DisplayName("Tests unitarios - ProductoService")
class ProductoServiceTest {

    @Mock private ProductoRepository productoRepository;
    @Mock private CategoriaRepository categoriaRepository;
    @InjectMocks private ProductoService productoService;

    private Producto producto;
    private Categoria categoria;
    private ProductoDTO productoDTO;

    @BeforeEach
    void setUp() {
        categoria = new Categoria(1L, "Ropa", "Ropa urbana");
        producto = new Producto();
        producto.setId(1L);
        producto.setSku("URB-001");
        producto.setNombre("Polera Urbana");
        producto.setPrecio(new BigDecimal("19990"));
        producto.setActivo(true);
        producto.setCategoria(categoria);
        productoDTO = new ProductoDTO();
        productoDTO.setSku("URB-001");
        productoDTO.setNombre("Polera Urbana");
        productoDTO.setPrecio(new BigDecimal("19990"));
        productoDTO.setCategoriaId(1L);
    }

    @Test
    @DisplayName("getAll - debería retornar lista de productos activos")
    void testGetAll() {
        when(productoRepository.findByActivoTrue()).thenReturn(List.of(producto));
        List<Producto> result = productoService.getAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("URB-001", result.get(0).getSku());
        verify(productoRepository).findByActivoTrue();
    }

    @Test
    @DisplayName("getById - debería retornar producto cuando existe")
    void testGetById_existe() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        Producto result = productoService.getById(1L);
        assertNotNull(result);
        assertEquals("Polera Urbana", result.getNombre());
    }

    @Test
    @DisplayName("getById - debería lanzar excepción cuando no existe")
    void testGetById_noExiste() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ProductoNotFoundException.class, () -> productoService.getById(99L));
    }

    @Test
    @DisplayName("getBySku - debería retornar producto por SKU válido")
    void testGetBySku_existe() {
        when(productoRepository.findBySku("URB-001")).thenReturn(Optional.of(producto));
        Producto result = productoService.getBySku("URB-001");
        assertEquals("URB-001", result.getSku());
    }

    @Test
    @DisplayName("getBySku - debería lanzar excepción cuando SKU no existe")
    void testGetBySku_noExiste() {
        when(productoRepository.findBySku("NOPE")).thenReturn(Optional.empty());
        assertThrows(ProductoNotFoundException.class, () -> productoService.getBySku("NOPE"));
    }

    @Test
    @DisplayName("create - debería crear producto con datos válidos")
    void testCreate_exitoso() {
        when(productoRepository.findBySku("URB-001")).thenReturn(Optional.empty());
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        Producto result = productoService.create(productoDTO);
        assertNotNull(result);
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    @DisplayName("create - debería lanzar excepción cuando SKU ya existe")
    void testCreate_skuDuplicado() {
        when(productoRepository.findBySku("URB-001")).thenReturn(Optional.of(producto));
        assertThrows(RuntimeException.class, () -> productoService.create(productoDTO));
        verify(productoRepository, never()).save(any());
    }

    @Test
    @DisplayName("delete - debería desactivar producto (soft delete)")
    void testDelete_desactiva() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        productoService.delete(1L);
        assertFalse(producto.isActivo());
        verify(productoRepository).save(producto);
    }
}
