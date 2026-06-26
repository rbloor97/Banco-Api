import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ClientesComponent } from './clientes.component';
import { ClientesService } from '../../core/services/clientes.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { Cliente } from '../../shared/models/cliente.model';

describe('ClientesComponent con Jest', () => {
  let component: ClientesComponent;
  let fixture: ComponentFixture<ClientesComponent>;
  let clienteService: ClientesService;

  const mockClientes: Cliente[] = [
    { clienteId: 'CL01', nombre: 'Renzo Test', identificacion: '0999999999', edad: 29, telefono: '0987654321', contraseña: '123', estado: true, genero: 'masculino', direccion: 'Ecuador' }
  ];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        ClientesComponent,
        HttpClientTestingModule,
        ReactiveFormsModule,
        FormsModule
      ],
      providers: [ClientesService]
    }).compileComponents();

    fixture = TestBed.createComponent(ClientesComponent);
    component = fixture.componentInstance;
    clienteService = TestBed.inject(ClientesService);

    jest.spyOn(clienteService, 'listarTodos').mockReturnValue(of(mockClientes));
    fixture.detectChanges();
  });

  it('Debe crear el componente correctamente', () => {
    expect(component).toBeTruthy();
  });

  it('Debe cargar los clientes al inicializar el componente', () => {
    expect(component.clientes.length).toBe(1);
    expect(component.clientes[0].nombre).toBe('Renzo Test');
  });

  it('Debe filtrar la lista de clientes correctamente', () => {
    component.terminoBusqueda = 'Renzo';
    expect(component.clientesFiltrados.length).toBe(1);

    component.terminoBusqueda = 'Inexistente';
    expect(component.clientesFiltrados.length).toBe(0);
  });
});
