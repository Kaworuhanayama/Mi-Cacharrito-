import { Routes } from '@angular/router';
import { ListaVehiculo } from './lista-vehiculo/lista-vehiculo';
import { FormularioAlquiler } from './formulario-alquiler/formulario-alquiler';
import { CancelarAlquiler } from './cancelar-alquiler/cancelar-alquiler';
import { Login } from './login/login';
import { LoginUsuario } from './login/usuario/login-usuario';
import { LoginAdministrador } from './login/administrador/login-administrador';
import { RegistroUsuario } from './registro/registro-usuario';
import { sesionGuard } from './guards/sesion-guard';
import { DevolucionAlquiler } from './devolucion-alquiler/devolucion-alquiler';

export const routes: Routes = [
    {path: '', redirectTo: '/login', pathMatch: 'full'},
    {path: 'login', component: Login},
    {path: 'login/usuario', component: LoginUsuario},
    {path: 'login/administrador', component: LoginAdministrador},
    {path: 'registro', component: RegistroUsuario},
    {path: 'home', component: ListaVehiculo},
    {path: 'alquiler', component: FormularioAlquiler},
    {path: 'cancelar-alquiler', component: CancelarAlquiler},
    {path: 'home', component: ListaVehiculo, canActivate: [sesionGuard]},
    { path: 'devolucion', component: DevolucionAlquiler, canActivate: [sesionGuard]}
];
