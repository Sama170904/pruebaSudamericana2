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

import com.example.sudamericanaprueba2.dto.ActualizarEstadoTareaDTO;
import com.example.sudamericanaprueba2.dto.TareaDTO;
import com.example.sudamericanaprueba2.entity.Tarea;
import com.example.sudamericanaprueba2.entity.Tarea.Categoria;
import com.example.sudamericanaprueba2.entity.Tarea.Estado;
import com.example.sudamericanaprueba2.service.AuthService;
import com.example.sudamericanaprueba2.service.TareaService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/tarea")
public class TareaController {

    private final TareaService tareaService;
    
    
    @GetMapping("/{tareaId}")
    public Tarea getTareaId(@PathVariable("tareaId") Long id) {
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

    @GetMapping("/{tareaId}/votos/count")
    public int contarVotos(@PathVariable("tareaId") Long id) {
        return tareaService.contarVotos(id); 
    }

    @GetMapping("/categoria/{categoria}/count")
    public int contarPorCategoria(@PathVariable("categoria") Categoria categoria) {
        return tareaService.contarPorCategoria(categoria); 
    }

    @PutMapping
    public Tarea actualizarEstado(@RequestBody ActualizarEstadoTareaDTO tarea) {
        return tareaService.cambiarEstadoTarea(tarea);
    }

    @PostMapping("/{tareaId}/usuarios/{usuarioId}/votar")
    public Tarea votar(@PathVariable("tareaId") Long tareaId, 
                                @PathVariable("usuarioId") Long usuarioId) {
        return tareaService.asignarVoto(tareaId, usuarioId);
    }

    @PostMapping("/{tareaId}/usuarios/{usuarioId}/quitarVoto")
    public Tarea quitarVoto(@PathVariable("tareaId") Long tareaId, 
                                @PathVariable("usuarioId") Long usuarioId) {
        return tareaService.quitarVoto(tareaId, usuarioId);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public Tarea create(@RequestBody TareaDTO tarea) {
        return tareaService.create(tarea);
    }
}