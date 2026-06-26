package com.banco.api.dtos;

import java.util.List;

import lombok.*;

@Getter @Setter @Builder
public class ReporteEstadoCuentaDTO {
    private String cliente;
    private String rangoFechas;
    private List<DetalleReporteDTO> detalles;
    private String pdfBase64;
}
