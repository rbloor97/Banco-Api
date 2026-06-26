package com.banco.api.controllers;

import com.banco.api.dtos.ClienteDTO;
import com.banco.api.entities.Cliente;
import com.banco.api.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Cliente> crear(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente nuevoCliente = clienteService.guardar(clienteDTO);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.ok(clienteService.actualizar(id, clienteDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cliente> actualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        return ResponseEntity.ok(clienteService.actualizarParcial(id, campos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
