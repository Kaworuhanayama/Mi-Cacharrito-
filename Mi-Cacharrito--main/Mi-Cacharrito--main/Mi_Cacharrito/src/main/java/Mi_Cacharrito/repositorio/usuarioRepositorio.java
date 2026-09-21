package Mi_Cacharrito.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Mi_Cacharrito.modelo.Usuario;

public interface usuarioRepositorio extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByIdentificacion(String identificacion);

    boolean existsByIdentificacion(String identificacion);

    boolean existsByCorreoElectronico(String correoElectronico);
}
