package com.example.sudamericanaprueba2.dto.Update;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SesionFinalizarDTO {

    private Long sesionId;

    private LocalTime horaFin;

}
