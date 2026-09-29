package Mi_Cacharrito.repositorio;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import Mi_Cacharrito.modelo.Alquiler;

public interface AlquilerR extends JpaRepository<Alquiler, Long> {

    // Métodos adicionales para no modificar la lógica previa del PDF
    List<Alquiler> findByEstado(String estado);
    
    Optional<Alquiler> findByPlacaAndEstado(String placa, String estado);
}