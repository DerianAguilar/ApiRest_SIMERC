package com.fesc.SIMERC.Security;

import com.fesc.SIMERC.Entities.Usuario;
import com.fesc.SIMERC.Models.Peticiones.JwtRequest;
import com.fesc.SIMERC.Repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticateService {

    private final AuthenticationManager authenticationManager;

    private final UsuarioRepository repository;

    private final JwtUtils jwtUtils;

    public String validation(JwtRequest jwtRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        jwtRequest.getEmail(),
                        jwtRequest.getPassword()
                )
        );

        List<Usuario> users = repository.findAll();
        Usuario usuario3 = null;
        for (Usuario user:users){
            if(Objects.equals(user.getRol().getRolNombre(),jwtRequest.getRol())&&
                    Objects.equals(user.getEmail(), jwtRequest.getEmail())){
                usuario3 = user;
            }
        }
        if (usuario3 != null) {
            var usuario = usuario3;

            var token = jwtUtils.generateToken(usuario);

            return token;
        }else {
            return null;
        }

    }

}
