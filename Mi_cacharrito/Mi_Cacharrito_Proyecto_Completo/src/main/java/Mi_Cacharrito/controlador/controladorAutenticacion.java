package Mi_Cacharrito.controlador;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Mi_Cacharrito.modelo.RespuestaAutenticacion;
import Mi_Cacharrito.modelo.SolicitudLogin;
import Mi_Cacharrito.modelo.SolicitudRegistroUsuario;
import Mi_Cacharrito.modelo.Usuario;
import Mi_Cacharrito.repositorio.usuarioRepositorio;

@RestController
@RequestMapping("/api/autenticacion")
@CrossOrigin(origins = "http://localhost:4200")
public class controladorAutenticacion {

    private static final String ROL_USUARIO = "USUARIO";
    private static final String ROL_ADMINISTRADOR = "ADMINISTRADOR";

    private final usuarioRepositorio usuarioRepositorio;

    public controladorAutenticacion(usuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody SolicitudRegistroUsuario solicitud) {
        if (solicitud == null || tieneCamposVacios(
                solicitud.identificacion(), solicitud.nombreCompleto(),
                solicitud.categoria(), solicitud.correoElectronico(),
                solicitud.numeroTelefono(), solicitud.password())
                || solicitud.fechaExpedicionLicencia() == null
                || solicitud.vigencia() == null) {
            return respuestaError(HttpStatus.BAD_REQUEST,
                    "Todos los datos del registro son obligatorios");
        }

        if (usuarioRepositorio.existsByIdentificacion(solicitud.identificacion())) {
            return respuestaError(HttpStatus.CONFLICT,
                    "La identificacion ya esta registrada");
        }

        if (usuarioRepositorio.existsByCorreoElectronico(solicitud.correoElectronico())) {
            return respuestaError(HttpStatus.CONFLICT,
                    "El correo electronico ya esta registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setIdentificacion(solicitud.identificacion());
        usuario.setNombreCompleto(solicitud.nombreCompleto());
        usuario.setFechaExpedicionLicencia(solicitud.fechaExpedicionLicencia());
        usuario.setCategoria(solicitud.categoria());
        usuario.setVigencia(solicitud.vigencia());
        usuario.setCorreoElectronico(solicitud.correoElectronico());
        usuario.setNumeroTelefono(solicitud.numeroTelefono());
        usuario.setPassword(solicitud.password());
        usuario.setRol(ROL_USUARIO);

        Usuario usuarioGuardado = usuarioRepositorio.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(crearRespuesta(usuarioGuardado, "Usuario registrado correctamente"));
    }

    @PostMapping("/login/usuario")
    public ResponseEntity<?> iniciarSesionUsuario(@RequestBody SolicitudLogin solicitud) {
        return autenticar(solicitud, ROL_USUARIO);
    }

    @PostMapping("/login/administrador")
    public ResponseEntity<?> iniciarSesionAdministrador(@RequestBody SolicitudLogin solicitud) {
        return autenticar(solicitud, ROL_ADMINISTRADOR);
    }

    private ResponseEntity<?> autenticar(SolicitudLogin solicitud, String rolEsperado) {
        if (solicitud == null || tieneCamposVacios(
                solicitud.identificacion(), solicitud.password())) {
            return respuestaError(HttpStatus.BAD_REQUEST,
                    "La identificacion y el password son obligatorios");
        }

        Usuario usuario = usuarioRepositorio
                .findByIdentificacion(solicitud.identificacion())
                .orElse(null);

        if (usuario == null
                || !usuario.getPassword().equals(solicitud.password())
                || !rolEsperado.equalsIgnoreCase(usuario.getRol())) {
            return respuestaError(HttpStatus.UNAUTHORIZED,
                    "Credenciales incorrectas o usuario sin permisos para este acceso");
        }

        return ResponseEntity.ok(
                crearRespuesta(usuario, "Inicio de sesion exitoso"));
    }

    private RespuestaAutenticacion crearRespuesta(Usuario usuario, String mensaje) {
        return new RespuestaAutenticacion(
                mensaje,
                usuario.getIdUsuario(),
                usuario.getIdentificacion(),
                usuario.getNombreCompleto(),
                usuario.getRol());
    }

    private boolean tieneCamposVacios(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.isBlank()) {
                return true;
            }
        }
        return false;
    }

    private ResponseEntity<Map<String, String>> respuestaError(
            HttpStatus estado, String mensaje) {
        return ResponseEntity.status(estado).body(Map.of("mensaje", mensaje));
    }
}
