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
@Table(name = "tbl_usuario")
public class Usuario implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    @Enumerated(EnumType.STRING) 
    private Rol rol;

    public enum Rol {
        ACTIVO, INACTIVO, N
    }

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @ManyToMany
    @JoinTable(
        name = "tbl_UsuarioVotosTarea", // Nombre de la tabla intermedia que Spring creará
        joinColumns = @JoinColumn(name = "user_id"), // Columna que apunta a usuario
        inverseJoinColumns = @JoinColumn(name = "tarea_id") // Columna que apunta a tarea
    )
    @ToString.Exclude // <--- Bloquea el bucle del toString()
    @EqualsAndHashCode.Exclude // <--- Bloquea problemas de memoria al comparar
    private Set<Tarea> tareas = new HashSet<>();

    @ToString.Exclude // Bloquea el bucle del toString()
    @EqualsAndHashCode.Exclude // Bloquea problemas de memoria al comparar
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios = new ArrayList<>();

}
