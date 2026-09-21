package Mi_Cacharrito.modelo;

public record SolicitudRegistroUsuario(
    String identificacion,
    String nombreCompleto,
    String fechaExpedicionLicencia,
    String categoria,
    String vigencia,
    String correoElectronico,
    String numeroTelefono,
    String password
) {}
