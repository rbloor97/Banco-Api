import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MovimientoService {
  private apiUrl = 'http://localhost:8080/movimientos';

  constructor(private http: HttpClient) { }

  crearMovimiento(movimiento: { numeroCuenta: string; tipoMovimiento: string; valor:number}): Observable<any>{
    return this.http.post<any>(this.apiUrl, movimiento);
  }
}
