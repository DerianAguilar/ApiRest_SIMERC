package com.fesc.SIMERC.Services.Impl;

import com.fesc.SIMERC.Entities.Rol;
import com.fesc.SIMERC.Entities.Usuario;
import com.fesc.SIMERC.Repositories.RolRepository;
import com.fesc.SIMERC.Repositories.UsuarioRepository;
import com.fesc.SIMERC.Security.Exceptions.MyException;
import com.fesc.SIMERC.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Usuario> listarUsuario() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardarAdmin(Usuario usuario, Rol rol) {

        List<Usuario> usuarioLocal = usuarioRepository.findAll();
        Usuario user = null;
        if (!usuarioLocal.isEmpty()) {
            for (Usuario users : usuarioLocal) {
                if (Objects.equals(users.getRol().getRolNombre(), rol.getRolNombre())&&
                        Objects.equals(users.getEmail(), usuario.getEmail())) {
                    throw new MyException("Ya existe un usuario con este rol");
                }
            }
        } else {

            rolRepository.save(rol);

            user = usuarioRepository.save(usuario);


        }

        return user;

    }
}
