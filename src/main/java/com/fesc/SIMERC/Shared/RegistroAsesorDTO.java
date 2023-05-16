package com.fesc.SIMERC.Shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RegistroAsesorDTO implements Serializable {

    private static final long SerialVersionUID=1L;

    private String tipoDocumento;
    private String documento;
    private String nombre;
    private String apellido;
    private String email;
    private String direccion;
    private String telefono;
    private String pass;
    private String passwordEmail;
    private String rols;
    private String correo;

}
