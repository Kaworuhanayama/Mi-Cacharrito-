import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Alquiler } from '../servicios/alquiler';
import { SolicitudAlquiler } from '../entities/solicitud-alquiler';

@Component({
  imports: [CommonModule, FormsModule],
  selector: 'app-formulario-alquiler',
  styleUrl: './formulario-alquiler.css',
  templateUrl: './formulario-alquiler.html',
})
export class FormularioAlquiler {
  solicitud: SolicitudAlquiler = new SolicitudAlquiler();

  enviando = false;
  error = '';
  confirmado = false;
  numeroAlquiler: number | null = null;
  pdfUrl: string | null = null;

  constructor(private servicioAlquiler: Alquiler) {}

  confirmarAlquiler(): void {
    this.error = '';

    if (!this.solicitud.identificacionUsuario || !this.solicitud.nombreUsuario ||
        !this.solicitud.placa || !this.solicitud.fechaInicio || !this.solicitud.fechaEntrega) {
      this.error = 'Completa todos los campos.';
      return;
    }

    if (this.solicitud.fechaEntrega < this.solicitud.fechaInicio) {
      this.error = 'La fecha de entrega no puede ser anterior a la fecha de inicio.';
      return;
    }

    this.enviando = true;

    this.servicioAlquiler.crearAlquiler(this.solicitud).subscribe({
      next: (respuesta) => {
        this.enviando = false;
        this.confirmado = true;

        const numero = respuesta.headers.get('X-Numero-Alquiler');
        this.numeroAlquiler = numero ? Number(numero) : null;

        const blob = respuesta.body as Blob;
        this.pdfUrl = URL.createObjectURL(blob);
      },
      error: (err) => {
        this.enviando = false;

        if (err.error instanceof Blob) {
          err.error.text().then((texto: string) => {
            this.error = texto || 'Ocurrió un error al crear el alquiler.';
          });
        } else {
          this.error = err.error || 'Ocurrió un error al crear el alquiler.';
        }
      }
    });
  }

  descargarPdf(): void {
    if (!this.pdfUrl || !this.numeroAlquiler) return;
    const enlace = document.createElement('a');
    enlace.href = this.pdfUrl;
    enlace.download = `alquiler-${this.numeroAlquiler}.pdf`;
    enlace.click();
  }

  cancelarAlquiler(): void {
    if (!this.numeroAlquiler) return;

    this.servicioAlquiler.cancelarAlquiler(this.numeroAlquiler).subscribe({
      next: () => {
        this.confirmado = false;
        this.numeroAlquiler = null;
        this.pdfUrl = null;
        this.solicitud = new SolicitudAlquiler();
      },
      error: () => {
        this.error = 'No se pudo cancelar el alquiler.';
      }
    });
  }
} 