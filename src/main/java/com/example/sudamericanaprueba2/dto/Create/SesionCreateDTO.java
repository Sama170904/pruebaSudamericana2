package com.example.sudamericanaprueba2.dto.Create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SesionCreateDTO {

    private Long estacionId;

    private Long usuarioId;
    
    private Long conectorId;
}
