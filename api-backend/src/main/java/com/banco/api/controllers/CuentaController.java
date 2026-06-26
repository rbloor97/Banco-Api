package com.banco.api.controllers;

import com.banco.api.dtos.CuentaDTO;
import com.banco.api.entities.Cuenta;
import com.banco.api.services.CuentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cuentas")
@CrossOrigin(origins = "*")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<Cuenta>> obtenerTodas() {
        return ResponseEntity.ok(cuentaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Cuenta> crear(@Valid @RequestBody CuentaDTO cuentaDTO) {
        Cuenta nuevaCuenta = cuentaService.guardar(cuentaDTO);
        return new ResponseEntity<>(nuevaCuenta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizar(@PathVariable Long id, @Valid @RequestBody CuentaDTO cuentaDTO) {
        return ResponseEntity.ok(cuentaService.actualizar(id, cuentaDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cuenta> actualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        return ResponseEntity.ok(cuentaService.actualizarParcial(id, campos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cuentaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
