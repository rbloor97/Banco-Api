package com.banco.api;

import com.banco.api.entities.Cliente;
import com.banco.api.entities.Cuenta;
import com.banco.api.entities.Movimiento;
import com.banco.api.exceptions.CupoDiarioException;
import com.banco.api.exceptions.SaldoException;
import com.banco.api.repositories.ClienteRepository;
import com.banco.api.repositories.CuentaRepository;
import com.banco.api.repositories.MovimientoRepository;
import com.banco.api.services.MovimientoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiApplicationTests {

	@Autowired
    private MovimientoService movimientoService;

    @Autowired
    private CuentaRepository cuentaRepository;

	@Autowired
	private MovimientoRepository movimientoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private String numeroCuentaTest = "123456";

    @BeforeEach
    public void setUp() {
		movimientoRepository.deleteAll();
        cuentaRepository.deleteAll();
        clienteRepository.deleteAll();

        Cliente cliente = new Cliente();
        cliente.setNombre("Renzo Test");
        cliente.setGenero("Masculino");
        cliente.setEdad(29);
        cliente.setIdentificacion("0999999999");
        cliente.setDireccion("Ecuador");
        cliente.setTelefono("0987654321");
        cliente.setClienteId("RELOOR");
        cliente.setContraseña("1234");
        cliente.setEstado(true);
        cliente = clienteRepository.save(cliente);

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(numeroCuentaTest);
        cuenta.setTipoCuenta("Ahorro");
        cuenta.setSaldoInicial(new BigDecimal("500"));
        cuenta.setEstado(true);
        cuenta.setCliente(cliente);
        cuentaRepository.save(cuenta);
    }

    @Test
    public void testRegistrarMovimiento_CreditoExitoso() {
        Movimiento resultado = movimientoService.registrarMovimiento(numeroCuentaTest, "Credito", new BigDecimal("200"));

        assertNotNull(resultado);
        assertEquals(new BigDecimal("700.00"), resultado.getSaldo());
    }

    @Test
    public void testRegistrarMovimiento_SaldoNoDisponible() {
        
        assertThrows(SaldoException.class, () -> {
            movimientoService.registrarMovimiento(numeroCuentaTest, "Debito", new BigDecimal("600.00"));
        });
    }

    @Test
    public void testRegistrarMovimiento_CupoDiarioExcedido() {
        
        assertThrows(CupoDiarioException.class, () -> {
            movimientoService.registrarMovimiento(numeroCuentaTest, "Debito", new BigDecimal("1100.00"));
        });
    }

}
