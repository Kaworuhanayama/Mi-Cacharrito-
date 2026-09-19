import { Routes } from '@angular/router';
import { ListaVehiculo } from './lista-vehiculo/lista-vehiculo';
import { FormularioAlquiler } from './formulario-alquiler/formulario-alquiler';
import { Login } from './auth/login/login';
import { LoginUsuario } from './auth/login/usuario/login-usuario';
import { LoginAdministrador } from './auth/login/administrador/login-administrador';

export const routes: Routes = [
    {path: '', redirectTo: '/home', pathMatch: 'full'},
    {path: 'home', component: ListaVehiculo},
    {path: 'alquiler', component: FormularioAlquiler},
    {path: 'login', component: Login},
    {path: 'login/usuario', component: LoginUsuario},
    {path: 'login/administrador', component: LoginAdministrador},
];
