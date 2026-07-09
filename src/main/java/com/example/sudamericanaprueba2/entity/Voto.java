package com.example.sudamericanaprueba2.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor //no me corria swagger por eso las agregue
@NoArgsConstructor  //no me corria swagger por eso las agregue
@Entity
@Table(name = "tbl_voto")
@SQLDelete(sql = "UPDATE tbl_voto SET estado = 'INACTIVO' WHERE voto_id = ?")
@SQLRestriction("estado = 'ACTIVO'") 
public class Voto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long votoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tarea_id")
    private Tarea tarea;

    @Enumerated(EnumType.STRING) 
    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'ACTIVO'")
    @Builder.Default//hace que el builder guarde los valores dafault
    private Estado estado = Estado.ACTIVO;

    public enum Estado {
        ACTIVO, INACTIVO, N
    }

    @Column(name = "fecha_creacion", updatable = false)
    @CreationTimestamp // Magia de Hibernate: llena la fecha automáticamente al crearla
    private LocalDateTime fechaCreacion;

}
