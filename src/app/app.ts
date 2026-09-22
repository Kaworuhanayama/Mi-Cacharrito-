import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Navegacion } from './navegacion/navegacion';
import { ListaVehiculo } from './lista-vehiculo/lista-vehiculo';

@Component({
  imports: [Navegacion],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('mi-cacharrito-frontend');
}
