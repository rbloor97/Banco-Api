import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ClientesService } from '../../core/services/clientes.service';
import { Cliente } from '../../shared/models/cliente.model';

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './reportes.component.html',
  styleUrl: './reportes.component.scss'
})
export class ReportesComponent implements OnInit {
  filtroForm!: FormGroup;
  clientes: Cliente[] = [];
  reporteData: any = null;
  buscando = false;

  constructor(
    private fb: FormBuilder,
    private clientesService: ClientesService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.cargarClientes();
  }

  initForm(): void {
    this.filtroForm = this.fb.group({
      clienteId: ['', Validators.required],
      fechaInicio: ['', Validators.required],
      fechaFin: ['', Validators.required]
    });
  }

  cargarClientes(): void {
    this.clientesService.listarTodos().subscribe({
      next: (data) => this.clientes = data,
      error: (err) => alert('Error al cargar filtros: ' + err.message)
    });
  }

  consultarReporte(): void {
    if (this.filtroForm.invalid) {
      this.filtroForm.markAllAsTouched();
      return;
    }

    this.buscando = true;
    const { clienteId, fechaInicio, fechaFin } = this.filtroForm.value;

    const params = new HttpParams()
      .set('cliente', clienteId)
      .set('fechaInicio', fechaInicio)
      .set('fechaFin', fechaFin);

    this.http.get<any>('http://localhost:8080/reportes', { params }).subscribe({
      next: (data) => {
        this.reporteData = data;
        this.buscando = false;
      },
      error: (err) => {
        alert('Error al generar el estado de cuenta: ' + (err.error?.message || err.message));
        this.buscando = false;
        this.reporteData = null;
      }
    });
  }

  descargarPDF(): void {
    if (!this.reporteData || !this.reporteData.pdfBase64) {
      alert('No hay un documento PDF disponible para descargar');
      return;
    }

    try {
      const byteCharacters = atob(this.reporteData.pdfBase64);
      const byteNumbers = new Array(byteCharacters.length);
      
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      
      const byteArray = new Uint8Array(byteNumbers);
      
      const blob = new Blob([byteArray], { type: 'application/pdf' });
      
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = `Estado_Cuenta_${this.filtroForm.value.clienteId}.pdf`;
      
      document.body.appendChild(link);
      link.click();
      
      document.body.removeChild(link);
      window.URL.revokeObjectURL(url);
    } catch (e) {
      alert('Ocurrio un error al procesar el archivo binario del PDF.');
    }
  }
}