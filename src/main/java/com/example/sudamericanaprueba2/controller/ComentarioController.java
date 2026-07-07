package com.example.sudamericanaprueba2.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sudamericanaprueba2.dto.ComentarioDTO;
import com.example.sudamericanaprueba2.entity.Comentario;
import com.example.sudamericanaprueba2.repository.ComentarioRepository;
import com.example.sudamericanaprueba2.repository.TareaRepository;
import com.example.sudamericanaprueba2.repository.UsuarioRepository;
import com.example.sudamericanaprueba2.service.ComentarioService;

//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/comentario")
public class ComentarioController {
    
    private final ComentarioService comentarioService; 

    @PostMapping
    public Comentario create(@RequestBody ComentarioDTO comentario) {
        return comentarioService.create(comentario);
    }


    @GetMapping("/{tareaId}")
    public List<Comentario> comentarioPorTarea(@PathVariable("tareaId") Long id) {
        return comentarioService.getComentariosPorTarea(id); 
    }

}
