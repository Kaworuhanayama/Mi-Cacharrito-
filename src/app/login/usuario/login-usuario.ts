import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Autenticacion, RespuestaAutenticacion } from '../servicios/autenticacion';

@Component({
  imports: [CommonModule, FormsModule, RouterLink],
  selector: 'app-login-usuario',
  styleUrl: './login-usuario.css',
  templateUrl: './login-usuario.html',
})
export class LoginUsuario {
  private readonly autenticacion = inject(Autenticacion);
  private readonly cdr = inject(ChangeDetectorRef);
  private readonly router = inject(Router);
  nombreUsuario = '';
  contrasena = '';
  mostrarAcceso = false;
  error = '';
  enviando = false;

  ingresar(): void {
    this.mostrarAcceso = false;
    this.error = '';
    this.enviando = true;

    this.autenticacion.ingresarUsuario({
      identificacion: this.nombreUsuario.trim(),
      password: this.contrasena,
    }).subscribe({
      next: (respuesta: RespuestaAutenticacion) => {
        this.enviando = false;
        sessionStorage.setItem('mi-cacharrito-sesion', JSON.stringify(respuesta));
        this.router.navigate(['/home']);
      },
      error: (error: HttpErrorResponse) => {
        this.enviando = false;
        this.error = error.error?.mensaje || 'No se pudo iniciar sesión. Verifica tus credenciales.';
        this.cdr.detectChanges();
      },
    });
  }
}