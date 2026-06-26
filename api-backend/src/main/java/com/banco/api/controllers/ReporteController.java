package com.banco.api.controllers;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.api.dtos.ReporteEstadoCuentaDTO;
import com.banco.api.services.MovimientoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/reportes")
@CrossOrigin(origins="*")
public class ReporteController {
    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public ResponseEntity<ReporteEstadoCuentaDTO> obtenerReporte(
            @RequestParam("cliente") String clienteId,
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        ReporteEstadoCuentaDTO reporte = movimientoService.generarReporte(clienteId, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
    
}
