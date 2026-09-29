package Mi_Cacharrito.controlador;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Mi_Cacharrito.modelo.*;
import Mi_Cacharrito.repositorio.usuarioRepositorio;

@RestController
@RequestMapping("/api/autenticacion")
@CrossOrigin(origins = "http://localhost:4200")
public class controladorAutenticacion {
    private static final String ROL_USUARIO="USUARIO";
    private static final String ROL_ADMINISTRADOR="ADMINISTRADOR";
    private final usuarioRepositorio usuarioRepositorio;
    public controladorAutenticacion(usuarioRepositorio usuarioRepositorio){this.usuarioRepositorio=usuarioRepositorio;}
    @PostMapping("/registro") public ResponseEntity<?> registrarUsuario(@RequestBody SolicitudRegistroUsuario s){
        if(s==null||vacios(s.identificacion(),s.nombreCompleto(),s.categoria(),s.correoElectronico(),s.numeroTelefono(),s.password())||s.fechaExpedicionLicencia()==null||s.vigencia()==null)return error(HttpStatus.BAD_REQUEST,"Todos los datos del registro son obligatorios");
        if(usuarioRepositorio.existsByIdentificacion(s.identificacion()))return error(HttpStatus.CONFLICT,"La identificacion ya esta registrada");
        if(usuarioRepositorio.existsByCorreoElectronico(s.correoElectronico()))return error(HttpStatus.CONFLICT,"El correo electronico ya esta registrado");
        Usuario u=new Usuario(); u.setIdentificacion(s.identificacion());u.setNombreCompleto(s.nombreCompleto());u.setFechaExpedicionLicencia(s.fechaExpedicionLicencia());u.setCategoria(s.categoria());u.setVigencia(s.vigencia());u.setCorreoElectronico(s.correoElectronico());u.setNumeroTelefono(s.numeroTelefono());u.setPassword(s.password());u.setRol(ROL_USUARIO);
        Usuario guardado=usuarioRepositorio.save(u); return ResponseEntity.status(HttpStatus.CREATED).body(respuesta(guardado,"Usuario registrado correctamente"));
    }
    @PostMapping("/login/usuario") public ResponseEntity<?> loginUsuario(@RequestBody SolicitudLogin s){return autenticar(s,ROL_USUARIO);}
    @PostMapping("/login/administrador") public ResponseEntity<?> loginAdmin(@RequestBody SolicitudLogin s){return autenticar(s,ROL_ADMINISTRADOR);}
    private ResponseEntity<?> autenticar(SolicitudLogin s,String rol){
        if(s==null||vacios(s.identificacion(),s.password()))return error(HttpStatus.BAD_REQUEST,"La identificacion y el password son obligatorios");
        Usuario u=usuarioRepositorio.findByIdentificacion(s.identificacion()).orElse(null);
        if(u==null||!u.getPassword().equals(s.password())||!rol.equalsIgnoreCase(u.getRol()))return error(HttpStatus.UNAUTHORIZED,"Credenciales incorrectas o usuario sin permisos para este acceso");
        return ResponseEntity.ok(respuesta(u,"Inicio de sesion exitoso"));
    }
    private RespuestaAutenticacion respuesta(Usuario u,String m){return new RespuestaAutenticacion(m,u.getIdUsuario(),u.getIdentificacion(),u.getNombreCompleto(),u.getRol());}
    private boolean vacios(String... c){for(String x:c)if(x==null||x.isBlank())return true;return false;}
    private ResponseEntity<Map<String,String>> error(HttpStatus s,String m){return ResponseEntity.status(s).body(Map.of("mensaje",m));}
}
