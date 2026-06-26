package com.banco.api.services;

import com.banco.api.dtos.CuentaDTO;
import com.banco.api.entities.Cliente;
import com.banco.api.entities.Cuenta;
import com.banco.api.repositories.ClienteRepository;
import com.banco.api.repositories.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> listarTodas() {
        return cuentaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta obtenerPorId(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public Cuenta guardar(CuentaDTO dto) {
        if (cuentaRepository.findByNumeroCuenta(dto.getNumeroCuenta()).isPresent()) {
            throw new RuntimeException("El numero de cuenta ya se encuentra registrado");
        }

        Cliente cliente = clienteRepository.findByClienteId(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("El Cliente con ID '" + dto.getClienteId() + "' no existe"));

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setEstado(dto.getEstado());
        cuenta.setCliente(cliente);

        return cuentaRepository.save(cuenta);
    }

    @Override
    @Transactional
    public Cuenta actualizar(Long id, CuentaDTO dto) {
        Cuenta cuenta = obtenerPorId(id);
        
        if (!cuenta.getNumeroCuenta().equals(dto.getNumeroCuenta()) && 
            cuentaRepository.findByNumeroCuenta(dto.getNumeroCuenta()).isPresent()) {
            throw new RuntimeException("El numero de cuenta pertenece a otro registro");
        }

        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setEstado(dto.getEstado());

        return cuentaRepository.save(cuenta);
    }

    @Override
    @Transactional
    public Cuenta actualizarParcial(Long id, Map<String, Object> campos) {
        Cuenta cuenta = obtenerPorId(id);

        campos.forEach((llave, valor) -> {
            Field campo = ReflectionUtils.findField(Cuenta.class, llave);
            if (campo != null) {
                campo.setAccessible(true);
                if (llave.equals("saldoInicial") && !(valor instanceof BigDecimal)) {
                    valor = new BigDecimal(valor.toString());
                }
                ReflectionUtils.setField(campo, cuenta, valor);
            }
        });

        return cuentaRepository.save(cuenta);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Cuenta cuenta = obtenerPorId(id);
        cuentaRepository.delete(cuenta);
    }
}