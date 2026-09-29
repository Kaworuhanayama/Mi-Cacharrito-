import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Alquiler } from '../servicios/alquiler';

@Component({
  selector: 'app-devolucion-alquiler',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './devolucion-alquiler.html',
  styleUrl: './devolucion-alquiler.css'
})
export class DevolucionAlquiler {
  tipoBusqueda: string = 'numero';
  valorBusqueda: string = '';

  alquilerEncontrado: any = null;
  resultadoDevolucion: any = null;

  cargando: boolean = false;
  error: string = '';
  mensajeExito: string = '';

  constructor(
    private servicioAlquiler: Alquiler,
    private cdr: ChangeDetectorRef
  ) {}

  buscar(): void {
    this.error = '';
    this.mensajeExito = '';
    this.alquilerEncontrado = null;
    this.resultadoDevolucion = null;

    if (!this.valorBusqueda.trim()) {
      this.error = 'Por favor ingrese un valor para buscar.';
      return;
    }

    this.cargando = true;
    this.servicioAlquiler.buscarAlquiler(this.tipoBusqueda, this.valorBusqueda.trim()).subscribe({
      next: (data) => {
        this.cargando = false;
        this.alquilerEncontrado = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.cargando = false;
        this.error = typeof err.error === 'string' ? err.error : 'No se encontró un alquiler con los datos ingresados.';
        this.cdr.detectChanges();
      }
    });
  }

  registrarDevolucion(numeroAlquiler: number): void {
    this.cargando = true;
    this.error = '';

    this.servicioAlquiler.procesarDevolucion(numeroAlquiler).subscribe({
      next: (res) => {
        this.cargando = false;
        this.resultadoDevolucion = res;
        this.mensajeExito = res.mensaje;
        this.alquilerEncontrado = null;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.cargando = false;
        this.error = typeof err.error === 'string' ? err.error : 'Ocurrió un error al procesar la devolución.';
        this.cdr.detectChanges();
      }
    });
  }
}