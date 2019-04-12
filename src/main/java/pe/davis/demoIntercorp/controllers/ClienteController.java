package pe.davis.demoIntercorp.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import pe.davis.demoIntercorp.domain.model.ClienteEntity;
import pe.davis.demoIntercorp.service.ClienteService;




@Api(value = "Demo Api Intercorp", description = "Api para manejo de clientes")
@RestController
public class ClienteController {

	@Autowired
	ClienteService clienteService;

	@ApiOperation(value = "Crea clientes", response = ResponseEntity.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Se creo cliente satisfactoriamente"),
			  @ApiResponse(code = 401, message = "No estas autorizado para ver este recurso"),
			  @ApiResponse(code = 403, message = "Acceso prohibido a este recurso"),
			  @ApiResponse(code = 404, message = "Un recurso para poder crear el cliente no fue encontrado"),
			  @ApiResponse(code = 500, message = "Error inesperado. Reintente en unos momentos")
	})
	@PostMapping("/createcliente")
	public ResponseEntity<ApiResult> createCliente(
			 @ApiParam(value = "Objeto Cliente", required = true) 
			 @Valid @RequestBody ClienteEntity cliente)  {
		try {
			clienteService.save(cliente);
		}
		catch(Exception e) {
			return Result.failure("Error creando cliente");
			
		}
		return  Result.ok("Se creo cliente satisfactoriamente");
	}
	
	
	
	class ClienteResultado extends ClienteEntity{
		
		LocalDate fechaProbableMuerte;

		public LocalDate getFechaProbableMuerte() {
			return fechaProbableMuerte;
		}

		public void setFechaProbableMuerte(LocalDate fechaProbableMuerte) {
			this.fechaProbableMuerte = fechaProbableMuerte;
		}

		
		
	}
	
	@ApiOperation(value = "Lista clientes + fecha Probable de muerte", response = ResponseEntity.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Se creo cliente satisfactoriamente"),
			  @ApiResponse(code = 401, message = "No estas autorizado para ver este recurso"),
			  @ApiResponse(code = 403, message = "Acceso prohibido a este recurso"),
			  @ApiResponse(code = 404, message = "Un recurso para poder crear el cliente no fue encontrado"),
			  @ApiResponse(code = 500, message = "Error inesperado. Reintente en unos momentos")
	})
	@GetMapping("/listacliente")
	public ResponseEntity<ApiResult> listaCliente(
			
			 )  {
		try {
			List<ClienteEntity> clientes = clienteService.getListaClientes();
			List<ClienteResultado> clientesResult = new ArrayList<>();
			for(ClienteEntity cliente :clientes )
			{
				ClienteResultado clienteResult = new ClienteResultado();
				clienteResult.setApellido(cliente.getApellido());
				clienteResult.setNombre(cliente.getNombre());
				clienteResult.setFechaNacimiento(cliente.getFechaNacimiento());
				clienteResult.setEdad(cliente.getEdad());
				clienteResult.setIdCliente(cliente.getIdCliente());
				clienteResult.setFechaProbableMuerte(LocalDate.now());
				clientesResult.add((ClienteResultado) clienteResult);
			}
			ApiResult apiResult = ApiResult.blank()
			        .add("clientes", clientesResult);
			return  Result.ok(apiResult);
		}
		catch(Exception e) {
			return Result.failure("Error obteniendo lista cliente");
			
		}
		
	}
	
	class KpiCliente{
		@ApiModelProperty(notes = "Promedio de edades de clientes", example = "30")
		double promedio;
		@ApiModelProperty(notes = "Desviacion estandárd de edades de clientes", example = "11.180339887498949")
		double desviacion;
		public double getPromedio() {
			return promedio;
		}
		public void setPromedio(double promedio) {
			this.promedio = promedio;
		}
		public double getDesviacion() {
			return desviacion;
		}
		public void setDesviacion(double desviacion) {
			this.desviacion = desviacion;
		}
		
		
	}
	@ApiOperation(value = "Muestra el Promedio y desviacion estandar de la edad de los clientes", response = ResponseEntity.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Se obtuvo el promedy deviacion satifactoriamente"),
			  @ApiResponse(code = 401, message = "No estas autorizado para ver este recurso"),
			  @ApiResponse(code = 403, message = "Acceso prohibido a este recurso"),
			  @ApiResponse(code = 404, message = "No existen clientes"),
			  @ApiResponse(code = 500, message = "Error inesperado. Reintente en unos momentos")
	})
	@GetMapping("/kpideclientes")
	public ResponseEntity<ApiResult> kpiDeClientes(
			
			 )  {
		try {
			List<ClienteEntity> clientes = clienteService.getListaClientes();
		
			int [] edades = new int[clientes.size()];
			int indice = 0 ;
			for(ClienteEntity cliente :clientes )
			{
				edades[indice] = cliente.getEdad();
			}
			KpiCliente kpi = new KpiCliente();
			kpi.setPromedio(promedio(edades));
			kpi.setDesviacion(desviacion(edades));
			
			ApiResult apiResult = ApiResult.blank()
			        .add("kpi", kpi);
			return  Result.ok(apiResult);
		}
		catch(Exception e) {
			return Result.failure("Error obteniendo lista cliente");
			
		}
		
	}
	
	public static double promedio ( int [ ] listValores ) {
	    double promedio = 0.0;
	    for ( int i = 0; i < listValores.length; i++ )
	      promedio += listValores[i];
	
	    return promedio / ( double ) listValores.length;  
	}
	
	public static double desviacion ( int [ ] v ) {
	    double prom, sum = 0; int i, n = v.length;
	    prom = promedio ( v );

	    for ( i = 0; i < n; i++ ) 
	      sum += Math.pow ( v [ i ] - prom, 2 );

	    return Math.sqrt ( sum / ( double ) n );
	  }
}
