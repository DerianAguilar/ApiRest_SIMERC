package com.fesc.SIMERC.Services;

import com.fesc.SIMERC.Entities.Recordatorio;
import com.fesc.SIMERC.Entities.Rol;
import com.fesc.SIMERC.Entities.Tarea;
import com.fesc.SIMERC.Entities.Usuario;
import com.fesc.SIMERC.Models.Respuestas.BuscarAlumnoResponse;
import com.fesc.SIMERC.Models.Respuestas.DetalleAlumnResponse;
import com.fesc.SIMERC.Shared.RecordatorioDTO;
import com.fesc.SIMERC.Shared.RegistroAlumDTO;
import com.fesc.SIMERC.Shared.RegistroAsesorDTO;
import com.fesc.SIMERC.Shared.TareaSaveDTO;

import java.text.ParseException;
import java.util.List;

public interface UsuarioService {

    public List<Usuario> listarUsuario();
    public Usuario guardarAdmin (Usuario usuario, Rol rol);
    public void registrarAsesor (RegistroAsesorDTO registroAsesorDTO);
    public void registrarAlumn (RegistroAlumDTO registroAlumDTO);
    public List<BuscarAlumnoResponse> buscarAlumno (String txtBusc);
    public DetalleAlumnResponse detalleAlumno (String txtBusc);
    public void guardarRecordatorio (RecordatorioDTO recordatorioDTO);
    public List<Recordatorio> listarRecordatorio (String email) throws ParseException;
    public void asignarTarea (TareaSaveDTO saveDTO);
    public List<Tarea> misTareas (String email);


}
