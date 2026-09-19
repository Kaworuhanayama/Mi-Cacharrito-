package Mi_Cacharrito.modelo;

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

    @Column(name = "estado", length = 20, nullable = false)
    private String estado;

	public Lista_Vehiculos(Long idVehiculo, String placa, String tipoVehiculo, String color, Double valorAlquiler,
			String estado) {
		super();
		this.idVehiculo = idVehiculo;
		this.placa = placa;
		this.tipoVehiculo = tipoVehiculo;
		this.color = color;
		this.valorAlquiler = valorAlquiler;
		this.estado = estado;
	}
	
	public Lista_Vehiculos() {}

	public Long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Double getValorAlquiler() {
		return valorAlquiler;
	}

	public void setValorAlquiler(Double valorAlquiler) {
		this.valorAlquiler = valorAlquiler;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	} // ej: "disponible", "alquilado", "pendiente de entrega"
}
    

    
    