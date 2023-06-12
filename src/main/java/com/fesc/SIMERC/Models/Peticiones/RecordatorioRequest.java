package com.fesc.SIMERC.Models.Peticiones;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RecordatorioRequest {

    private String titulo;
    private String desc;
    private Date fecha;
    private String email; // email persona logeada

}
