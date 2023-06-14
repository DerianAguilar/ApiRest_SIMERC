package com.fesc.SIMERC.Services.Impl;

import com.fesc.SIMERC.Entities.*;
import com.fesc.SIMERC.Models.Respuestas.BuscarAlumnoResponse;
import com.fesc.SIMERC.Models.Respuestas.DetalleAlumnResponse;
import com.fesc.SIMERC.Repositories.*;
import com.fesc.SIMERC.Security.Exceptions.MyException;
import com.fesc.SIMERC.Services.UsuarioService;
import com.fesc.SIMERC.Shared.RecordatorioDTO;
import com.fesc.SIMERC.Shared.RegistroAlumDTO;
import com.fesc.SIMERC.Shared.RegistroAsesorDTO;
import com.fesc.SIMERC.Shared.TareaSaveDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private RecordatorioRepository recordatorioRepository;
    @Autowired
    private TareaRepository tareaRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuditoriaGestionRepository auditoriaGest;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Override
    public void registrarAsesor(RegistroAsesorDTO registroAsesorDTO) {

        List<Usuario> usuarioList = usuarioRepository.findAll();

        Usuario usuario = null;

        for (Usuario user : usuarioList){
            if (Objects.equals(user.getEmail(), registroAsesorDTO.getEmail())){
                usuario = user;
            }
        }

        if (usuario != null){
            throw new MyException("EL usuario ya existe. ");
        }

        Rol rol = new Rol();
        rol.setId(2L);
        rol.setRolNombre(registroAsesorDTO.getRols().toUpperCase());

        Rol rols = rolRepository.save(rol);

        usuario = modelMapper.map(registroAsesorDTO, Usuario.class);
        usuario.setRol(rols);
        usuario.setPass(passwordEncoder.encode(registroAsesorDTO.getPass()));

        usuarioRepository.save(usuario);

        auditoria("Registro","Registro al "+usuario.getRol().getRolNombre() +" "
                +usuario.getNombre()+" "+usuario.getApellido(), registroAsesorDTO.getCorreo());


    }

    @Override
    public void registrarAlumn(RegistroAlumDTO registroAlumDTO) {

        List<Usuario> usuarioList = usuarioRepository.findAll();

        Usuario usuario = null;

        for (Usuario user : usuarioList){
            if (Objects.equals(user.getEmail(), registroAlumDTO.getEmail())){
                usuario = user;
            }
        }

        if (usuario != null){
            throw new MyException("EL usuario ya existe.");
        }

        Rol rol = new Rol();
        rol.setId(3L);
        rol.setRolNombre("ALUMNO");

        Rol rols = rolRepository.save(rol);

        usuario = modelMapper.map(registroAlumDTO, Usuario.class);
        usuario.setRol(rols);

        usuarioRepository.save(usuario);

        auditoria("Registro","Registro al "+usuario.getRol().getRolNombre() +" "
                +usuario.getNombre()+" "+usuario.getApellido(), registroAlumDTO.getCorreo());

    }

    @Override
    public List<BuscarAlumnoResponse> buscarAlumno(String txtBusc) {

        List<Usuario> usuarios = usuarioRepository.buscarAumno(txtBusc);

        List<BuscarAlumnoResponse> alumnos = new ArrayList<>();

        for (Usuario usuario : usuarios){
            if (Objects.equals(usuario.getRol().getRolNombre(),"ALUMNO")){

                BuscarAlumnoResponse alumnoResponse = new BuscarAlumnoResponse();
                alumnoResponse.setNombre(usuario.getNombre() + " "+ usuario.getApellido());
                alumnoResponse.setDocumento(usuario.getDocumento());
                alumnoResponse.setCorreo(usuario.getEmail());
                alumnoResponse.setCarrera(usuario.getCarrera());
                alumnos.add(alumnoResponse);

            }
        }



        return alumnos;
    }

    @Override
    public DetalleAlumnResponse detalleAlumno(String txtBusc) {

        Usuario user = usuarioRepository.findByEmail(txtBusc);

        if (user == null){
            throw new MyException("El alumno no existe");
        }
        return modelMapper.map(user, DetalleAlumnResponse.class);
    }

    @Override
    public void guardarRecordatorio(RecordatorioDTO recordatorioDTO) {

        Usuario user = usuarioRepository.findByEmail(recordatorioDTO.getEmail());
        if (user == null){
            throw new MyException("El usuario no existe");
        }
        System.out.println(recordatorioDTO.getFecha()+" *********");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(recordatorioDTO.getFecha());

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date nuevaFecha = calendar.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormat = simpleDateFormat.format(nuevaFecha);

        System.out.println(fechaFormat+" ***********");
        Recordatorio recordatorio = modelMapper.map(recordatorioDTO, Recordatorio.class);
        recordatorio.setUser(user);
        recordatorio.setFecha(fechaFormat);

        recordatorioRepository.save(recordatorio);

    }

    @Override
    public List<Recordatorio> listarRecordatorio(String email) throws ParseException {

        Usuario user = usuarioRepository.findByEmail(email);
        List<Recordatorio> recordatorios = recordatorioRepository.listarRecorUser(user.getId());

        if (recordatorios.isEmpty()){
            throw new MyException("No hay recordatorios");
        }
        List<Recordatorio> recorReturn = new ArrayList<>();
        Date fechaActual = new Date();
        for (Recordatorio recor: recordatorios){
            String fecha = recor.getFecha();

            System.out.println(fecha+" **************");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaAct = simpleDateFormat.format(fechaActual);
            System.out.println(fechaAct + " *************");

            if (Objects.equals(fecha, fechaAct)){
                recorReturn.add(recor);
            }
        }

        if (recorReturn.isEmpty()){
            throw new MyException("No tienes recordatorios para hoy");
        }

        return recorReturn;
    }

    @Override
    public void asignarTarea(TareaSaveDTO saveDTO) {
        Usuario user = usuarioRepository.findByEmail(saveDTO.getEmail());
        if (user == null){
            throw new MyException("El usuario no existe");
        }

        Tarea tarea = modelMapper.map(saveDTO, Tarea.class);
        tarea.setUser(user);

        tareaRepository.save(tarea);
    }

    @Override
    public List<Tarea> misTareas(String email) {

        Usuario user = usuarioRepository.findByEmail(email);
        if (user == null){
            throw new MyException("El usuario no existe");
        }

        List<Tarea> misTareas = tareaRepository.misTareas(user.getId());
        if (misTareas.isEmpty()){
            throw new MyException("No tienes tareas pendientes");
        }

        return misTareas;
    }

    public void auditoria (String titulo, String desc, String correo){

        Usuario user = usuarioRepository.findByEmail(correo);

        AuditoriaGestion auditoriaGestion = new AuditoriaGestion();
        auditoriaGestion.setTitulo(titulo);
        auditoriaGestion.setDescripcion(desc);
        auditoriaGestion.setFecha(String.valueOf(LocalDate.now()));
        auditoriaGestion.setUser(user);

        auditoriaGest.save(auditoriaGestion);

    }
}
