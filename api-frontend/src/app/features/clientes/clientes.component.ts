import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, FormsModule } from '@angular/forms';
import { ClientesService } from '../../core/services/clientes.service';
import { Cliente } from '../../shared/models/cliente.model';

@Component({
  selector: 'app-clientes',
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './clientes.component.html',
  styleUrl: './clientes.component.scss'
})
export class ClientesComponent implements OnInit{
  clientes: Cliente[] = [];
  clienteForm!: FormGroup;
  editando = false;
  clienteIdActual?: number;
  mostrarFormulario = false;
  terminoBusqueda: string ='';

  constructor(
    private fb: FormBuilder,
    private clienteService: ClientesService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.obtenerClientes();
  }

  initForm(): void {
    this.clienteForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      genero: ['', Validators.required],
      edad: ['', [Validators.required, Validators.min(18)]],
      identificacion: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      direccion: [''],
      telefono: [''],
      clienteId: ['', Validators.required],
      contraseña: ['', [Validators.required, Validators.minLength(4)]],
      estado: [true, Validators.required]
    });
  }

  get clientesFiltrados():Cliente[]{
    if(!this.terminoBusqueda.trim()){
      return this.clientes;
    }
    const termino = this.terminoBusqueda.toLowerCase().trim();

    return this.clientes.filter(cliente =>
      cliente.nombre.toLowerCase().includes(termino) ||
      cliente.identificacion.includes(termino) ||
      cliente.clienteId.toLowerCase().includes(termino)
    )
  }

  obtenerClientes(): void {
    this.clienteService.listarTodos().subscribe({
      next: (data) => this.clientes = data,
      error: (err) => alert('Error al cargar clientes: ' + err.message)
    });
  }

  guardarCliente(): void {
    if (this.clienteForm.invalid) {
      this.clienteForm.markAllAsTouched();
      return;
    }

    const clienteData: Cliente = this.clienteForm.value;

    if (this.editando && this.clienteIdActual) {
      this.clienteService.actualizar(this.clienteIdActual, clienteData).subscribe({
        next: () => {
          alert('Cliente actualizado correctamente');
          this.resetearVista();
        },
        error: (err) => alert('Error al actualizar: ' + err.message)
      });
    } else {
      this.clienteService.crear(clienteData).subscribe({
        next: () => {
          alert('Cliente creado correctamente');
          this.resetearVista();
        },
        error: (err) => alert('Error al crear cliente: ' + err.message)
      });
    }
  }

  seleccionarEditar(cliente: Cliente): void {
    this.editando = true;
    this.clienteIdActual = cliente.id;
    this.mostrarFormulario = true;
    
     this.clienteForm.get('contraseña')?.clearValidators();
    this.clienteForm.get('contraseña')?.updateValueAndValidity();

    this.clienteForm.patchValue(cliente);
  }

  eliminarCliente(id: number): void {
    if (confirm('Esta seguro de eliminar este cliente?')) {
      this.clienteService.eliminar(id).subscribe({
        next: () => {
          alert('Cliente eliminado');
          this.obtenerClientes();
        },
        error: (err) => alert('Error al eliminar: ' + err.message)
      });
    }
  }

  abrirFormularioNuevo(): void {
    this.editando = false;
    this.clienteIdActual = undefined;
    this.clienteForm.reset({ estado: true });
    this.clienteForm.get('contraseña')?.setValidators([Validators.required, Validators.minLength(4)]);
    this.clienteForm.get('contraseña')?.updateValueAndValidity();
    this.mostrarFormulario = true;
  }

  resetearVista(): void {
    this.mostrarFormulario = false;
    this.editando = false;
    this.clienteIdActual = undefined;
    this.clienteForm.reset();
    this.obtenerClientes();
  }
}
