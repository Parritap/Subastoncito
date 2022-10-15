package model;

import exceptions.LecturaException;
import persistencia.Persistencia;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import exceptions.CRUDExceptions;
import exceptions.EscrituraException;
import java.util.Objects;

/**
 * SINGLETON
 */
public class ModelFactoryController {

    //VARIABLE GENERAL PARA TODA LA EMPRESA
    private static EmpresaSubasta empresaSubasta;
    private static Integer idListaPujas=0;
    /**
     * METODO QUE DEVUELVE LA INSTANCIA DE LA EMPRESA
     * @return LA INSTANCIA DE LA EMPRESA
     */
    public static EmpresaSubasta getInstance(){
        return Objects.requireNonNullElseGet(empresaSubasta, () -> {
            try {
                return empresaSubasta = new EmpresaSubasta();
            } catch (CRUDExceptions e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void deserializarEmpresa() throws CRUDExceptions {
        empresaSubasta  = Persistencia.deserializarEmpresaXML();
        //hace una copia de seguridad del xml
        Persistencia.respaldarXML();
    }

    //Los metodos getRuta... sirven para obtener las rutas
    //especificadas en el taller

    public static String getRutaBase(){
        return Paths.get("").toAbsolutePath().toString()+"\\src";
    }

    /**devuelve la ruta en la que se guarda el log
     * @param nombreArchivo nombre del archivo en el que se guarda el log
     * @return ruta en la que se va a guardar el log*/
    public static String getRutaLogs(String nombreArchivo){
        return getRutaBase()+"\\persistencia\\log\\"+nombreArchivo;
    }

    public static String getRutaObjetos(String nombreArchivo){
        return getRutaBase()+"\\persistencia\\archivos\\"+nombreArchivo;
    }

    //CONSTRUYE LA RUTA EN BASE AL NOMBRE DE LA CLASE
    public static String getRutaObjetos(Object obj){
        Class<?> claseObj = obj.getClass();
        return getRutaBase()+"\\persistencia\\archivos\\"+claseObj.getSimpleName()+".txt";
    }

    /**devuelve la ruta de respaldo con el formato que debe tener
     * el nombre del archivo
     * */

    public static String getRutaRespaldo(String nombreArchivo){
        String[] componentes = nombreArchivo.split("\\.");
        String nombre  = componentes[0];
        String formato="";
        if(componentes.length > 1) {
            formato = componentes[1];
        }
        LocalDateTime f = LocalDateTime.now();
        return  getRutaBase()+"\\persistencia\\respaldo\\"+nombre+"__"+f.getDayOfMonth()+f.getMonthValue()+(f.getYear()%100)+
                "__"+f.getHour()+"__"+f.getMinute()+"__"+f.getSecond()+"."+formato;
    }

    public static String getRutaSerializado(String nombreArchivo){
        return  getRutaBase()+"\\persistencia\\"+nombreArchivo;
    }

    /**da un id para la lista de pujas, usualmente a objetos Usuario o Anuncio
     * este es necesario para la deserializacion
     * */
    public static Integer darIdListaPuja(){
        idListaPujas++;
        return idListaPujas;
    }
    public static ArrayList<Anuncio> getlistaAnuncios() {
        return getInstance().getListaAnuncios();
    }

    public static void addUsuario(Usuario usuario) throws EscrituraException, IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //serializa el usuario
        Persistencia.serializarUsuario(usuario);
        empresaSubasta.crearUsuario(usuario);
    }

    public static void crearAnuncio(Anuncio anuncio, Producto producto, Usuario clienteActivo) throws CRUDExceptions {
        empresaSubasta.crearAnuncio(anuncio, producto, clienteActivo);
    }

    public static void actualizarUsuario(Usuario clienteActivo, Usuario usuario) throws LecturaException {
        empresaSubasta.actualizarUsuario(clienteActivo, usuario);
    }
}
