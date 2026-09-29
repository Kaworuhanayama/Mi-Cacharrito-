import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Alquiler } from '../servicios/alquiler';

@Component({
  imports: [CommonModule, FormsModule],
  selector: 'app-cancelar-alquiler',
  styleUrl: './cancelar-alquiler.css',
  templateUrl: './cancelar-alquiler.html',
})
export class CancelarAlquiler {
  numeroAlquiler: number | null = null;
  mensaje = '';
  error = '';
  procesando = false;

  constructor(private servicioAlquiler: Alquiler, private cdr: ChangeDetectorRef) {}

  cancelar(): void {
    this.mensaje = '';
    this.error = '';

    if (!this.numeroAlquiler) {
      this.error = 'Ingresa el número de alquiler.';
      return;
    }

    this.procesando = true;

    this.servicioAlquiler.cancelarAlquiler(this.numeroAlquiler).subscribe({
      next: (respuesta) => {
        this.procesando = false;
        this.mensaje = respuesta;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.procesando = false;
        this.error = typeof err.error === 'string' ? err.error : 'No se pudo cancelar el alquiler. Verifica el número.';
        this.cdr.detectChanges();
      }
    });
  }
}