import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VehiculoService, Vehiculo } from '../../services/vehiculo.service';
@Component({selector:'app-vehiculos',standalone:true,imports:[CommonModule],templateUrl:'./vehiculos.component.html'})
export class VehiculosComponent {
 private service=inject(VehiculoService); vehiculos:Vehiculo[]=[]; mensaje='';
 ngOnInit(){this.cargar()}
 cargar(){this.service.listarPendientes().subscribe({next:v=>this.vehiculos=v,error:()=>this.mensaje='No fue posible consultar el backend.'})}
 entregar(v:Vehiculo){this.service.entregar(v.placa).subscribe({next:()=>{this.mensaje='Vehículo marcado como entregado.';this.cargar()},error:()=>this.mensaje='No se pudo cambiar el estado.'})}
}
