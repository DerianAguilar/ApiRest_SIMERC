package com.fesc.SIMERC.Controllers;

import com.fesc.SIMERC.Models.Peticiones.EnviarUnEmailRequest;
import com.fesc.SIMERC.Security.Exceptions.Mensaje;
import com.fesc.SIMERC.Services.EmailService;
import com.fesc.SIMERC.Shared.EnviarEmailDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/correo")
@CrossOrigin("*")
public class EmailController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/enviar")
    public ResponseEntity<?> enviarCorreo (@RequestBody EnviarUnEmailRequest emailRequest){

        try{
            System.out.println("entre al correo"+emailRequest.getEmail()+" ***********");
            EnviarEmailDTO emailDTO = modelMapper.map(emailRequest, EnviarEmailDTO.class);

            emailService.enviarEmail(emailDTO);

            return new ResponseEntity<>(new Mensaje("Correo enviado"), HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(new Mensaje("Error al enviar el correo"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
