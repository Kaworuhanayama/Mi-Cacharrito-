import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SolicitudAlquiler } from '../entities/solicitud-alquiler';

@Injectable({
  providedIn: 'root'
})
export class Alquiler {
  private baseUrl = 'http://localhost:8080/Alquiler';

  constructor(private http: HttpClient) {}

  crearAlquiler(solicitud: SolicitudAlquiler): Observable<HttpResponse<Blob>> {
    return this.http.post(`${this.baseUrl}/crear`, solicitud, {
      responseType: 'blob',
      observe: 'response'
    });
  }

  cancelarAlquiler(numeroAlquiler: number): Observable<string> {
    return this.http.put(`${this.baseUrl}/cancelar/${numeroAlquiler}`, {}, { responseType: 'text' });
  }

  // 1. Buscar alquiler por placa o por número
  buscarAlquiler(tipo: string, valor: string): Observable<any> {
    const params = new HttpParams()
      .set('tipo', tipo)
      .set('valor', valor);
    return this.http.get<any>(`${this.baseUrl}/buscar`, { params });
  }

  // 2. Procesar devolución de vehículo (cambia estado a disponible y calcula días extra)
  procesarDevolucion(numeroAlquiler: number): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/devolucion/${numeroAlquiler}`, {});
  }

  // 3. Obtener listado de vehículos no entregados (para panel de admin)
  obtenerNoEntregados(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/no-entregados`);
  }
}