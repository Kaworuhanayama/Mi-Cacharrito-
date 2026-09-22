import { Routes } from '@angular/router';
import { ListaVehiculo } from './lista-vehiculo/lista-vehiculo';
import { FormularioAlquiler } from './formulario-alquiler/formulario-alquiler';
import { CancelarAlquiler } from './cancelar-alquiler/cancelar-alquiler';

export const routes: Routes = [
    {path: '', redirectTo: '/home', pathMatch: 'full'},
    {path: 'home', component: ListaVehiculo},
    {path: 'alquiler', component: FormularioAlquiler},
    {path: 'cancelar-alquiler', component: CancelarAlquiler},
];
