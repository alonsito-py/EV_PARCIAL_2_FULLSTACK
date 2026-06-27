package com.urbano.autenticacion.controller;

import com.urbano.autenticacion.model.Usuario;
import com.urbano.autenticacion.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/usuarios")
@Tag(name = "Usuarios V2 (HATEOAS)", description = "Gestión de usuarios con navegación hipermedia")
public class UsuarioControllerV2 {

    @Autowired private UsuarioService service;

    private EntityModel<Usuario> toModel(Usuario u) {
        return EntityModel.of(u,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioControllerV2.class).getById(u.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioControllerV2.class).getAll()).withRel("usuarios"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioControllerV2.class).delete(u.getId())).withRel("eliminar")
        );
    }

    @GetMapping
    @Operation(summary = "Listar usuarios con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> getAll() {
        List<EntityModel<Usuario>> list = service.getAll().stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioControllerV2.class).getAll()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID con HATEOAS")
    public ResponseEntity<EntityModel<Usuario>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toModel(service.getById(id)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
