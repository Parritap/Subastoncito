package controllers;

import application.App;
import exceptions.EscrituraException;
import exceptions.LecturaException;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.ModelFactoryController;
import model.Usuario;
import persistencia.ArchivoUtil;
import utilities.Utils;
import java.io.ByteArrayInputStream;

public class CrearCuentaController implements IApplication, Inicializable {


    private App application;
    //Nodos de la GUI
    @FXML
    private Button btnCrearCuenta;
    @FXML
    private Button btnActualizarCuenta;
    @FXML
    private Circle circleImage;
    @FXML
    private Spinner<Integer> edadSpinner;
    @FXML
    private MenuItem itemMastercard;

    @FXML
    private MenuItem itemPaypal;

    @FXML
    private MenuItem itemVisa;

    @FXML
    private MenuItem itermEfectivo;

    @FXML
    private RadioButton rbFemale;

    @FXML
    private RadioButton rbMale;

    @FXML
    private RadioButton rbNoMore;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtTelefono;

    @FXML
    private MenuButton cmbBoxPago;

    //Variables auxiliares para la creacion de las cuentas
    //y la actualization
    private String name;

    private Integer edad;

    private String cedula;

    private String correo;

    private String direccion;

    private String telefono;

    private String contrasenia;

    private CuentaController cuentaController;

    /**
     * Metodo que permite que al hacer clic se cargue una imagen
     * @param ignoredEvent generado al hacer clic
     */
    @FXML
    void cargarPerfil(MouseEvent ignoredEvent) {
        Image img = new Image(new ByteArrayInputStream(Utils.obtenerBytesImagen()), 199, 199, false, false);
        circleImage.setFill(new ImagePattern(img));
    }

    /**
     * Metodo que permite crear una cuenta
     * @param event generado al hacer clic
     */
    @FXML
    void crearCuenta(ActionEvent event) {
        //Los campos de textos requeridos para crear el usuario
        //cliente activo es el cliente que tiene la application
        if(cargarCampos() && application.getClienteActivo() == null){
            //creo el usuario con los datos obtenidos en el txt
            Usuario usuario = new Usuario(name, edad, cedula, correo, direccion, telefono, contrasenia);
            //el singleton agrega el usuario a la lista
            try {
                ModelFactoryController.addUsuario(usuario);
                limpiarCamposTexto();
                application.abrirAlerta("El usuario se agregó correctamente");
                application.setClienteActivo(usuario);
                btnCrearCuenta.setVisible(false);
                ArchivoUtil.guardarRegistroLog("se creo el usuario "+usuario.getId()+":"+usuario.getName(), 1, "CreacionUsuario", ModelFactoryController.getRutaLogs("CreacionUsuario"));
                btnActualizarCuenta.setVisible(true);
                cuentaController.mostrarBotonesBarraLateral();
            } catch (EscrituraException e) {
                //si el usuario ya existe entonces se lanza una excepcion
                application.abrirAlerta(e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            //lo establezco como usuario activo

        }
    }

    /**
     * Metodo que permite actualizar la cuenta al hacer clic
     * @param event generado al hacer clic
     */
    @FXML
    void actualizarCuenta(ActionEvent event) {
        cargarCampos();
        Usuario usuario = new Usuario(name, edad, cedula, correo, direccion, telefono, contrasenia);
        try {
            ModelFactoryController.actualizarUsuario(application.getClienteActivo(), usuario);
        } catch (LecturaException e) {
            application.abrirAlerta(e.getMessage());
        }
    }
    /**
     * Este metodo carga los campos en las variables auxiliares,
     * también verifica que los campos no estén vacíos
     * @return true si todos los campos están llenos, false si falta alguno
     */
    private boolean cargarCampos() {
        String mensaje = "";

        if(!txtName.getText().equals("")){
            name = txtName.getText();
        }else {
            mensaje += "Agregue un nombre\n";
        }

        edad = edadSpinner.getValue();

        if(!txtCedula.getText().equals("")){
            cedula = txtCedula.getText();
        }else {
            mensaje += "Agregue una cedula\n";
        }

        if(!txtCorreo.getText().equals("")){
            correo = txtCorreo.getText();
        }else{
            mensaje += "Agregue un correo \n";
        }

        if(!txtDireccion.getText().equals("")){
            direccion = txtDireccion.getText();
        }else{
            mensaje += "Agregue una direccion\n";
        }

        if(!txtTelefono.getText().equals("")){
            telefono = txtTelefono.getText();
        }else {
            mensaje += "Agregue un telefono";
        }

        if(!txtPassword.getText().equals("")){
            contrasenia = txtPassword.getText();
        }else {
            mensaje += "Agregue una contraseña";
        }
        if(!mensaje.equals("")){
            application.abrirAlerta(mensaje);
            return false;
        }

        return true;
    }


    @FXML
    void initialize() {
        circleImage.setFill(new ImagePattern(new Image(Utils.profileImage)));
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(18, 100, 18);
        edadSpinner.setValueFactory(valueFactory);
        ToggleGroup group = new ToggleGroup();
        rbFemale.setToggleGroup(group);
        rbMale.setToggleGroup(group);
        rbNoMore.setToggleGroup(group);
    }

    /**
     * Este metodo permite vaciar la informacion contenida en los
     * campos de texto
     */
    private void limpiarCamposTexto() {
        txtName.setText("");
        txtPassword.setText("");
        txtCedula.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        cmbBoxPago.setText("Efectivo");
        txtTelefono.setText("");
    }

    /**
     * Metodo que cambia el valor del metodo de pago
     * @param event generado al hacer clic
     */
    @FXML
    void setValueComboBox(ActionEvent event) {
        Object itemSeleccionado =  event.getSource();
        if(itemSeleccionado == itemMastercard) cmbBoxPago.setText("Mastercard");
        else if( itemSeleccionado == itemPaypal) cmbBoxPago.setText("Paypal");
        else if( itemSeleccionado == itemVisa) cmbBoxPago.setText("Visa");
        else if( itemSeleccionado == itermEfectivo) cmbBoxPago.setText("Efectivo");
    }

    @Override
    public App getApplication() {
        return application;
    }

    @Override
    public void setApplication(App application) {
        this.application =application;
    }

    @Override
    public void inicializarComponentes() {
        if(application.getClienteActivo() == null){
            btnCrearCuenta.setVisible(true);
            btnActualizarCuenta.setVisible(false);
        }else {
            btnCrearCuenta.setVisible(false);
            btnActualizarCuenta.setVisible(true);
        }
        cuentaController = application.getCuentaController();
    }
}