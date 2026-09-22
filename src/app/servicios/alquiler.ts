import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
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
}