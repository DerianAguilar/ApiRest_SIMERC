package com.fesc.SIMERC.Services.Impl;

import com.fesc.SIMERC.Entities.AuditoriaGestion;
import com.fesc.SIMERC.Entities.Rol;
import com.fesc.SIMERC.Entities.Usuario;
import com.fesc.SIMERC.Repositories.AuditoriaGestionRepository;
import com.fesc.SIMERC.Repositories.RolRepository;
import com.fesc.SIMERC.Repositories.UsuarioRepository;
import com.fesc.SIMERC.Security.Exceptions.MyException;
import com.fesc.SIMERC.Services.UsuarioService;
import com.fesc.SIMERC.Shared.RegistroAlumDTO;
import com.fesc.SIMERC.Shared.RegistroAsesorDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
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
            if (Objects.equals(user.getEmail(), registroAsesorDTO.getEmail())
                && Objects.equals(registroAsesorDTO.getRols(), user.getRol().getRolNombre())){
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
            if (Objects.equals(user.getEmail(), registroAlumDTO.getEmail())
                    && Objects.equals("ALUMNO", user.getRol().getRolNombre())){
                usuario = user;
            }
        }

        if (usuario != null){
            throw new MyException("EL usuario ya existe con este rol. ");
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
