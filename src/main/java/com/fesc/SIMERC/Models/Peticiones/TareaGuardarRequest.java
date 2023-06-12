package com.fesc.SIMERC.Models.Peticiones;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TareaGuardarRequest {

    private String titulo;
    private String desc;
    private String email; // email del usuario al que van asignarle la tarea
}
