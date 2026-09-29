import { Component, inject, signal } from '@angular/core';
import { NavigationEnd, Router, RouterLink, RouterOutlet } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  imports: [RouterOutlet, RouterLink],
  selector: 'app-navegacion',
  styleUrl: './navegacion.css',
  templateUrl: './navegacion.html',
})
export class Navegacion {
  private readonly router = inject(Router);
  haySesion = signal(!!sessionStorage.getItem('mi-cacharrito-sesion'));

  constructor() {
    this.router.events
      .pipe(filter((evento) => evento instanceof NavigationEnd))
      .subscribe(() => {
        this.haySesion.set(!!sessionStorage.getItem('mi-cacharrito-sesion'));
      });
  }

  cerrarSesion(): void {
    sessionStorage.removeItem('mi-cacharrito-sesion');
    this.haySesion.set(false);
    this.router.navigate(['/login']);
  }
}