package com.example.sudamericanaprueba2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.sudamericanaprueba2.filter.JwtAuthenticationFilter;


@EnableMethodSecurity 
@Configuration
@EnableWebSecurity //le dice spring que aqui estaran instanciadas las reglas de seguridad de mi web
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter; 
    private final AuthenticationProvider authenticationProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;


    //este metodo basicamente le pasamos el "formulario" httpSecurity vacio y la funcion del metodo es llenrlo
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/monitor/**", "/ws-notificaciones/**").permitAll()
                .anyRequest().authenticated()
            ).exceptionHandling(exception -> exception
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider) //le pasamos nuestro detective, el metodo en app config
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);//Spring tiene un "guardia" por defecto
            //que funciona como portero a traves de usuairo y cotnrasena, como usamos jwt no lo necesitamos aqui, entonces ponemos
            //por defecto el filtro lector de jwt como portero

        return http.build();
        /*
        cunado se hace el build, se construye estebojeto http y crea mas filtros automaticamente y
        los inyecta en esta clase secuirty filter chain, donde funcionan en conjunto
        */
    }
}
//este el el contenedor de todos los filtros, es como el filtro empaquetado con todos los filtros
//es una lista