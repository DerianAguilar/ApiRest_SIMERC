package com.fesc.SIMERC.Controllers;


import com.fesc.SIMERC.Models.Peticiones.RegistroAlumRequest;
import com.fesc.SIMERC.Models.Peticiones.RegistroAsesorRequest;
import com.fesc.SIMERC.Security.Exceptions.Mensaje;
import com.fesc.SIMERC.Services.UsuarioService;
import com.fesc.SIMERC.Shared.RegistroAlumDTO;
import com.fesc.SIMERC.Shared.RegistroAsesorDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registro")
@CrossOrigin("*")
public class UsuarioController {


    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/asesor")
    public ResponseEntity<?> registrarAsesor (@RequestBody RegistroAsesorRequest asesorRequest){

        try{

            RegistroAsesorDTO asesorDTO = modelMapper.map(asesorRequest, RegistroAsesorDTO.class);

            usuarioService.registrarAsesor(asesorDTO);

            return new ResponseEntity<>(new Mensaje(asesorRequest.getRols()+" registrado con exito"), HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(new Mensaje("Error al registrar"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/alumno")
    public ResponseEntity<?> registrarAlumno (@RequestBody RegistroAlumRequest alumRequest){

        try{

            RegistroAlumDTO alumDTO = modelMapper.map(alumRequest, RegistroAlumDTO.class);

            usuarioService.registrarAlumn(alumDTO);

            return new ResponseEntity<>(new Mensaje("Alumno registrado"), HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(new Mensaje("Error al registrar el alumno "), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
