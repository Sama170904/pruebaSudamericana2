package com.example.sudamericanaprueba2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDTO {

    @JsonProperty("access_token") //asi se ve la clave en el json y no sera con el nombre del atributo
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;
}