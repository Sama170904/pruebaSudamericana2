package com.example.sudamericanaprueba2.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
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


    @Column(name = "fecha_creacion", updatable = false)
    @CreationTimestamp // Magia de Hibernate: llena la fecha automáticamente al crearla
    private LocalDateTime fechaCreacion;



    // mappedBy indica que Student es el dueño de la relación (el que crea la tabla intermedia)
// Bloquea problemas de memoria al compara
    @ManyToMany
    @JoinTable(
        name = "tbl_UsuarioVotosTarea", // Nombre de la tabla intermedia que Spring creará
        joinColumns = @JoinColumn(name = "user_id"), // Columna que apunta a usuario
        inverseJoinColumns = @JoinColumn(name = "tarea_id") // Columna que apunta a tarea
    )
    @ToString.Exclude // <--- Bloquea el bucle del toString()
    @EqualsAndHashCode.Exclude // <--- Bloquea el bucle del EqualsandHashcode()
    private Set<Usuario> usuariosVotantes = new HashSet<>();

    //comentarios
    @ToString.Exclude // Bloquea el bucle del toString()
    @EqualsAndHashCode.Exclude // Bloquea problemas de memoria al comparar
    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios = new ArrayList<>();
}
