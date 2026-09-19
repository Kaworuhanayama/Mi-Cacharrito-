import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Autenticacion, RespuestaAutenticacion } from '../servicios/autenticacion';

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

  constructor(private readonly autenticacion: Autenticacion) {}

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
      },
      error: (error: HttpErrorResponse) => {
        this.enviando = false;
        this.error = error.error?.mensaje || 'No se pudo iniciar sesión. Verifica tus credenciales.';
      },
    });
  }
}