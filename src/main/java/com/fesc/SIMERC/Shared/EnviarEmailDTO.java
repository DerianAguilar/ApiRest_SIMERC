package com.fesc.SIMERC.Shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EnviarEmailDTO implements Serializable {

    private static final long serialVersionUID=1L;

    private String email; //email de la persona a la que se le va a enviar el correo
    private String asunto;
    private String descripcion;
    private String correo; // correo de la persona que esta logeada

}
