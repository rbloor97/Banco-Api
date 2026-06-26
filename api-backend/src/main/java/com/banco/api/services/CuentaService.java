package com.banco.api.services;

import com.banco.api.dtos.CuentaDTO;
import com.banco.api.entities.Cuenta;
import java.util.List;
import java.util.Map;

public interface CuentaService {
    List<Cuenta> listarTodas();
    Cuenta obtenerPorId(Long id);
    Cuenta guardar(CuentaDTO cuentaDTO);
    Cuenta actualizar(Long id, CuentaDTO cuentaDTO);
    Cuenta actualizarParcial(Long id, Map<String, Object> campos);
    void eliminar(Long id);
}
