package com.example.sudamericanaprueba2.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Builder
@Data
@AllArgsConstructor // no me corria swagger por eso las agregue
@NoArgsConstructor // no me corria swagger por eso las agregue
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "tbl_conector")
public class Conector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conectorId;

    @Column
    private String tipo;

    @Column
    private Double potencia;

    @Column
    private Double tarifa;

    @Column
    @Builder.Default
    private Boolean disponible = true;

    @JsonIgnore
    @ManyToMany(mappedBy = "conectores")
    private List<Estacion> estaciones = new ArrayList<>();

}
