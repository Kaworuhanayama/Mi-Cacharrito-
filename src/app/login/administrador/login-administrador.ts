import { CommonModule } from '@angular/common';
import { Component, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Autenticacion, RespuestaAutenticacion } from '../servicios/autenticacion';
import { Alquiler } from '../../servicios/alquiler';

@Component({
  imports: [CommonModule, FormsModule, RouterLink],
  selector: 'app-login-administrador',
  styleUrl: './login-administrador.css',
  templateUrl: './login-administrador.html',
})
export class LoginAdministrador {
  identificacion = '';
  contrasena = '';
  mostrarAcceso = false;
  error = '';
  enviando = false;

  // Variables para la lista de vehículos no entregados
  listaNoEntregados: any[] = [];
  cargandoNoEntregados = false;
  errorNoEntregados = '';

  constructor(
    private readonly autenticacion: Autenticacion,
    private readonly servicioAlquiler: Alquiler,
    private readonly cdr: ChangeDetectorRef
  ) {}

  ingresar(): void {
    this.mostrarAcceso = false;
    this.error = '';
    this.enviando = true;

    this.autenticacion.ingresarAdministrador({
      identificacion: this.identificacion.trim(),
      password: this.contrasena,
    }).subscribe({
      next: (respuesta: RespuestaAutenticacion) => {
        this.enviando = false;
        this.mostrarAcceso = true;
        sessionStorage.setItem('mi-cacharrito-sesion', JSON.stringify(respuesta));
        this.cargarNoEntregados();
      },
      error: (error: HttpErrorResponse) => {
        this.enviando = false;
        this.error = error.error?.mensaje || 'No se pudo iniciar sesión. Verifica tus credenciales.';
      },
    });
  }

  cargarNoEntregados(): void {
    this.cargandoNoEntregados = true;
    this.errorNoEntregados = '';

    this.servicioAlquiler.obtenerNoEntregados().subscribe({
      next: (datos) => {
        this.listaNoEntregados = datos;
        this.cargandoNoEntregados = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.cargandoNoEntregados = false;
        this.errorNoEntregados = 'No se pudo cargar la lista de vehículos no entregados.';
        this.cdr.detectChanges();
      }
    });
  }
}