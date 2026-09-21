import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Autenticacion, RespuestaAutenticacion, SolicitudRegistroUsuario } from '../login/servicios/autenticacion';

@Component({
  imports: [CommonModule, FormsModule, RouterLink],
  selector: 'app-registro-usuario',
  styleUrl: './registro-usuario.css',
  templateUrl: './registro-usuario.html',
})
export class RegistroUsuario {
  private readonly autenticacion = inject(Autenticacion);

  registro: SolicitudRegistroUsuario = {
    identificacion: '',
    nombreCompleto: '',
    fechaExpedicionLicencia: '',
    categoria: '',
    vigencia: '',
    correoElectronico: '',
    numeroTelefono: '',
    password: '',
  };
  confirmarPassword = '';
  error = '';
  registrado = false;
  enviando = false;

  registrar(formulario: NgForm): void {
    this.error = '';
    this.registrado = false;

    if (!formulario.valid) {
      this.error = 'Completa todos los campos obligatorios.';
      return;
    }

    if (this.registro.password !== this.confirmarPassword) {
      this.error = 'Las contraseñas no coinciden.';
      return;
    }

    this.enviando = true;
    this.autenticacion.registrarUsuario(this.registro).subscribe({
      next: (_respuesta: RespuestaAutenticacion) => {
        this.enviando = false;
        this.registrado = true;
      },
      error: (respuesta: HttpErrorResponse) => {
        this.enviando = false;
        this.error = respuesta.error?.mensaje || 'No se pudo completar el registro.';
      },
    });
  }
}