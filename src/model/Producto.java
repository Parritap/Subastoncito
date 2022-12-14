package model;

import lombok.Getter;
import lombok.Setter;
import model.enums.Estado;
import model.enums.TipoProducto;
import java.io.Serializable;

@Getter
@Setter

public  class Producto implements Serializable {

    //VARIABLES GLOBALES
    private Integer id;
    private static int idAux;
    private String descripcion;
    private String nombre;
    private Estado estado;

    private TipoProducto tipoProducto;

    /**
     * CONSTRUCTOR
     * @param nombre NOMBRE DEL PRODUCTO
     * @param descripcion DESCRIPCION DEL PRODUCTO
     */
    public Producto(String nombre, String descripcion){
        id = ++ idAux;
        this.nombre = nombre;
        this.descripcion = descripcion;
        estado = Estado.NUEVO;
    }

    public Producto(){}

    public boolean compararId(Integer id){
        return this.id.compareTo(id) == 0;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", estado=" + estado +
                '}';
    }

    /**
     * Metodo que devuelve todos los atributos de la clase en un String separados por una arroba
     * @return String con todos los atributos de la clase separados por una arroba
     */
    public String getStringProducto() {
        StringBuilder arrobas = new StringBuilder("@@");
        return (arrobas.append(id)
                .append("@@")
                .append(nombre)
                .append("@@")
                .append(descripcion)
                .append("@@")
                .append(estado)
                .append("@@")).toString();
    }
    /**
     * Metodo equals que compara dos productos
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return nombre.equals(producto.nombre) && estado == producto.estado && tipoProducto == producto.tipoProducto;
    }
}
