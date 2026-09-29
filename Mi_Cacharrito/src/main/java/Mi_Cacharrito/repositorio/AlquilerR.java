package Mi_Cacharrito.repositorio;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import Mi_Cacharrito.modelo.Alquiler;

public interface AlquilerR extends JpaRepository<Alquiler, Long> {

    List<Alquiler> findByEstado(String estado);
    
    Optional<Alquiler> findByPlacaAndEstado(String placa, String estado);
}