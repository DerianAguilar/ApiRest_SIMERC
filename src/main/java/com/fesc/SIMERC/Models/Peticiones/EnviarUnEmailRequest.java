package com.fesc.SIMERC.Models.Peticiones;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EnviarUnEmailRequest {

    private String email; //email de la persona a la que se le va a enviar el correo
    private String asunto;
    private String descripcion;
    private String correo; // correo de la persona que esta logeada

}
