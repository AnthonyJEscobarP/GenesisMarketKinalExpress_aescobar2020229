package org.anthonyescobar.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.anthonyescobar.bean.Clientes;
import org.anthonyescobar.db.Conexion;
import org.anthonyescobar.report.GenerarReportes;
import org.anthonyescobar.system.Main;

/**
 *
 * @author Anthony Josue Escobar Ponce
 */
public class MenuClientesController implements Initializable {

    // -------------------------------------------- //
    private Main escenarioPrincipal;
    private ObservableList<Clientes> listadoClientes;

    private enum operaciones {

        NULO, ACTUALIZAR, ELIMINAR
    }
    private operaciones tipoDeOperaciones = operaciones.NULO;
    // -------------------------------------------- //
    @FXML
    private TableView tblClientes;
    @FXML
    private TableColumn colClienteID;
    @FXML
    private TableColumn colNombreClientes;
    @FXML
    private TableColumn colApellidoClientes;
    @FXML
    private TableColumn colNitClientes;
    @FXML
    private TableColumn colDireccionClientes;
    @FXML
    private TableColumn colTelefonoClientes;
    @FXML
    private TableColumn colCorreoClientes;
    // -------------------------------------------- //
    @FXML
    private TextField txtNombreClientes;
    @FXML
    private TextField txtApellidoClientes;
    @FXML
    private TextField txtNitClientes;
    @FXML
    private TextField txtDireccionClientes;
    @FXML
    private TextField txtTelefonoClientes;
    @FXML
    private TextField txtCorreoClientes;
    @FXML
    private Button btnAgregarC;
    @FXML
    private Button btnEditarC;
    @FXML
    private Button btnEliminarC;
    @FXML
    private Button btnReporteC;
    @FXML
    private Button btnRegresar;
    // -------------------------------------------- //
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgReporte;
    // -------------------------------------------- //
    // ENCAPSULAMIENTO PARA MENU INICIAR

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    //METODO DE INCIO PARA BASE DE DATOS VINCULADO AQUI EN JAVA
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        desactivarControls();
    }

    //METODO CARGAR DATOS EN JAVA
    public void cargarDatos() {
        tblClientes.setItems(mostrarClientes());
        colClienteID.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("clienteID"));
        colNombreClientes.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreCliente"));
        colApellidoClientes.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidoCliente"));
        colNitClientes.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nitCliente"));
        colDireccionClientes.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCliente"));
        colTelefonoClientes.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
        colCorreoClientes.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));
    }

    //METODO LISTAR 
    public ObservableList<Clientes> mostrarClientes() {
        ArrayList<Clientes> listadoC = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listadoC.add(new Clientes(resultado.getInt("clienteID"),
                        resultado.getString("nombreCliente"),
                        resultado.getString("apellidoCliente"),
                        resultado.getString("nitCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")));
            }//WHILE
        } catch (Exception e) {
            e.printStackTrace();
        }//CATCH TRY
        return listadoClientes = FXCollections.observableArrayList(listadoC);
    } //METODO

    public void agregarCliente() {
        switch (tipoDeOperaciones) {
            case NULO:
                limpiarControls();
                tblClientes.setDisable(false);//////////////////////////////////
                activarControls();
                btnAgregarC.setText("Guardar");
                btnEliminarC.setText("Cancelar");
                btnEditarC.setDisable(true);
                btnReporteC.setDisable(true);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardarInformacion();
                cargarDatos();
                desactivarControls();
                limpiarControls();
                btnAgregarC.setText("Agregar");
                btnEliminarC.setText("Eliminar");
                btnEditarC.setDisable(false);
                btnReporteC.setDisable(false);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoEliminar.png"));
                tipoDeOperaciones = operaciones.NULO;
                break;
        }//SWITVH
        tblClientes.setDisable(true);///////////////////////////////////////////
    }//METODO

    public void eliminarCliente() {
        tblClientes.setDisable(false);//////////////////////////////////////////
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControls();
                limpiarControls();
                btnAgregarC.setText("Agregar");
                btnEliminarC.setText("Eliminar");
                btnEditarC.setDisable(false);
                btnReporteC.setDisable(false);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoEliminar.png"));
                tipoDeOperaciones = operaciones.NULO;
                break;
            default:
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar al cliente?", "Eliminar Clientes",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCliente(?)}");
                            procedimiento.setInt(1, ((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getClienteID());
                            procedimiento.execute();
                            listadoClientes.remove(tblClientes.getSelectionModel().getSelectedItem());
                            limpiarControls();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }//CATCH & TRY
                    } else {
                        tblClientes.getSelectionModel().clearSelection();
                        limpiarControls();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila con datos", "Seleccionar el Cliente a eliminar porfavor", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }//SWITCH
    }//METODO

    public void editarCliente() {
        switch (tipoDeOperaciones) {
            case NULO:
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    btnEditarC.setText("Actualizar");
                    btnReporteC.setText("Cancelar");
                    btnAgregarC.setDisable(true);
                    btnEliminarC.setDisable(true);
                    imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoActualizar.png"));
                    imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                    activarControls();
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila con datos", "Seleccionar Cliente Gracias :)",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case ACTUALIZAR:
                actualizarInformacion();
                btnEditarC.setText("Editar");
                btnReporteC.setText("Reporte");
                btnAgregarC.setDisable(false);
                btnEliminarC.setDisable(false);
                desactivarControls();
                limpiarControls();
                imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoEditar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoListar.png"));
                tipoDeOperaciones = operaciones.NULO;
                cargarDatos();
                break;
        }
    }

    public void imprimirReporte(){
        Map parametros= new HashMap();
        parametros.put("clienteID", null);
        GenerarReportes.mostrarReportes("ReporteClientes.jasper", "Reporte de clientes", parametros);
        
    }
    
    public void reportesCancelarClientes() {
        switch (tipoDeOperaciones) {
            case NULO:
                imprimirReporte();
                break;
            case ACTUALIZAR:
                desactivarControls();
                limpiarControls();
                btnEditarC.setText("Editar");
                btnReporteC.setText("Reporte");
                btnAgregarC.setDisable(false);
                btnEliminarC.setDisable(false);
                imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoEditar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoListar.png"));
                tipoDeOperaciones = operaciones.NULO;
                break;

        }//SWITVH
    }//METODO

    
    public void actualizarInformacion() {
        try {
            if (txtNombreClientes.getText().equals("") || txtApellidoClientes.getText().equals("")
                || txtNitClientes.getText().equals("") || txtDireccionClientes.getText().equals("") || txtTelefonoClientes.getText().equals("")
                || txtCorreoClientes.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos porfavor", "Ingrese su informacion", JOptionPane.ERROR_MESSAGE);
            } else {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarCliente(?,?,?,?,?,?,?)}");
                Clientes clienteGuardar = (Clientes) tblClientes.getSelectionModel().getSelectedItem();
                clienteGuardar.setNombreCliente(txtNombreClientes.getText());
                clienteGuardar.setApellidoCliente(txtApellidoClientes.getText());
                clienteGuardar.setNitCliente(txtNitClientes.getText());
                clienteGuardar.setDireccionCliente(txtDireccionClientes.getText());
                clienteGuardar.setTelefonoCliente(txtTelefonoClientes.getText());
                clienteGuardar.setCorreoClientes(txtCorreoClientes.getText());
                // -------------------------------------------------------------- //
                procedimiento.setInt(1, clienteGuardar.getClienteID());
                procedimiento.setString(2, clienteGuardar.getNombreCliente());
                procedimiento.setString(3, clienteGuardar.getApellidoCliente());
                procedimiento.setString(4, clienteGuardar.getNitCliente());
                procedimiento.setString(5, clienteGuardar.getDireccionCliente());
                procedimiento.setString(6, clienteGuardar.getTelefonoCliente());
                procedimiento.setString(7, clienteGuardar.getCorreoClientes());
                procedimiento.execute();
            }//if else
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//METODO

    public void guardarInformacion() {
        if (txtNombreClientes.getText().equals("") || txtApellidoClientes.getText().equals("")
                || txtNitClientes.getText().equals("") || txtDireccionClientes.getText().equals("") || txtTelefonoClientes.getText().equals("")
                || txtCorreoClientes.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos porfavor", "Ingrese su informacion", JOptionPane.ERROR_MESSAGE);
        } else {
            Clientes clienteGuardar = new Clientes();
            clienteGuardar.setNombreCliente(txtNombreClientes.getText());
            clienteGuardar.setApellidoCliente(txtApellidoClientes.getText());
            clienteGuardar.setNitCliente(txtNitClientes.getText());
            clienteGuardar.setDireccionCliente(txtDireccionClientes.getText());
            clienteGuardar.setTelefonoCliente(txtTelefonoClientes.getText());
            clienteGuardar.setCorreoClientes(txtCorreoClientes.getText());
            try {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarCliente(?,?,?,?,?,?)}");
                procedimiento.setString(1, clienteGuardar.getNombreCliente());
                procedimiento.setString(2, clienteGuardar.getApellidoCliente());
                procedimiento.setString(3, clienteGuardar.getNitCliente());
                procedimiento.setString(4, clienteGuardar.getDireccionCliente());
                procedimiento.setString(5, clienteGuardar.getTelefonoCliente());
                procedimiento.setString(6, clienteGuardar.getCorreoClientes());
                procedimiento.execute();
                listadoClientes.add(clienteGuardar);
            } catch (Exception e) {
                e.printStackTrace();
            }//TRY CATCH
        }
    }//METODO

    public void seleccionarFila() {
        if (tblClientes.getSelectionModel().getSelectedItem() != null) {
            txtNombreClientes.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNombreCliente());
            txtApellidoClientes.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getApellidoCliente());
            txtNitClientes.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNitCliente());
            txtDireccionClientes.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
            txtTelefonoClientes.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
            txtCorreoClientes.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCorreoClientes());
        } else {
            JOptionPane.showMessageDialog(null, "Seleccionar fila con datos", "Seleccionar Cliente", JOptionPane.ERROR_MESSAGE);
            tblClientes.getSelectionModel().clearSelection();
        }
    }

    //METODO ACTIVAR LLENADO CONTROLES TXTS
    public void activarControls() {
        txtNombreClientes.setEditable(true);
        txtApellidoClientes.setEditable(true);
        txtNitClientes.setEditable(true);
        txtDireccionClientes.setEditable(true);
        txtTelefonoClientes.setEditable(true);
        txtCorreoClientes.setEditable(true);
    }

    //METODO DESACTIVAR LLENADO CONTROLES TXTS
    public void desactivarControls() {
        txtNombreClientes.setEditable(false);
        txtApellidoClientes.setEditable(false);
        txtNitClientes.setEditable(false);
        txtDireccionClientes.setEditable(false);
        txtTelefonoClientes.setEditable(false);
        txtCorreoClientes.setEditable(false);
    }

    //METODO LIMPIAR CONTROLES TXTS DE LLENADO
    public void limpiarControls() {
        txtNombreClientes.clear();
        txtApellidoClientes.clear();
        txtNitClientes.clear();
        txtDireccionClientes.clear();
        txtTelefonoClientes.clear();
        txtCorreoClientes.clear();
    }

    @FXML
    public void ButtonsActionsMenuClientes(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}//TABULAR TODO CON ALT SHIFT + F

