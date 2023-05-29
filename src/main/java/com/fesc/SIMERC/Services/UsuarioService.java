package com.fesc.SIMERC.Services;

import com.fesc.SIMERC.Entities.Rol;
import com.fesc.SIMERC.Entities.Usuario;
import com.fesc.SIMERC.Models.Respuestas.BuscarAlumnoResponse;
import com.fesc.SIMERC.Shared.RegistroAlumDTO;
import com.fesc.SIMERC.Shared.RegistroAsesorDTO;

import java.util.List;

public interface UsuarioService {

    public List<Usuario> listarUsuario();
    public Usuario guardarAdmin (Usuario usuario, Rol rol);
    public void registrarAsesor (RegistroAsesorDTO registroAsesorDTO);
    public void registrarAlumn (RegistroAlumDTO registroAlumDTO);
    public BuscarAlumnoResponse buscarAlumno (String txtBusc);

}
