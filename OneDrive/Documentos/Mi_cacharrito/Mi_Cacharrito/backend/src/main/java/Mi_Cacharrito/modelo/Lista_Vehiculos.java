package Mi_Cacharrito.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vehiculo")
public class Lista_Vehiculos {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo") private Long idVehiculo;
    @Column(name = "placa", length = 10, nullable = false, unique = true) private String placa;
    @Column(name = "tipo_vehiculo", length = 30, nullable = false) private String tipoVehiculo;
    @Column(name = "color", length = 30, nullable = false) private String color;
    @Column(name = "valor_alquiler", nullable = false) private Double valorAlquiler;
    @Column(name = "estado", length = 30, nullable = false) private String estado;
    @Column(name = "numero_alquiler", unique = true) private Long numeroAlquiler;
    @Column(name = "fecha_devolucion") private LocalDate fechaDevolucion;

    public Lista_Vehiculos() {}
    public Long getIdVehiculo(){return idVehiculo;} public void setIdVehiculo(Long v){idVehiculo=v;}
    public String getPlaca(){return placa;} public void setPlaca(String v){placa=v;}
    public String getTipoVehiculo(){return tipoVehiculo;} public void setTipoVehiculo(String v){tipoVehiculo=v;}
    public String getColor(){return color;} public void setColor(String v){color=v;}
    public Double getValorAlquiler(){return valorAlquiler;} public void setValorAlquiler(Double v){valorAlquiler=v;}
    public String getEstado(){return estado;} public void setEstado(String v){estado=v;}
    public Long getNumeroAlquiler(){return numeroAlquiler;} public void setNumeroAlquiler(Long v){numeroAlquiler=v;}
    public LocalDate getFechaDevolucion(){return fechaDevolucion;} public void setFechaDevolucion(LocalDate v){fechaDevolucion=v;}
}
