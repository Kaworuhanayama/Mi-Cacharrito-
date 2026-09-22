package Mi_Cacharrito.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Mi_Cacharrito.modelo.Lista_Vehiculos;
import Mi_Cacharrito.repositorio.lista_vehiculos;

@RestController
@CrossOrigin(origins = "http://localhost:4200") 
@RequestMapping("/ListaVehiculos/V")
public class controladorListaVehiculos {
	
	@Autowired
	private lista_vehiculos listvehiculo;
	
	 @GetMapping
	 public List<Lista_Vehiculos> listarTodos() {
	 return listvehiculo.findAll();
	 }
	 
	 @GetMapping("/disponibles")
	 public List<Lista_Vehiculos> listarDisponibles() {
	 return listvehiculo.findByEstado("disponible");
	 }
	 @GetMapping("/tipo/{tipo}")
	 public List<Lista_Vehiculos> listarPorTipo(@PathVariable String tipo) {
	     return listvehiculo.findByTipoVehiculo(tipo);
	 }

	 @GetMapping("/tipo/{tipo}/disponibles")
	 public List<Lista_Vehiculos> listarPorTipoDisponibles(@PathVariable String tipo) {
	     return listvehiculo.findByTipoVehiculoAndEstado(tipo, "disponible");
	 }
	
	 


}
