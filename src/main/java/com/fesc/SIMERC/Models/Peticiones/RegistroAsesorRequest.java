package com.fesc.SIMERC.Models.Peticiones;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RegistroAsesorRequest {

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
    private String correo; //correo del que registra ej: adminFesc@fesc.edu.co

}
