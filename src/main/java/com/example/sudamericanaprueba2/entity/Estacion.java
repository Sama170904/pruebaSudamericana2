package com.example.sudamericanaprueba2.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.geo.Point;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@AllArgsConstructor // no me corria swagger por eso las agregue
@NoArgsConstructor // no me corria swagger por eso las agregue
@Entity
@SQLDelete(sql = "UPDATE tbl_estacion SET estado = 'INACTIVO' WHERE estacion_id = ?")
@SQLRestriction("estado = 'ACTIVO'")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "tbl_estacion")
public class Estacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long estacionId;

    @Column
    private String nombre;

    @Column
    private Point ubicacion;

    @Column
    @Enumerated(EnumType.STRING)
    @Builder.Default // hace que el builder guarde los valores dafault
    private Estado estado = Estado.ACTIVO;

    public enum Estado {
        ACTIVO, INACTIVO
    }

    @ToString.Exclude // Bloquea el bucle del toString()
    @EqualsAndHashCode.Exclude // Bloquea problemas de memoria al comparar
    @OneToMany(mappedBy = "estacion")
    @JsonIgnore
    private List<Sesion> sesiones = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "estacion_conector", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "estacion_id"), // FK hacia esta entidad
            inverseJoinColumns = @JoinColumn(name = "conector_id") // FK hacia la otra entidad
    )
    private List<Conector> conectores = new ArrayList<>();

}
