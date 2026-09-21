package Mi_Cacharrito.repositorio;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import Mi_Cacharrito.modelo.Usuario;

public interface usuarioRepositorio extends JpaRepository<Usuario, Long> {

    boolean existsByIdentificacion(String identificacion);

    boolean existsByCorreoElectronico(String correoElectronico);

    Optional<Usuario> findByIdentificacion(String identificacion);
}
