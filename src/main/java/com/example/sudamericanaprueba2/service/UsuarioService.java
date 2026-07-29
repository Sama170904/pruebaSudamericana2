package com.example.sudamericanaprueba2.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.sudamericanaprueba2.dto.Response.UsuarioResponseDTO;
import com.example.sudamericanaprueba2.entity.Usuario;
import com.example.sudamericanaprueba2.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public Page<UsuarioResponseDTO> getUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(this::mapToDTO);
    }

    public UsuarioResponseDTO getUsuarioById(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el ID: " + id));
        return mapToDTO(u);
    }

    private UsuarioResponseDTO mapToDTO(Usuario u) {
        return UsuarioResponseDTO.builder()
                .userId(u.getUserId())
                .nombre(u.getNombre())
                .apellido(u.getApellido())
                .email(u.getEmail())
                .rol(u.getRol() != null ? u.getRol().name() : null)
                .build();
    }

}
