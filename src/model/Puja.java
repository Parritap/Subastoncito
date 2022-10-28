package model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Puja implements Serializable {
	//VARIABLES GLOBALES
	private LocalDate fechaDePuja;
	private Usuario usuario;
	private Double valorOfrecido;
	Integer idUsuario;


	/**
	 * CONSTRUCTOR
	 * @param fechaDePuja MOMENTO EN QUE SE HIZO LA PUJA
	 * @param usuario USUARIO QUE REALIZO LA PUJA
	 * @param valorOfrecido CANTIDAD DE DINERO OFRECIDA
	 */
	public Puja(LocalDate fechaDePuja, Usuario usuario, Double valorOfrecido) {
		
		this.fechaDePuja = fechaDePuja;
	    this.usuario = usuario;
	    this.valorOfrecido = valorOfrecido;
		this.idUsuario = usuario.getId();
	}

	public Puja(){
		this.fechaDePuja = LocalDate.now();
	}

	public Puja(Integer idUsuario, Double valorOfrecido){
		this.fechaDePuja = LocalDate.now();
		this.idUsuario = idUsuario;
		this.valorOfrecido = valorOfrecido;
	}
}
