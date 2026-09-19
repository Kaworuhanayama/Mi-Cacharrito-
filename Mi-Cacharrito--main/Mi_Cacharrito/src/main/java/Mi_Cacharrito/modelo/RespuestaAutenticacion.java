package Mi_Cacharrito.modelo;

public record RespuestaAutenticacion(
        String mensaje,
        Long idUsuario,
        String identificacion,
        String nombreCompleto,
        String rol) {
}
