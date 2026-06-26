package com.banco.api.services;

import com.banco.api.dtos.DetalleReporteDTO;
import com.banco.api.dtos.ReporteEstadoCuentaDTO;
import com.banco.api.entities.Cliente;
import com.banco.api.entities.Cuenta;
import com.banco.api.entities.Movimiento;
import com.banco.api.exceptions.CupoDiarioException;
import com.banco.api.exceptions.SaldoException;
import com.banco.api.repositories.ClienteRepository;
import com.banco.api.repositories.CuentaRepository;
import com.banco.api.repositories.MovimientoRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Movimiento registrarMovimiento(String numeroCuenta, String tipoMovimiento, BigDecimal valor) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        if (tipoMovimiento.equalsIgnoreCase("Debito") && valor.compareTo(BigDecimal.ZERO) > 0) {
            valor = valor.negate();
        }

        BigDecimal saldoActual = cuenta.getSaldoInicial();

        if (tipoMovimiento.equalsIgnoreCase("Debito")) {
          
            LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
            LocalDateTime finDia = LocalDate.now().atTime(LocalTime.MAX);
            
            BigDecimal debitosDelDia = movimientoRepository.sumDebitosDelDia(cuenta.getId(), inicioDia, finDia);
            BigDecimal totalConNuevoDebito = debitosDelDia.add(valor.abs());

            if (totalConNuevoDebito.compareTo(new BigDecimal("1000")) > 0) {
                throw new CupoDiarioException();
            }
           
            if (saldoActual.add(valor).compareTo(BigDecimal.ZERO) < 0) {
                throw new SaldoException();
            }
        }

        BigDecimal nuevoSaldo = saldoActual.add(valor);
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setTipoMovimiento(tipoMovimiento);
        movimiento.setValor(valor);
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuenta(cuenta);

        return movimientoRepository.save(movimiento);
    }

@Override
@Transactional(readOnly = true)
public ReporteEstadoCuentaDTO generarReporte(String clienteId, LocalDate inicio, LocalDate fin) {
    Cliente cliente = clienteRepository.findByClienteId(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

    LocalDateTime desde = inicio.atStartOfDay();
    LocalDateTime hasta = fin.atTime(LocalTime.MAX);

    List<Movimiento> movimientos = movimientoRepository.findByCuentaClienteIdAndFechaBetween(cliente.getId(), desde, hasta);

    List<DetalleReporteDTO> detalles = movimientos.stream().map(m -> {
        BigDecimal saldoAnteTransaccion = m.getSaldo().subtract(m.getValor());
        return DetalleReporteDTO.builder()
                .fecha(m.getFecha().toLocalDate().toString())
                .numeroCuenta(m.getCuenta().getNumeroCuenta())
                .tipo(m.getCuenta().getTipoCuenta())
                .saldoInicial(saldoAnteTransaccion)
                .estado(m.getCuenta().getEstado())
                .movimiento(m.getValor())
                .saldoDisponible(m.getSaldo())
                .build();
    }).collect(Collectors.toList());

    String pdfBase64 = "";
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        document.add(new Paragraph("ESTADO DE CUENTA BANCARIO"));
        document.add(new Paragraph("Cliente: " + cliente.getNombre()));
        document.add(new Paragraph("Periodo: " + inicio + " al " + fin));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(6);
        table.addCell("Fecha");
        table.addCell("Cuenta");
        table.addCell("Tipo");
        table.addCell("Saldo Inicial");
        table.addCell("Movimiento");
        table.addCell("Saldo Disp.");

        detalles.forEach(d -> {
            table.addCell(d.getFecha());
            table.addCell(d.getNumeroCuenta());
            table.addCell(d.getTipo());
            table.addCell(d.getSaldoInicial().toString());
            table.addCell(d.getMovimiento().toString());
            table.addCell(d.getSaldoDisponible().toString());
        });

        document.add(table);
        document.close();

        pdfBase64 = Base64.getEncoder().encodeToString(baos.toByteArray());
    } catch (Exception e) {
        throw new RuntimeException("Error al generar el reporte PDF", e);
    }

    return ReporteEstadoCuentaDTO.builder()
            .cliente(cliente.getNombre())
            .rangoFechas(inicio + " a " + fin)
            .detalles(detalles)
            .pdfBase64(pdfBase64)
            .build();
}
}

