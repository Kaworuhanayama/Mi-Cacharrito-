package Mi_Cacharrito.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Mi_Cacharrito.modelo.Lista_Vehiculos;

public interface lista_vehiculos extends JpaRepository<Lista_Vehiculos, Long> {

    List<Lista_Vehiculos> findByTipoVehiculo(String tipoVehiculo);

    List<Lista_Vehiculos> findByEstado(String estado);

    List<Lista_Vehiculos> findByTipoVehiculoAndEstado(String tipoVehiculo, String estado);

    Lista_Vehiculos findByPlaca(String placa);
}