package com.example.sudamericanaprueba2.entity;

import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor // no me corria swagger por eso las agregue
@NoArgsConstructor // no me corria swagger por eso las agregue
@Entity
@SQLDelete(sql = "UPDATE tbl_sesion SET estado = 'INACTIVO' WHERE sesion_id = ?")
@SQLRestriction("estado = 'ACTIVO'")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "tbl_sesion")
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sesionId;

    @CreationTimestamp
    @Column
    private LocalTime horaInicio;

    @Column
    private LocalTime horaFin;

    @Column
    private Double consumoEnergia;

    @Column
    private Double costo;

    @Column
    @Enumerated(EnumType.STRING)
    @Builder.Default // hace que el builder guarde los valores dafault
    private Estado estado = Estado.ACTIVO;

    public enum Estado {
        ACTIVO, INACTIVO
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estacion_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Estacion estacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conector_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Conector conector;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Usuario usuarioId;

}
