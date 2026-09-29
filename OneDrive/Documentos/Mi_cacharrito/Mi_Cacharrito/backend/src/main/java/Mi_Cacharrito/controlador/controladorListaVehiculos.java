package Mi_Cacharrito.controlador;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Mi_Cacharrito.modelo.Lista_Vehiculos;
import Mi_Cacharrito.repositorio.lista_vehiculos;

@RestController
@RequestMapping("/ListaVehiculos/V")
@CrossOrigin(origins = "http://localhost:4200")
public class controladorListaVehiculos {
    private final lista_vehiculos listvehiculo;
    public controladorListaVehiculos(lista_vehiculos listvehiculo){this.listvehiculo=listvehiculo;}

    @GetMapping public List<Lista_Vehiculos> listarTodos(){return listvehiculo.findAll();}
    @GetMapping("/disponibles") public List<Lista_Vehiculos> listarDisponibles(){return listvehiculo.findByEstado("disponible");}
    @GetMapping("/pendientes") public List<Lista_Vehiculos> listarPendientes(){return listvehiculo.findByEstado("alquilado");}
    @GetMapping("/placa/{placa}") public ResponseEntity<Lista_Vehiculos> buscarPorPlaca(@PathVariable String placa){
        Lista_Vehiculos v=listvehiculo.findByPlaca(placa); return v==null?ResponseEntity.notFound().build():ResponseEntity.ok(v);
    }
    @GetMapping("/alquiler/{numeroAlquiler}") public ResponseEntity<Lista_Vehiculos> buscarPorAlquiler(@PathVariable Long numeroAlquiler){
        Lista_Vehiculos v=listvehiculo.findByNumeroAlquiler(numeroAlquiler); return v==null?ResponseEntity.notFound().build():ResponseEntity.ok(v);
    }
    @PutMapping("/entregar/{placa}") public ResponseEntity<Lista_Vehiculos> entregarPorPlaca(@PathVariable String placa){
        Lista_Vehiculos v=listvehiculo.findByPlaca(placa);
        if(v==null)return ResponseEntity.notFound().build();
        if(!"alquilado".equalsIgnoreCase(v.getEstado()))return ResponseEntity.badRequest().build();
        v.setEstado("entregado"); return ResponseEntity.ok(listvehiculo.save(v));
    }
    @PutMapping("/finalizar/{numeroAlquiler}") public ResponseEntity<Lista_Vehiculos> finalizarAlquiler(@PathVariable Long numeroAlquiler){
        Lista_Vehiculos v=listvehiculo.findByNumeroAlquiler(numeroAlquiler);
        if(v==null)return ResponseEntity.notFound().build();
        if(!"alquilado".equalsIgnoreCase(v.getEstado()))return ResponseEntity.badRequest().build();
        LocalDate hoy=LocalDate.now();
        if(v.getFechaDevolucion()!=null && hoy.isAfter(v.getFechaDevolucion())){
            long diasExtra=ChronoUnit.DAYS.between(v.getFechaDevolucion(),hoy);
            double base=v.getValorAlquiler(); v.setValorAlquiler(base+(diasExtra*base));
        }
        v.setEstado("disponible"); return ResponseEntity.ok(listvehiculo.save(v));
    }
}
