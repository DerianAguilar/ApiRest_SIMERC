package com.fesc.SIMERC.Models.Respuestas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DetalleAlumnResponse {

    private String tipoDocumento;
    private String documento;
    private String nombre;
    private String apellido;
    private String email;
    private String colegio;
    private String direccion;
    private String carrera;
    private String telefono;
    private String nacionalidad;
    private String modalidad;
    private String observaciones;

}
