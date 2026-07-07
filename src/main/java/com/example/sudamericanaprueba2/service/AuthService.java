package com.example.sudamericanaprueba2.service;



import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.sudamericanaprueba2.dto.LoginRequestDTO;
import com.example.sudamericanaprueba2.dto.TokenResponseDTO;
import com.example.sudamericanaprueba2.entity.Usuario;
import com.example.sudamericanaprueba2.repository.TokenRepository;
import com.example.sudamericanaprueba2.repository.UsuarioRepository;
import com.example.sudamericanaprueba2.entity.Token;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponseDTO login(LoginRequestDTO request) {
        //Se verifica si la contraseña coincide con la base de datos
        // Si la contraseña está mal, esto lanza una excepción automáticamente y corta el proceso.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        //Si pasamos la línea anterior, las credenciales son correctas. Buscamos al usuario.
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));

        // Fabricamos los tokens 
        String jwtToken = jwtService.generateToken(usuario);
        String refreshToken = jwtService.generateRefreshToken(usuario);

        //Inactivamos tokens viejos en la BD y guardamos el nuevo
        revokeAllUserTokens(usuario);
        saveUserToken(usuario, jwtToken);

        // Devolvemos el sobre DTO listo para ir al controlador
        return new TokenResponseDTO(jwtToken, refreshToken);
    }

    //se inserta en un objeto token el la informacion del token y se la guarda en la db
    private void saveUserToken(Usuario usuario, String jwtToken) {
        Token token = new Token();
        token.setUsuario(usuario);
        token.setToken(jwtToken);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepository.save(token);
    }


    private void revokeAllUserTokens(Usuario usuario) {
        //busca el admin
        var validUserTokens = tokenRepository.findAllValidTokensByUser(usuario.getUserId());
        //si es su primera vez iniciando sesion solo retorna
        if (validUserTokens.isEmpty()) {
            return;
        }
        //si ha iniciado sesion antes y tiene tokens previos, todos los revoca con un bucle for each
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        //se gurdan los cambios en la db
        tokenRepository.saveAll(validUserTokens);
    }
}