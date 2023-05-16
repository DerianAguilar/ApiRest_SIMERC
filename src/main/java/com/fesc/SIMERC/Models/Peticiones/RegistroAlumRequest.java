package com.fesc.SIMERC.Models.Peticiones;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class RegistroAlumRequest {

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
    private String correo; //Correo de la persona que registra
}
