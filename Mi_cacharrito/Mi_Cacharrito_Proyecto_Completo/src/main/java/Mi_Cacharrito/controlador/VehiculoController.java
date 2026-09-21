package Mi_Cacharrito.controlador;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Mi_Cacharrito.modelo.Lista_Vehiculos;
import Mi_Cacharrito.repositorio.lista_vehiculos;

@RestController
@RequestMapping("/vehiculos")
@CrossOrigin(origins = "http://localhost:4200")
public class VehiculoController {

    private final lista_vehiculos vehiculoRepo;

    public VehiculoController(lista_vehiculos vehiculoRepo) {
        this.vehiculoRepo = vehiculoRepo;
    }

    // 1. Listado de vehículos alquilados no entregados
    @GetMapping("/pendientes")
    public List<Lista_Vehiculos> listarPendientes() {
        return vehiculoRepo.findByEstado("alquilado");
    }

    // 2. Búsqueda por placa + cambio de estado a "entregado"
    @PutMapping("/entregar/{placa}")
    public ResponseEntity<Lista_Vehiculos> entregarPorPlaca(@PathVariable String placa) {
        Lista_Vehiculos vehiculo = vehiculoRepo.findByPlaca(placa);

        if (vehiculo == null) {
            return ResponseEntity.notFound().build();
        }

        if (!"alquilado".equalsIgnoreCase(vehiculo.getEstado())) {
            return ResponseEntity.badRequest().build();
        }

        vehiculo.setEstado("entregado");
        return ResponseEntity.ok(vehiculoRepo.save(vehiculo));
    }

    // 3. Búsqueda por número de alquiler + cambio a "disponible"
    //    + cobro extra por días de retraso.
    @PutMapping("/finalizar/{numeroAlquiler}")
    public ResponseEntity<Lista_Vehiculos> finalizarAlquiler(
            @PathVariable Long numeroAlquiler) {

        Lista_Vehiculos vehiculo = vehiculoRepo.findByNumeroAlquiler(numeroAlquiler);

        if (vehiculo == null) {
            return ResponseEntity.notFound().build();
        }

        if (!"alquilado".equalsIgnoreCase(vehiculo.getEstado())) {
            return ResponseEntity.badRequest().build();
        }

        LocalDate hoy = LocalDate.now();

        if (vehiculo.getFechaDevolucion() != null
                && hoy.isAfter(vehiculo.getFechaDevolucion())) {

            long diasExtra = ChronoUnit.DAYS.between(
                    vehiculo.getFechaDevolucion(), hoy);

            double valorBase = vehiculo.getValorAlquiler();
            vehiculo.setValorAlquiler(valorBase + (diasExtra * valorBase));
        }

        vehiculo.setEstado("disponible");
        return ResponseEntity.ok(vehiculoRepo.save(vehiculo));
    }
}
