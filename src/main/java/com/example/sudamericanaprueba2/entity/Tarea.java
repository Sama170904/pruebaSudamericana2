package com.example.sudamericanaprueba2.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.sudamericanaprueba2.entity.Tarea.Estado;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@AllArgsConstructor //no me corria swagger por eso las agregue
@NoArgsConstructor  //no me corria swagger por eso las agregue
@Entity
@Table(name = "tbl_tarea")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tareaId;

    @Column
    private String titulo;

    @Column
    private String descripcion;

    @Column
    @Enumerated(EnumType.STRING) 
    private Estado estado;

    public enum Estado {
        OPEN, INPROGRESS, COMPLETED
    }

    @Column
    @Enumerated(EnumType.STRING) 
    private Categoria categoria;

    public enum Categoria {
        UI, UX, FEATURE, BUG, PERFORMANCE
    }

    // mappedBy indica que Student es el dueño de la relación (el que crea la tabla intermedia)
    @ManyToMany(mappedBy = "tareas")
    @JsonIgnore // Evita el bucle infinito al convertir la respuesta a JSON
    @ToString.Exclude // Bloquea el bucle del toString()
    @EqualsAndHashCode.Exclude // Bloquea problemas de memoria al comparar
    private Set<Usuario> usuarios = new HashSet<>();

    //comentarios
    @ToString.Exclude // Bloquea el bucle del toString()
    @EqualsAndHashCode.Exclude // Bloquea problemas de memoria al comparar
    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios = new ArrayList<>();
}
