import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Vehiculo { idVehiculo:number; placa:string; tipoVehiculo:string; color:string; valorAlquiler:number; estado:string; numeroAlquiler:number|null; fechaDevolucion:string|null; }
@Injectable({providedIn:'root'})
export class VehiculoService {
  private http=inject(HttpClient); private readonly base='http://localhost:8080/ListaVehiculos/V';
  listarPendientes():Observable<Vehiculo[]>{return this.http.get<Vehiculo[]>(`${this.base}/pendientes`)}
  listarDisponibles():Observable<Vehiculo[]>{return this.http.get<Vehiculo[]>(`${this.base}/disponibles`)}
  buscarPlaca(placa:string):Observable<Vehiculo>{return this.http.get<Vehiculo>(`${this.base}/placa/${encodeURIComponent(placa)}`)}
  buscarAlquiler(numero:number):Observable<Vehiculo>{return this.http.get<Vehiculo>(`${this.base}/alquiler/${numero}`)}
  entregar(placa:string):Observable<Vehiculo>{return this.http.put<Vehiculo>(`${this.base}/entregar/${encodeURIComponent(placa)}`,{})}
  finalizar(numero:number):Observable<Vehiculo>{return this.http.put<Vehiculo>(`${this.base}/finalizar/${numero}`,{})}
}
