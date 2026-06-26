import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {Cuenta} from '../../shared/models/cuenta.model';

@Injectable({
  providedIn: 'root'
})
export class CuentaService {
  private apiUrl = 'http://localhost:8080/cuentas';

  constructor(private http: HttpClient) { }

  listarTodas(): Observable<Cuenta[]>{
      return this.http.get<Cuenta[]>(this.apiUrl);
    }
  
    obtenerPorId(id: number): Observable<Cuenta>{
      return this.http.get<Cuenta>(`${this.apiUrl}/${id}`);
    }
  
    crear(cuenta: Cuenta): Observable<Cuenta>{
      return this.http.post<Cuenta>(this.apiUrl, cuenta);
    }
  
    actualizar(id: number, cuenta: Cuenta): Observable<Cuenta>{
      return this.http.put<Cuenta>(`${this.apiUrl}/${id}`,cuenta);
    }
  
    eliminar(id: number): Observable<void>{
      return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }

}
