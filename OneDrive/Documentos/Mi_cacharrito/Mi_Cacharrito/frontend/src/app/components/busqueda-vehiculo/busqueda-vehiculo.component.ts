import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { VehiculoService, Vehiculo } from '../../services/vehiculo.service';
@Component({selector:'app-busqueda-vehiculo',standalone:true,imports:[CommonModule,FormsModule],templateUrl:'./busqueda-vehiculo.component.html'})
export class BusquedaVehiculoComponent {
 private service=inject(VehiculoService); modo:'placa'|'alquiler'='placa'; valor=''; vehiculo:Vehiculo|null=null; mensaje='';
 buscar(){this.mensaje='';this.vehiculo=null;if(!this.valor.trim())return this.mensaje='Ingrese un valor.'; const req=this.modo==='placa'?this.service.buscarPlaca(this.valor.trim()):this.service.buscarAlquiler(Number(this.valor)); req.subscribe({next:v=>this.vehiculo=v,error:()=>this.mensaje='Vehículo no encontrado.'})}
 entregar(){if(!this.vehiculo)return;this.service.entregar(this.vehiculo.placa).subscribe({next:v=>{this.vehiculo=v;this.mensaje='Estado cambiado a entregado.'},error:()=>this.mensaje='No se pudo actualizar el estado.'})}
 finalizar(){if(!this.vehiculo?.numeroAlquiler)return;this.service.finalizar(this.vehiculo.numeroAlquiler).subscribe({next:v=>{this.vehiculo=v;this.mensaje='Alquiler finalizado y vehículo disponible.'},error:()=>this.mensaje='No se pudo finalizar el alquiler.'})}
}
