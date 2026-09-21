package Mi_Cacharrito.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "identificacion", length = 30, nullable = false, unique = true)
    private String identificacion;

    @Column(name = "nombre_completo", length = 100, nullable = false)
    private String nombreCompleto;

    @Column(name = "fecha_expedicion_licencia", length = 30, nullable = false)
    private String fechaExpedicionLicencia;

    @Column(name = "categoria", length = 20, nullable = false)
    private String categoria;

    @Column(name = "vigencia", length = 30, nullable = false)
    private String vigencia;

    @Column(name = "correo_electronico", length = 120, nullable = false, unique = true)
    private String correoElectronico;

    @Column(name = "numero_telefono", length = 30, nullable = false)
    private String numeroTelefono;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "rol", length = 30, nullable = false)
    private String rol;

    public Usuario() {}

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getFechaExpedicionLicencia() { return fechaExpedicionLicencia; }
    public void setFechaExpedicionLicencia(String fechaExpedicionLicencia) { this.fechaExpedicionLicencia = fechaExpedicionLicencia; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getVigencia() { return vigencia; }
    public void setVigencia(String vigencia) { this.vigencia = vigencia; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getNumeroTelefono() { return numeroTelefono; }
    public void setNumeroTelefono(String numeroTelefono) { this.numeroTelefono = numeroTelefono; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
