package com.example.sudamericanaprueba2.entity;
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
@Table(name = "tbl_comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comentarioId;

    @Column
    private String comentario;

    @ManyToOne(fetch = FetchType.LAZY) // LAZY es mejor para rendimiento
    @JoinColumn(name = "tarea_id") // Esta es la columna en la BD
    private Tarea tarea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="usuario_id")
    private Usuario usuario;
}
