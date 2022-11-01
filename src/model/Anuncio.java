package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class Anuncio implements Serializable {

	//El anuncio contiene un producto
	private Producto producto;
	private String titulo;
	private String nombreAnunciante;
	private byte[] imageSrc; //Es necesario cambiar la imagen a String, y contener solo la ruta para tener flexibilidad
	private LocalDateTime fechaPublicacion;
	private LocalDateTime fechaTerminacion;
	private Double valorInicial;
	private ArrayList<Puja> listaPujas;
	private Integer idListaPujas;
	private  Usuario usuario; //El usuario que realiza el anuncio
	/**
	 * creo la variable Estado para indicar cuando un Anuncio ha sido eliminado, actualizado
	 */
	private Estado estado;

	private Boolean fueMostrado;
	/*
	cada anuncio esta relacionado a un id de tipo entero
	ya que solo vamos a manejar una lista de anuncios podemos hacerlo static
	NUEVA MANERA DE GENERAR IDS, USANDO EL SINGLE RESPONSIBILITY PRINCIPLE
	*/
	private static int idAux;
	private Integer id;

	/**
	 * CONSTRUCTOR NECESARIO PARA PRUEBAS
	 */

	public Anuncio(){}

	public Anuncio(String tituloAnuncio, byte[] bytesImg, Double valorInicialAnuncio) {
		this.titulo = tituloAnuncio;
		this.imageSrc = bytesImg;
		this.valorInicial = valorInicialAnuncio;
		this.fechaPublicacion = LocalDateTime.now();
		this.fechaTerminacion = this.fechaPublicacion.plusMinutes(5L);
		this.idListaPujas = ModelFactoryController.darIdListaPuja();
		this.listaPujas = new ArrayList<>();
		this.fueMostrado = false;
		this.estado = Estado.NUEVO;
		this.id = ++idAux;
	}


	/**
	 * METODO QUE PERMITE COMPARAR IDS DADO UNO POR PARAMETRO
	 * @param id CON EL QUE SE DESEA COMPARAR
	 * @return TRUE || FALSE
	 */
	public boolean compararId(Integer id) {
		return this.id.compareTo(id) == 0;
	}

	/**
	 * metodo que devuelve el nombre del producto asociado con este anuncio
	 * @return el nombre del producto
	 */
	public String getNameProducto() {
		return producto.getNombre();
	}

	/**
	 * Este metodo devuelve la cantidad de pujas que se ha hecho en ese anuncio
	 * @return el total de pujas en el anuncio
	 */
	public String getTotalPujas() {
		return listaPujas.size()+"";
	}

	/**
	 * Este metodo devuelve el valor de la puja más alta
	 * @return el string del valor de la puja mas alta
	 */
	public String getValorMasAlto() {
		//El valor de la puja más alta será 0
		Double masAlto = 0.0;
		for (Puja aux : listaPujas) {
			//si el valor de la puja es mayor entonces cambio el valor de la puja más alta
			if (aux.getValorOfrecido().compareTo(masAlto) == 0) {
				masAlto = aux.getValorOfrecido();
			}
		}
		return masAlto+"";
	}

	@Override
	public String toString() {
		return "producto=" + producto.getNombre() +
				", titulo='" + titulo + '\'' +
				", nombreAnunciante='" + nombreAnunciante + '\'' +
				", fechaPublicacion=" + fechaPublicacion +
				", fechaTerminacion=" + fechaTerminacion.toString() +
				", valorInicial=" + valorInicial +
				", idListaPujas=" + idListaPujas +
				", usuario=" + usuario.getName() +
				", estado=" + estado +
				", fueMostrado=" + fueMostrado +
				", id=" + id;
	}
}
