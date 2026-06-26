package com.banco.api.controllers;

import com.banco.api.entities.Movimiento;
import com.banco.api.services.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/movimientos")
@CrossOrigin(origins = "*")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<Movimiento> crearMovimiento(@RequestBody Map<String, Object> body) {
        String numeroCuenta = (String) body.get("numeroCuenta");
        String tipoMovimiento = (String) body.get("tipoMovimiento");
        java.math.BigDecimal valor = new java.math.BigDecimal(body.get("valor").toString());

        Movimiento nuevo = movimientoService.registrarMovimiento(numeroCuenta, tipoMovimiento, valor);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }
}
