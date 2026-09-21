package Mi_Cacharrito.modelo;

import java.time.LocalDate;

public record SolicitudRegistroUsuario(
        String identificacion,
        String nombreCompleto,
        LocalDate fechaExpedicionLicencia,
        String categoria,
        LocalDate vigencia,
        String correoElectronico,
        String numeroTelefono,
        String password) {
}
