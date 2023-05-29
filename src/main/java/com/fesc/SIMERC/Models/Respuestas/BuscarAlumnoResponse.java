package com.fesc.SIMERC.Models.Respuestas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class BuscarAlumnoResponse {

    private String nombre;
    private String correo;
    private String documento;
    private String carrera;

}
