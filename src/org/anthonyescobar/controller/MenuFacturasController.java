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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.anthonyescobar.bean.Clientes;
import org.anthonyescobar.bean.Empleados;
import org.anthonyescobar.bean.Factura;
import org.anthonyescobar.db.Conexion;
import org.anthonyescobar.report.GenerarReportes;
import org.anthonyescobar.system.Main;

/**
 *
 * @author Anthony Josue Escobar Ponce
 */
public class MenuFacturasController implements Initializable {

    // -------------------------------------------- //
    private Main escenarioPrincipal;
    private ObservableList<Clientes> listadoClientes;
    private ObservableList<Factura> listadoFacturas;
    private ObservableList<Empleados> listadoEmpleados;

    private enum operaciones {

        NULO, ACTUALIZAR, ELIMINAR
    }
    private operaciones tipoDeOperaciones = operaciones.NULO;
    // -------------------------------------------- //
    @FXML
    private TableView tblFacturas;
    @FXML
    private TableColumn colNumeroDeFactura;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colTotalFactura;
    @FXML
    private TableColumn colFechaFactura;
    @FXML
    private TableColumn colCodigoCliente;
    @FXML
    private TableColumn colCodigoEmpleado;
    // -------------------------------------------- //
    @FXML
    private TextField txtNumeroDeFactura;
    @FXML
    private TextField txtEstado;
    @FXML
    private TextField txtTotalFactura;
    @FXML
    private TextField txtFechaFactura;
    @FXML
    private ComboBox cmbCodigoCliente;
    @FXML
    private ComboBox cmbCodigoEmpleado;
    // -------------------------------------------- //
    @FXML
    private Button btnAgregarFct;
    @FXML
    private Button btnEditarFct;
    @FXML
    private Button btnEliminarFct;
    @FXML
    private Button btnReporteFct;
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
        cmbCodigoCliente.setItems(mostrarClientes());
        cmbCodigoEmpleado.setItems(mostrarEmpleado());
    }

    //METODO CARGAR DATOS EN JAVA
    public void cargarDatos() {
        tblFacturas.setItems(mostrarFactura());
        colNumeroDeFactura.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("numeroDeFactura"));
        colEstado.setCellValueFactory(new PropertyValueFactory<Factura, String>("estado"));
        colTotalFactura.setCellValueFactory(new PropertyValueFactory<Factura, Double>("totalFactura"));
        colFechaFactura.setCellValueFactory(new PropertyValueFactory<Factura, String>("fechaFactura"));
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoCliente"));
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoEmpleado"));
    }

    //metodo MOSTRAR FACTURA
    public ObservableList<Factura> mostrarFactura() {
        ArrayList<Factura> listaFacturas = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaFacturas.add(new Factura(resultado.getInt("numeroDeFactura"),
                                                        resultado.getString("estado"),
                                                        resultado.getDouble("totalFactura"),
                                                        resultado.getString("fechaFactura"),
                                                        resultado.getInt("codigoCliente"), 
                                                        resultado.getInt("codigoEmpleado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listadoFacturas = FXCollections.observableList(listaFacturas);
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

    public Clientes buscarCliente(int cod){
        Clientes result=null;
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarCliente(?)}");
           procedimiento.setInt(1, cod);
           ResultSet registro = procedimiento.executeQuery();
           while(registro.next()){
               result=new Clientes(registro.getInt("codigoCliente"),
                        registro.getString("NITCliente"),
                        registro.getString("nombreCliente"),
                        registro.getString("apellidosCliente"),
                        registro.getString("direccionCliente"),
                        registro.getString("telefonoCliente"),
                        registro.getString("correoCliente"));
           }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
    public Empleados buscarEmpleado(int cod){
        Empleados result=null;
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarEmpleado(?)}");
           procedimiento.setInt(1, cod);
           ResultSet registro = procedimiento.executeQuery();
           while(registro.next()){
               result=new Empleados(registro.getInt("codigoEmpleado"),
                                   registro.getString("nombresEmpleado"),
                                 registro.getString("apellidosEmpleado"),
                                          registro.getDouble("sueldo"),
                                        registro.getString("direccion"), 
                                           registro.getString("turno"),
                                registro.getInt("codigoCargoEmpleado"));
           }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
    public ObservableList<Empleados> mostrarEmpleado() {
        ArrayList<Empleados> listaEmpleado = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                listaEmpleado.add(new Empleados(resultado.getInt("codigoEmpleado"),
                                                        resultado.getString("nombresEmpleado"),
                                                        resultado.getString("apellidosEmpleado"),
                                                        resultado.getDouble("sueldo"),
                                                        resultado.getString("direccion"), 
                                                        resultado.getString("turno"),
                                                        resultado.getInt("codigoCargoEmpleado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listadoEmpleados = FXCollections.observableList(listaEmpleado);
    }

    public void agregarFactura() {
        switch (tipoDeOperaciones) {
            case NULO:
                limpiarControls();
                tblFacturas.setDisable(false);//////////////////////////////////
                activarControls();
                btnAgregarFct.setText("Guardar");
                btnEliminarFct.setText("Cancelar");
                btnEditarFct.setDisable(true);
                btnReporteFct.setDisable(true);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardarInformacion();
                cargarDatos();
                desactivarControls();
                limpiarControls();
                btnAgregarFct.setText("Agregar");
                btnEliminarFct.setText("Eliminar");
                btnEditarFct.setDisable(false);
                btnReporteFct.setDisable(false);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoEliminar.png"));
                tipoDeOperaciones = operaciones.NULO;
                break;
        }//SWITVH
        tblFacturas.setDisable(true);
    }//METODO

    public void eliminarFactura() {
        tblFacturas.setDisable(false);
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControls();
                limpiarControls();
                btnAgregarFct.setText("Agregar");
                btnEliminarFct.setText("Eliminar");
                btnEditarFct.setDisable(false);
                btnReporteFct.setDisable(false);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoEliminar.png"));
                tipoDeOperaciones = operaciones.NULO;
                break;
            default:
                if (tblFacturas.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar la Factura ?", "Eliminar Factura",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarFactura(?)}");
                            procedimiento.setInt(1, ((Factura) tblFacturas.getSelectionModel().getSelectedItem()).getNumeroDeFactura());
                            procedimiento.execute();
                            listadoFacturas.remove(tblFacturas.getSelectionModel().getSelectedItem());
                            limpiarControls();
                        } catch (Exception e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error eliminando la Factura || reintentar: " + e.getMessage());
                        }//CATCH & TRY
                    } else {
                        tblFacturas.getSelectionModel().clearSelection();
                        limpiarControls();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila con datos", "Seleccionar el Factura a eliminar porfavor", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }//SWITCH
    }//METODO

    public void editarFactura() {
        switch (tipoDeOperaciones) {
            case NULO:
                if (tblFacturas.getSelectionModel().getSelectedItem() != null) {
                    activarControls();
                    btnEditarFct.setText("Actualizar");
                    btnReporteFct.setText("Cancelar");
                    btnAgregarFct.setDisable(true);
                    btnEliminarFct.setDisable(true);
                    imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoActualizar.png"));
                    imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila con datos", "Seleccionar Factura Gracias :)",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case ACTUALIZAR:
                actualizarInformacion();
                btnEditarFct.setText("Editar");
                btnReporteFct.setText("Reporte");
                btnAgregarFct.setDisable(false);
                btnEliminarFct.setDisable(false);
                desactivarControls();
                limpiarControls();
                imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoEditar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoListar.png"));
                tipoDeOperaciones = operaciones.NULO;
                cargarDatos();
                break;
        }
    }

    public void imprimirReporte() {
        Map parametros = new HashMap();
        int IdFactura = Integer.valueOf(((Factura) tblFacturas.getSelectionModel().getSelectedItem()).getNumeroDeFactura());
        parametros.put("IdFactura", IdFactura);
        
        GenerarReportes.mostrarReportes("Factura.jasper", "Factura", parametros);
    }
    
    public void reportesCancelarFactura() {
        switch (tipoDeOperaciones) {
            case NULO:
                imprimirReporte();
                 break;
            case ACTUALIZAR:
                desactivarControls();
                limpiarControls();
                btnEditarFct.setText("Editar");
                btnReporteFct.setText("Reporte");
                btnAgregarFct.setDisable(false);
                btnEliminarFct.setDisable(false);
                imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoEditar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoListar.png"));
                tipoDeOperaciones = operaciones.NULO;
                break;

        }//SWITVH
    }//METODO

    
    
    
    public void actualizarInformacion() {
        try {
            if (txtNumeroDeFactura.getText().equals("") || txtEstado.getText().equals("") || txtTotalFactura.getText().equals("")
                || txtFechaFactura.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos porfavor", "Ingrese su informacion", JOptionPane.ERROR_MESSAGE);
            } else {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarFactura(?,?,?,?,?,?)}");
                Factura facturaGuardar = new Factura();
                facturaGuardar.setNumeroDeFactura(Integer.parseInt(txtNumeroDeFactura.getText()));
                facturaGuardar.setEstado(txtEstado.getText());
                facturaGuardar.setTotalFactura(Double.parseDouble(txtEstado.getText()));
                facturaGuardar.setFechaFactura(txtEstado.getText());
                facturaGuardar.setCodigoCliente(((Clientes) cmbCodigoCliente.getSelectionModel().getSelectedItem()).getClienteID());
                facturaGuardar.setCodigoEmpleado(((Empleados) cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                // -------------------------------------------------------------- //
                    procedimiento.setInt(1, facturaGuardar.getNumeroDeFactura());
                    procedimiento.setString(2, facturaGuardar.getEstado());
                    procedimiento.setDouble(3, facturaGuardar.getTotalFactura());
                    procedimiento.setString(4, facturaGuardar.getFechaFactura());
                    procedimiento.setInt(5, facturaGuardar.getCodigoCliente());
                    procedimiento.setInt(6, facturaGuardar.getCodigoEmpleado());
                    procedimiento.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//METODO

    public void guardarInformacion() {
        if (txtNumeroDeFactura.getText().equals("") || txtEstado.getText().equals("") || txtTotalFactura.getText().equals("")
                || txtFechaFactura.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos porfavor", "Ingrese su informacion", JOptionPane.ERROR_MESSAGE);
            eliminarFactura();
        } else {
            Factura facturaGuardar = new Factura();
                facturaGuardar.setNumeroDeFactura(Integer.parseInt(txtNumeroDeFactura.getText()));
                facturaGuardar.setEstado(txtEstado.getText());
                facturaGuardar.setTotalFactura(Double.parseDouble(txtEstado.getText()));
                facturaGuardar.setFechaFactura(txtEstado.getText());
                facturaGuardar.setCodigoCliente(((Clientes) cmbCodigoCliente.getSelectionModel().getSelectedItem()).getClienteID());
                facturaGuardar.setCodigoEmpleado(((Empleados) cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
            try {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarFactura(?,?,?,?,?,?,?,?)}");
                procedimiento.setInt(1, facturaGuardar.getNumeroDeFactura());
                    procedimiento.setString(2, facturaGuardar.getEstado());
                    procedimiento.setDouble(3, facturaGuardar.getTotalFactura());
                    procedimiento.setString(4, facturaGuardar.getFechaFactura());
                    procedimiento.setInt(5, facturaGuardar.getCodigoCliente());
                    procedimiento.setInt(6, facturaGuardar.getCodigoEmpleado());
                    procedimiento.execute();
                    listadoFacturas.add(facturaGuardar);
            } catch (Exception e) {
                e.printStackTrace();
            }//TRY CATCH
        }
    }//METODO
    
    public void seleccionarFila() {
        if (tblFacturas.getSelectionModel().getSelectedItem() != null) {
            txtNumeroDeFactura.setText(String.valueOf(((Factura) tblFacturas.getSelectionModel().getSelectedItem()).getNumeroDeFactura()));
            txtEstado.setText(((Factura) tblFacturas.getSelectionModel().getSelectedItem()).getEstado());
            txtTotalFactura.setText(String.valueOf(((Factura) tblFacturas.getSelectionModel().getSelectedItem()).getTotalFactura()));
            txtFechaFactura.setText(((Factura) tblFacturas.getSelectionModel().getSelectedItem()).getFechaFactura());
            cmbCodigoCliente.getSelectionModel().select(buscarCliente(((Factura)tblFacturas.getSelectionModel().getSelectedItem()).getCodigoCliente()));
            cmbCodigoEmpleado.getSelectionModel().select(buscarEmpleado(((Factura)tblFacturas.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        } else {
            JOptionPane.showMessageDialog(null, "Seleccionar fila con datos", "Seleccionar Factura", JOptionPane.ERROR_MESSAGE);
            tblFacturas.getSelectionModel().clearSelection();
        }
    }

    //METODO ACTIVAR LLENADO CONTROLES TXTS
    public void activarControls() {
        txtNumeroDeFactura.setEditable(true);
        txtEstado.setEditable(true);
        txtTotalFactura.setEditable(true);
        txtFechaFactura.setEditable(true);
        cmbCodigoCliente.setDisable(false);
        cmbCodigoEmpleado.setDisable(false);
    }

    //METODO DESACTIVAR LLENADO CONTROLES TXTS
    public void desactivarControls() {
        txtNumeroDeFactura.setEditable(false);
        txtEstado.setEditable(false);
        txtTotalFactura.setEditable(false);
        txtFechaFactura.setEditable(false);
        cmbCodigoCliente.setDisable(true);
        cmbCodigoEmpleado.setDisable(true);
    }

    //METODO LIMPIAR CONTROLES TXTS DE LLENADO
    public void limpiarControls() {
        txtNumeroDeFactura.clear();
        txtEstado.clear();
        txtTotalFactura.clear();
        txtFechaFactura.clear();
        cmbCodigoCliente.setValue(null);
        cmbCodigoEmpleado.setValue(null);
    }

    @FXML
    public void ButtonsActionsMenuProductos(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}//TABULAR TODO CON ALT SHIFT + F