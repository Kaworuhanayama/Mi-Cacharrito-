import { Component } from '@angular/core';
import { VehiculosComponent } from './components/vehiculos/vehiculos.component';
import { BusquedaVehiculoComponent } from './components/busqueda-vehiculo/busqueda-vehiculo.component';
@Component({selector:'app-root',standalone:true,imports:[VehiculosComponent,BusquedaVehiculoComponent],template:`<div class="app-shell"><nav class="navbar navbar-dark bg-dark"><div class="container"><span class="navbar-brand">Mi Cacharrito</span></div></nav><app-vehiculos></app-vehiculos><app-busqueda-vehiculo></app-busqueda-vehiculo></div>`})
export class AppComponent {}
