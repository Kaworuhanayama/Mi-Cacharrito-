package Mi_Cacharrito.controlador;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Mi_Cacharrito.modelo.Lista_Vehiculos;
import Mi_Cacharrito.repositorio.lista_vehiculos;

@RestController
@RequestMapping("/ListaVehiculos/V")
@CrossOrigin(origins = "http://localhost:4200")
public class controladorListaVehiculos {

    private final lista_vehiculos listvehiculo;

    public controladorListaVehiculos(lista_vehiculos listvehiculo) {
        this.listvehiculo = listvehiculo;
    }

    // Frontend: vista general de vehículos.
    @GetMapping
    public List<Lista_Vehiculos> listarTodos() {
        return listvehiculo.findAll();
    }

    // Frontend: vehículos disponibles.
    @GetMapping("/disponibles")
    public List<Lista_Vehiculos> listarDisponibles() {
        return listvehiculo.findByEstado("disponible");
    }

    // Backend tarea 1: vehículos alquilados que aún no han sido entregados.
    @GetMapping("/pendientes")
    public List<Lista_Vehiculos> listarPendientes() {
        return listvehiculo.findByEstado("alquilado");
    }

    // Backend tarea 2: búsqueda por placa y cambio a "entregado".
    @PutMapping("/entregar/{placa}")
    public ResponseEntity<?> entregarPorPlaca(@PathVariable String placa) {
        return listvehiculo.findByPlaca(placa)
            .map(vehiculo -> {
                if (!"alquilado".equalsIgnoreCase(vehiculo.getEstado())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new Mensaje("El vehículo no se encuentra en estado alquilado"));
                }

                vehiculo.setEstado("entregado");
                return ResponseEntity.ok(listvehiculo.save(vehiculo));
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Mensaje("No se encontró un vehículo con la placa indicada")));
    }

    // Backend tarea 3: búsqueda por número de alquiler, cobro por días extra
    // y cambio final a "disponible".
    @PutMapping("/finalizar/{numeroAlquiler}")
    public ResponseEntity<?> finalizarAlquiler(@PathVariable Long numeroAlquiler) {
        return listvehiculo.findByNumeroAlquiler(numeroAlquiler)
            .map(vehiculo -> {
                if (!"alquilado".equalsIgnoreCase(vehiculo.getEstado())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new Mensaje("El alquiler no se encuentra en estado alquilado"));
                }

                LocalDate hoy = LocalDate.now();
                double valorBase = vehiculo.getValorAlquiler() == null ? 0.0 : vehiculo.getValorAlquiler();

                if (vehiculo.getFechaDevolucion() != null
                        && hoy.isAfter(vehiculo.getFechaDevolucion())) {
                    long diasExtra = ChronoUnit.DAYS.between(
                        vehiculo.getFechaDevolucion(), hoy
                    );
                    double cobroExtra = diasExtra * valorBase;
                    vehiculo.setValorAlquiler(valorBase + cobroExtra);
                }

                vehiculo.setEstado("disponible");
                return ResponseEntity.ok(listvehiculo.save(vehiculo));
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Mensaje("No se encontró el número de alquiler indicado")));
    }

    // Búsqueda usada por la vista de frontend.
    @GetMapping("/placa/{placa}")
    public ResponseEntity<?> buscarPorPlaca(@PathVariable String placa) {
        return listvehiculo.findByPlaca(placa)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Mensaje("No se encontró un vehículo con la placa indicada")));
    }

    // Búsqueda usada por la vista de frontend.
    @GetMapping("/alquiler/{numeroAlquiler}")
    public ResponseEntity<?> buscarPorNumeroAlquiler(@PathVariable Long numeroAlquiler) {
        return listvehiculo.findByNumeroAlquiler(numeroAlquiler)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Mensaje("No se encontró el número de alquiler indicado")));
    }

    public record Mensaje(String mensaje) {}
}
