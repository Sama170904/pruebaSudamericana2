package com.example.sudamericanaprueba2.config;

import com.example.sudamericanaprueba2.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
@RequiredArgsConstructor //crea un cosntructor para todas las variables final en esta clase, y remplaza @autowired
public class AppConfig {

    private final UsuarioRepository usuarioRepository;

    /*
    Esto nos retorna el administador, usa la funcion lambda y se esta pasando el usuario para
    que el repositoryo haga findbyemail y nos retorne el objeto admin completo
    */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Administrador no encontrado en la base de datos"));
    }

    /*
    Aqui le pasamos a la clase DaoAuthenticatorProvider nuestro buscador de la base de datos,
    porque no le pasamos directamente el repository? porque DaoAtuhtneticatorProvider solo
    sabe hablar con userDetailService entonces hay que hacerlo asi, y tambien le pasamos el
    password encoder para que sepa decodificar la contrasena
    */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }// este AuthenticationProvider se usa en SecurityConfig, este es nuestro DETECTIVE

    //Es el que recibe la informacion del service que es el acceso derivado a los endpoints
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    //Esto es lo que encripta las contrasenas, es el motor de encriptacion
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}