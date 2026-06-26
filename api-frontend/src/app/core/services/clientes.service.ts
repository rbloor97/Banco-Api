import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cliente } from '../../shared/models/cliente.model';

@Injectable({
  providedIn: 'root'
})
export class ClientesService {
  private apiUrl = 'http://localhost:8080/clientes';

  constructor(private http: HttpClient) { }

  listarTodos(): Observable<Cliente[]>{
    return this.http.get<Cliente[]>(this.apiUrl);
  }

  obtenerPorId(id: number): Observable<Cliente>{
    return this.http.get<Cliente>(`${this.apiUrl}/${id}`);
  }

  crear(cliente: Cliente): Observable<Cliente>{
    return this.http.post<Cliente>(this.apiUrl, cliente);
  }

  actualizar(id: number, cliente: Cliente): Observable<Cliente>{
    return this.http.put<Cliente>(`${this.apiUrl}/${id}`,cliente);
  }

  eliminar(id: number): Observable<void>{
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
