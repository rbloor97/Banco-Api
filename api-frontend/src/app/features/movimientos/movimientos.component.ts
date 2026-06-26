import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MovimientoService } from '../../core/services/movimiento.service';
import { CuentaService } from '../../core/services/cuenta.service';
import { Cuenta } from '../../shared/models/cuenta.model';

@Component({
  selector: 'app-movimientos',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './movimientos.component.html',
  styleUrl: './movimientos.component.scss'
})
export class MovimientosComponent implements OnInit {
  movimientoForm!: FormGroup;
  cuentas: Cuenta[] = [];
  procesando = false;

  constructor(
    private fb: FormBuilder,
    private movimientoService: MovimientoService,
    private cuentaService: CuentaService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.cargarCuentas();
  }

  initForm(): void {
    this.movimientoForm = this.fb.group({
      numeroCuenta: ['', Validators.required],
      tipoMovimiento: ['', Validators.required],
      valor: ['', [Validators.required, Validators.min(0.01)]]
    });
  }

  cargarCuentas(): void {
    this.cuentaService.listarTodas().subscribe({
      next: (data) => this.cuentas = data.filter(c => c.estado),
      error: (err) => alert('Error al obtener las cuentas: ' + err.message)
    });
  }

  ejecutarTransaccion(): void {
    if (this.movimientoForm.invalid) {
      this.movimientoForm.markAllAsTouched();
      return;
    }

    this.procesando = true;
    const datosFormulario = this.movimientoForm.value;

    this.movimientoService.crearMovimiento(datosFormulario).subscribe({
      next: (respuesta) => {
        alert(`Transaccion Exitosa!\nNuevo Saldo Disponible: $${respuesta.saldo}`);
        this.movimientoForm.reset({ numeroCuenta: '', tipoMovimiento: '' });
        this.procesando = false;
        this.cargarCuentas();
      },
      error: (err) => {
        const mensajeError = err.error?.error   || 'Error al procesar la transaccion';
        alert('Error en Transaccion2: ' + (mensajeError));
        this.procesando = false;
      }
    });
  }
}