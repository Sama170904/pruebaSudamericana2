package com.example.sudamericanaprueba2.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// ¡IMPORTACIÓN CORREGIDA! 
import org.springframework.web.bind.annotation.RequestBody;

import com.example.sudamericanaprueba2.dto.Create.ActualizarEstadoTareaDTO;
import com.example.sudamericanaprueba2.dto.Create.TareaCreateDTO;
import com.example.sudamericanaprueba2.entity.Tarea;
import com.example.sudamericanaprueba2.entity.Tarea.Categoria;
import com.example.sudamericanaprueba2.entity.Tarea.Estado;
import com.example.sudamericanaprueba2.service.AuthService;
import com.example.sudamericanaprueba2.service.TareaService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/tarea")
public class TareaController {

    private final TareaService tareaService;
    
    
    @GetMapping("/{tareaId}")
    public Tarea getTareaId(@PathVariable("tareaId") @Min(value = 1, message = "El ID debe ser mayor a 0") Long id) {
        return tareaService.getTareaId(id); 
    }

    @GetMapping("/categoria/{categoria}")
    public List<Tarea> getTareaPorCategoria(@PathVariable("categoria") Categoria categoria) {
        return tareaService.getTareaPorCategoria(categoria); 
    }

    @GetMapping("/estado/{estado}")
    public List<Tarea> getTareaPorEstado(@PathVariable("estado") Estado estado) {
        return tareaService.getTareaPorEstado(estado); 
    }



    @GetMapping("/categoria/{estado}/count")
    public int contarPorEstado(@PathVariable("estado") Estado estado) {
        return tareaService.contarPorEstado(estado); 
    }

    @PutMapping
    public Tarea actualizarEstado(@RequestBody ActualizarEstadoTareaDTO tarea) {
        return tareaService.cambiarEstadoTarea(tarea);
    }



    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public Tarea create(@RequestBody @Valid TareaCreateDTO tarea) {
        return tareaService.create(tarea);
    }
}