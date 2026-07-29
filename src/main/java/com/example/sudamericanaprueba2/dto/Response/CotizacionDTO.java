package com.example.sudamericanaprueba2.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CotizacionDTO {

    private Long conectorId;
    private String tipoConector;
    private Double potenciaKw;
    private Double tarifaKwh;
    private Double energiaEstimadaKwh;
    private Double costoEstimadoUSD;
}
