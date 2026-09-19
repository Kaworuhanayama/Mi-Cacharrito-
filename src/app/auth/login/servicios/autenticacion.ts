import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

export interface SolicitudLogin {
  identificacion: string;
  password: string;
}

export interface RespuestaAutenticacion {
  mensaje: string;
  idUsuario: number;
  identificacion: string;
  nombreCompleto: string;
  rol: string;
}

@Injectable({ providedIn: 'root' })
export class Autenticacion {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api/autenticacion';

  ingresarUsuario(solicitud: SolicitudLogin): Observable<RespuestaAutenticacion> {
    return this.http.post<RespuestaAutenticacion>(`${this.baseUrl}/login/usuario`, solicitud);
  }

  ingresarAdministrador(solicitud: SolicitudLogin): Observable<RespuestaAutenticacion> {
    return this.http.post<RespuestaAutenticacion>(`${this.baseUrl}/login/administrador`, solicitud);
  }
}