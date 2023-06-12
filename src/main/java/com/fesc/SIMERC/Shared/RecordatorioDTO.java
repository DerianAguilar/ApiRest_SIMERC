package com.fesc.SIMERC.Shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RecordatorioDTO implements Serializable {

    private static final long serialVersionUID=1L;

    private String titulo;
    private String desc;
    private Date fecha;
    private String email;

}
