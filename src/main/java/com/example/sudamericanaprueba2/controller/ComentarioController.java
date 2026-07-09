package com.example.sudamericanaprueba2.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sudamericanaprueba2.dto.Create.ComentarioCreateDTO;
import com.example.sudamericanaprueba2.dto.Response.ComentarioResponseDTO;
import com.example.sudamericanaprueba2.entity.Comentario;
import com.example.sudamericanaprueba2.repository.ComentarioRepository;
import com.example.sudamericanaprueba2.repository.TareaRepository;
import com.example.sudamericanaprueba2.repository.UsuarioRepository;
import com.example.sudamericanaprueba2.service.ComentarioService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/comentario")
public class ComentarioController {
    
    private final ComentarioService comentarioService; 

    @PostMapping
    public ComentarioResponseDTO create(@RequestBody @Valid ComentarioCreateDTO comentario) {
        return comentarioService.create(comentario);
    }


    @GetMapping("/{tareaId}")
    public List<ComentarioResponseDTO> comentarioPorTarea(@PathVariable("tareaId") @Min(value = 1, message = "El ID debe ser mayor a 0") Long id) {
        return comentarioService.getComentariosPorTarea(id); 
    }

}
