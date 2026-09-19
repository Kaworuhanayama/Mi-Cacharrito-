import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Livehiculos } from '../servicios/livehiculos';
import { Vehiculo } from '../entities/vehiculo';

@Component({
  imports: [CommonModule],
  selector: 'app-lista-vehiculo',
  styleUrl: './lista-vehiculo.css',
  templateUrl: './lista-vehiculo.html',
})
export class ListaVehiculo implements OnInit {
  tipos: string[] = ['automovil', 'camioneta', 'campero', 'microbus', 'motocicleta'];
  vehiculosPorTipo: { [tipo: string]: Vehiculo[] } = {};
  cargando = false;
  error = false;

  constructor(private servicioVehiculos: Livehiculos, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.listarVehiculos();
  }

  private listarVehiculos(): void {
    this.cargando = true;
    this.error = false;

    this.servicioVehiculos.listarVehiculos().subscribe({
      next: (dato) => {
        // Inicializa cada tipo con arreglo vacío
        this.tipos.forEach(tipo => this.vehiculosPorTipo[tipo] = []);

        // Agrupa los vehículos según su tipoVehiculo
        dato.forEach(v => {
          if (!this.vehiculosPorTipo[v.tipoVehiculo]) {
            this.vehiculosPorTipo[v.tipoVehiculo] = [];
          }
          this.vehiculosPorTipo[v.tipoVehiculo].push(v);
        });

        this.cargando = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error al cargar vehículos:', err);
        this.error = true;
        this.cargando = false;
        this.cdr.markForCheck();
      }
    });
  }

  imagenDe(v: Vehiculo): string {
    return `Vehiculos/${v.placa}.jpg`;
  }
}

