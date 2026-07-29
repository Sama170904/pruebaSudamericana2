package com.example.sudamericanaprueba2.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sudamericanaprueba2.dto.Response.UsuarioResponseDTO;
import com.example.sudamericanaprueba2.service.UsuarioService;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@CrossOrigin(originPatterns = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public Page<UsuarioResponseDTO> getAll(@ParameterObject @PageableDefault(page = 0, size = 10, sort = "userId") Pageable pageable) {
        return usuarioService.getUsuarios(pageable);
    }

    @GetMapping("/{userId}")
    public UsuarioResponseDTO getById(@PathVariable("userId") @Min(value = 1, message = "El ID debe ser mayor a 0") Long userId) {
        return usuarioService.getUsuarioById(userId);
    }
}
