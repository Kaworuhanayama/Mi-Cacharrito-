package Mi_Cacharrito.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "alquiler")
public class Alquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_alquiler")
    private Long numeroAlquiler;

    @Column(name = "identificacion_usuario", nullable = false)
    private String identificacionUsuario;

    @Column(name = "nombre_usuario", nullable = false)
    private String nombreUsuario;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_entrega", nullable = false)
    private LocalDate fechaEntrega;

    @Column(name = "placa", length = 10, nullable = false)
    private String placa;

    @Column(name = "tipo_vehiculo", length = 30, nullable = false)
    private String tipoVehiculo;

    @Column(name = "color", length = 30, nullable = false)
    private String color;

    @Column(name = "valor_alquiler", nullable = false)
    private Double valorAlquiler;

    @Column(name = "estado", length = 30, nullable = false)
    private String estado;

    public Alquiler() {}

    // Getters y Setters
    public Long getNumeroAlquiler() { return numeroAlquiler; }
    public void setNumeroAlquiler(Long numeroAlquiler) { this.numeroAlquiler = numeroAlquiler; }

    public String getIdentificacionUsuario() { return identificacionUsuario; }
    public void setIdentificacionUsuario(String identificacionUsuario) { this.identificacionUsuario = identificacionUsuario; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(String tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Double getValorAlquiler() { return valorAlquiler; }
    public void setValorAlquiler(Double valorAlquiler) { this.valorAlquiler = valorAlquiler; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
