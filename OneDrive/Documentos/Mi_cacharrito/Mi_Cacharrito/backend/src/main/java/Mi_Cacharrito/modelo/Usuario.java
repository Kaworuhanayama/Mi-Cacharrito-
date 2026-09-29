package Mi_Cacharrito.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name="id_usuario") private Long idUsuario;
    @Column(name="identificacion", nullable=false, unique=true, length=30) private String identificacion;
    @Column(name="nombre_completo", nullable=false, length=100) private String nombreCompleto;
    @Column(name="fecha_expedicion_licencia", nullable=false) private LocalDate fechaExpedicionLicencia;
    @Column(name="categoria", nullable=false, length=10) private String categoria;
    @Column(name="vigencia", nullable=false) private LocalDate vigencia;
    @Column(name="correo_electronico", nullable=false, unique=true, length=120) private String correoElectronico;
    @Column(name="numero_telefono", nullable=false, length=30) private String numeroTelefono;
    @Column(name="password", nullable=false, length=255) private String password;
    @Column(name="rol", nullable=false, length=30) private String rol;
    public Usuario() {}
    public Long getIdUsuario(){return idUsuario;} public void setIdUsuario(Long v){idUsuario=v;}
    public String getIdentificacion(){return identificacion;} public void setIdentificacion(String v){identificacion=v;}
    public String getNombreCompleto(){return nombreCompleto;} public void setNombreCompleto(String v){nombreCompleto=v;}
    public LocalDate getFechaExpedicionLicencia(){return fechaExpedicionLicencia;} public void setFechaExpedicionLicencia(LocalDate v){fechaExpedicionLicencia=v;}
    public String getCategoria(){return categoria;} public void setCategoria(String v){categoria=v;}
    public LocalDate getVigencia(){return vigencia;} public void setVigencia(LocalDate v){vigencia=v;}
    public String getCorreoElectronico(){return correoElectronico;} public void setCorreoElectronico(String v){correoElectronico=v;}
    public String getNumeroTelefono(){return numeroTelefono;} public void setNumeroTelefono(String v){numeroTelefono=v;}
    public String getPassword(){return password;} public void setPassword(String v){password=v;}
    public String getRol(){return rol;} public void setRol(String v){rol=v;}
}
