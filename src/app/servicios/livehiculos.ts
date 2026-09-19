import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vehiculo } from '../entities/vehiculo';

@Injectable({
  providedIn: 'root'
})
export class Livehiculos {
  private baseUrl = 'http://localhost:8080/ListaVehiculos/V'; // ajusta el puerto si cambia

  constructor(private http: HttpClient) {}

  listarVehiculos(): Observable<Vehiculo[]> {
    return this.http.get<Vehiculo[]>(this.baseUrl);
  }

  

  listarDisponibles(): Observable<Vehiculo[]> {
    return this.http.get<Vehiculo[]>(`${this.baseUrl}/disponibles`);
  }

  listarPorTipo(tipo: string): Observable<Vehiculo[]> {
    return this.http.get<Vehiculo[]>(`${this.baseUrl}/tipo/${tipo}`);
  }

  listarPorTipoDisponibles(tipo: string): Observable<Vehiculo[]> {
    return this.http.get<Vehiculo[]>(`${this.baseUrl}/tipo/${tipo}/disponibles`);
  }
  obtenerPdf(numeroAlquiler: number): Observable<Blob> {
  return this.http.get(`${this.baseUrl}/pdf/${numeroAlquiler}`, { responseType: 'blob' });
}
}