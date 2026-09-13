import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  private readonly formBuilder = inject(FormBuilder);

  protected readonly loginForm = this.formBuilder.nonNullable.group({
    nombreUsuario: ['', Validators.required],
    contrasena: ['', [Validators.required, Validators.minLength(6)]],
  });
  protected mostrarAcceso = false;

  protected submit(): void {
    this.mostrarAcceso = false;

    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.mostrarAcceso = true;
  }
}