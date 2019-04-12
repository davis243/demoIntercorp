package pe.davis.demoIntercorp.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
@Entity(name = "ClienteEntity")
@Table(name = "cliente")
public class ClienteEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private Long idCliente;

	@Column(name = "nombre", nullable = false)
	@Length(min = 1, max = 50)
	@ApiModelProperty(notes = "Nombre del cliente", example = "David")
	private String nombre;
	
	@Column(name = "apellido", nullable = false)
	@Length(min = 1, max = 100)
	@ApiModelProperty(notes = "Apellido del cliente", example = "Sarmiento")
	private String apellido;
	
	@Column(name = "edad", nullable = false)
	@Min(0)
    @Max(100)
	@ApiModelProperty(notes = "Edad del cliente", example = "30")
	private Integer edad;
	
	@Column(name = "fechaNacimiento")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@ApiModelProperty(notes = "Fecha de nacimiento del cliente", example = "2019-04-11")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaNacimiento ;

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	
	
	
}


