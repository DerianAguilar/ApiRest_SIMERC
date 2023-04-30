package com.fesc.SIMERC.Services.Impl;

import com.fesc.SIMERC.Entities.Usuario;
import com.fesc.SIMERC.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDatailsImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUser(String email, String rol){
        List<Usuario> usuario = usuarioRepository.findAll();
        Usuario user = null;
        for (Usuario users:usuario){
            if (users.getRol().getRolNombre().equals(rol)&&
                    Objects.equals(users.getEmail(), email)){
                user = users;
            }
        }

        if (user == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return user;
    }


}
