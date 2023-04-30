package com.fesc.SIMERC.Services;

import com.fesc.SIMERC.Entities.Rol;
import com.fesc.SIMERC.Entities.Usuario;

import java.util.List;

public interface UsuarioService {

    public List<Usuario> listarUsuario();
    public Usuario guardarAdmin (Usuario usuario, Rol rol);

}
