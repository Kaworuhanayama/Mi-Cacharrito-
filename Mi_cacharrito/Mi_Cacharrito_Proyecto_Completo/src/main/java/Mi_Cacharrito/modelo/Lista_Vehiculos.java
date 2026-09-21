package Mi_Cacharrito.modelo;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "vehiculo")
public class Lista_Vehiculos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Long idVehiculo;

    @Column(name = "placa", length = 10, nullable = false, unique = true)
    private String placa;

    @Column(name = "tipo_vehiculo", length = 30, nullable = false)
    private String tipoVehiculo;

    @Column(name = "color", length = 30, nullable = false)
    private String color;

    @Column(name = "valor_alquiler", nullable = false)
    private Double valorAlquiler;

    @Column(name = "estado", length = 30, nullable = false)
    private String estado;

    @Column(name = "numero_alquiler", unique = true)
    private Long numeroAlquiler;

    @Column(name = "fecha_devolucion")
    private LocalDate fechaDevolucion;

    public Lista_Vehiculos() {}

    public Lista_Vehiculos(Long idVehiculo, String placa, String tipoVehiculo, String color,
                           Double valorAlquiler, String estado, Long numeroAlquiler,
                           LocalDate fechaDevolucion) {
        this.idVehiculo = idVehiculo;
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.color = color;
        this.valorAlquiler = valorAlquiler;
        this.estado = estado;
        this.numeroAlquiler = numeroAlquiler;
        this.fechaDevolucion = fechaDevolucion;
    }

    public Long getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(Long idVehiculo) { this.idVehiculo = idVehiculo; }

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

    public Long getNumeroAlquiler() { return numeroAlquiler; }
    public void setNumeroAlquiler(Long numeroAlquiler) { this.numeroAlquiler = numeroAlquiler; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
}
