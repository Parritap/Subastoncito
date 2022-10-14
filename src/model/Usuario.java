package model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@Setter
@Getter
public class Usuario implements Serializable {
	//VARIABLES GLOBALES
	private String name;
	private Integer age;
	private String cedula;
	private String correo;
	private Integer cantAnuncios;
	private String direccion;
	private String password;

	private ArrayList<Puja> listaPujas;

	private Integer idListaPujas;
	//Este atributo permite conocer si el cliente actual esta activo en la app
	private Boolean activo;
	private static Integer idAux = 0;
	//Este atributo permite saber el estado del usuario, Eliminado, Nuevo, Actualizado
	private Estado estado;
	private Integer id;

	//Constructores
	public Usuario(String name, Integer age, String cedula, String correo, Integer cantAnuncios, Puja puja) {
		this.name = name;
	    this.age = age;
	    this.cedula = cedula;
	    this.correo = correo;
	    this.cantAnuncios = cantAnuncios;
		id = ++idAux;
		estado = Estado.NUEVO;
		this.activo = true;
		this.idListaPujas = ModelFactoryController.darIdListaPuja();
		this.cantAnuncios = 0;
		this.listaPujas = new ArrayList<>();
	}

	public Usuario(String name, Integer age, String cedula, String correo, String password, String direccion ) {
		this.name = name;
		this.age = age;
		this.cedula = cedula;
		this.correo = correo;
		this.direccion = direccion;
		this.password = password;
		this.id = ++idAux;
		this.estado = Estado.NUEVO;
		this.listaPujas = new ArrayList<>();
		this.activo=true;
		this.idListaPujas = ModelFactoryController.darIdListaPuja();
		this.cantAnuncios = 0;
	}

	public Usuario(ArrayList<Puja> listaPujas){
		this.id = ++idAux;
		this.activo = true;
		this.idListaPujas = ModelFactoryController.darIdListaPuja();
		this.cantAnuncios = 0;
		this.listaPujas = listaPujas;
	}

	public Usuario(){

	}

	/**
	 * Metodo que permite comparar dos id, el del usuario actual y el
	 * pasado por parametro
	 * @param id a comparar con el usuario actual
	 * @return true || false
	 */
	public boolean compararId(Integer id) {return this.id.compareTo(id) == 0;}

	/**
	 * METODO QUE PERMITE ACTUALIZAR ATRIBUTOS DE UN USUARIO SIN CAMBIAR EL
	 * ID
	 * @param nuevoUsuario EL USUARIO QUE CONTIENE LOS ATRIBUTOS A ACTUALIZAR
	 */
    public void actualizarAtributos(Usuario nuevoUsuario) {
		this.name = nuevoUsuario.getName();
		this.age = nuevoUsuario.getAge();
		this.cedula=nuevoUsuario.getCedula();
		this.correo = nuevoUsuario.getCorreo();
		this.password = nuevoUsuario.getPassword();
    }


}
