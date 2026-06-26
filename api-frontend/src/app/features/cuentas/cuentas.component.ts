import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, FormsModule } from '@angular/forms';
import { CuentaService } from '../../core/services/cuenta.service';
import { ClientesService } from '../../core/services/clientes.service';
import { Cuenta } from '../../shared/models/cuenta.model';
import { Cliente } from '../../shared/models/cliente.model';

@Component({
  selector: 'app-cuentas',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './cuentas.component.html',
  styleUrl: './cuentas.component.scss'
})
export class CuentasComponent implements OnInit {
  cuentas: Cuenta[] = [];
  clientes: Cliente[] = [];
  cuentaForm!: FormGroup;
  editando = false;
  cuentaIdActual?: number;
  mostrarFormulario = false;
  terminoBusqueda: string ='';

  constructor(
    private fb: FormBuilder,
    private cuentaService: CuentaService,
    private clientesService: ClientesService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.obtenerCuentas();
    this.obtenerClientes();
  }

  initForm(): void {
    this.cuentaForm = this.fb.group({
      numeroCuenta: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      tipoCuenta: ['', Validators.required],
      saldoInicial: [0, [Validators.required, Validators.min(0)]],
      estado: [true, Validators.required],
      clienteId: ['', Validators.required]
    });
  }

  get cuentasFiltradas(): Cuenta[]{
    if(!this.terminoBusqueda.trim()){
      return this.cuentas;
    }
    const termino = this.terminoBusqueda.toLowerCase().trim();

    return this.cuentas.filter(cuenta =>
      cuenta.numeroCuenta.includes(termino) ||
      cuenta.clienteId?.toLowerCase().includes(termino)
    )
  }

  obtenerCuentas(): void {
    this.cuentaService.listarTodas().subscribe({
      next: (data) => this.cuentas = data,
      error: (err) => alert('Error al cargar cuentas: ' + err.message)
    });
  }

  obtenerClientes(): void {
    this.clientesService.listarTodos().subscribe({
      next: (data) => this.clientes = data,
      error: (err) => alert('Error al cargar clientes para el selector: ' + err.message)
    });
  }

  guardarCuenta(): void {
    if (this.cuentaForm.invalid) {
      this.cuentaForm.markAllAsTouched();
      return;
    }

    const cuentaData: Cuenta = this.cuentaForm.value;

    if (this.editando && this.cuentaIdActual) {
      this.cuentaService.actualizar(this.cuentaIdActual, cuentaData).subscribe({
        next: () => {
          alert('Cuenta modificada con exito');
          this.resetearVista();
        },
        error: (err) => alert('Error al actualizar cuenta: ' + err.message)
      });
    } else {
      this.cuentaService.crear(cuentaData).subscribe({
        next: () => {
          alert('Cuenta creada con exito');
          this.resetearVista();
        },
        error: (err) => alert('Error al crear cuenta: ' + err.message)
      });
    }
  }

  seleccionarEditar(cuenta: Cuenta): void {
    this.editando = true;
    this.cuentaIdActual = cuenta.id;
    this.mostrarFormulario = true;
    
    this.cuentaForm.patchValue({
      numeroCuenta: cuenta.numeroCuenta,
      tipoCuenta: cuenta.tipoCuenta,
      saldoInicial: cuenta.saldoInicial,
      estado: cuenta.estado,
      clienteId: cuenta.cliente?.clienteId
    });
  }

  eliminarCuenta(id: number): void {
    if (confirm('Esta seguro de eliminar esta cuenta?')) {
      this.cuentaService.eliminar(id).subscribe({
        next: () => {
          alert('Cuenta eliminada (desactivada)');
          this.obtenerCuentas();
        },
        error: (err) => alert('Error al eliminar: ' + err.message)
      });
    }
  }

  abrirFormularioNuevo(): void {
    this.editando = false;
    this.cuentaIdActual = undefined;
    this.cuentaForm.reset({ estado: true, saldoInicial: 0 });
    this.mostrarFormulario = true;
  }

  resetearVista(): void {
    this.mostrarFormulario = false;
    this.editando = false;
    this.cuentaIdActual = undefined;
    this.cuentaForm.reset();
    this.obtenerCuentas();
  }
}