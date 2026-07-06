package com.example.sudamericanaprueba2.middlewares;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.example.sudamericanaprueba2.dto.ApiResponseDTO;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    //methodParameter: información sobre el método del controlador que me dio la respuesta
    //converterType: el tipo de convertidor que se usará para convertir el objeto a JSON 

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return !returnType.getParameterType().equals(ApiResponseDTO.class) && 
        !returnType.getParameterType().equals(ResponseEntity.class)&&
            !returnType.getParameterType().equals(String.class) &&
            !returnType.getParameterType().equals(byte[].class);
    }
    //no hay logica de que se tiene que continuar si es que retorna true or false, porque 
    //como es una interfaz la logica ya está implementada en la clase que lo implementa, 
    //entonces el metodo support solo se encarga en decirle a beforebodywrite si continuar o no.



    //object: es el objeto que el controlador devolvió como respuesta a la peticion web
    //MediaType: el tipo de contenido que se va a enviar 
    //class: el tipo de convertidor que se va a usar para convertir el objeto a JSON
    //ServerHttpRequest: información sobre la solicitud HTTP entrante
    //ServerHttpResponse: información sobre la respuesta HTTP que se va a enviar
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, 
                                    MediaType selectedContentType, Class selectedConverterType, 
                                    ServerHttpRequest request, ServerHttpResponse response) {

        return ApiResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Operación exitosa")
                .data(body) 
                .build();
    }
}
