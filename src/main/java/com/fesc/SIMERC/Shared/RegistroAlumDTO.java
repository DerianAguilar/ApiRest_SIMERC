package com.fesc.SIMERC.Shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class RegistroAlumDTO implements Serializable {

    private static final long SerialVersionUID=1L;

    private String tipoDocumento;
    private String documento;
    private String nombre;
    private String apellido;
    private String email;
    private String colegio;
    private String direccion;
    private String telefono;
    private String nacionalidad;
    private String modalidad;
    private String correo;

}
