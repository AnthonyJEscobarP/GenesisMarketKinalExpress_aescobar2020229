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
import org.anthonyescobar.bean.CargoEmpleado;
import org.anthonyescobar.bean.Empleados;
import org.anthonyescobar.db.Conexion;
import org.anthonyescobar.report.GenerarReportes;
import org.anthonyescobar.system.Main;

/**
 *
 * @author Anthony Josue Escobar Ponce
 */
public class MenuEmpleadoController implements Initializable {

    // -------------------------------------------- //
    private Main escenarioPrincipal;
    private ObservableList<Empleados> listadoEmpleados;
     private ObservableList<CargoEmpleado> listadoCargoEmpleado;

    private enum operaciones {

        NULO, ACTUALIZAR, ELIMINAR
    }
    private operaciones tipoDeOperaciones = operaciones.NULO;
    // -------------------------------------------- //
    @FXML
    private TableView tblEmpleados;
    @FXML
    private TableColumn colCodigoEmpleado;
    @FXML
    private TableColumn colNombresEmpleado;
    @FXML
    private TableColumn colApellidosEmpleado;
    @FXML
    private TableColumn colSueldo;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TableColumn colTurno;
    @FXML
    private TableColumn colCodigoCargoEmpleado;
    // -------------------------------------------- //
     @FXML
    private TextField txtCodigoEmpleado;
    @FXML
    private TextField txtNombresEmpleado;
    @FXML
    private TextField txtApellidosEmpleado;
    @FXML
    private TextField txtSueldo;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtTurno;
    @FXML
    private ComboBox cmbCodigoCargoEmpleado;
    // -------------------------------------------- //
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
        tblEmpleados.setItems(mostrarEmpleado());
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoEmpleado"));
        colNombresEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombresEmpleado"));
        colApellidosEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidosEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empleados, String>("direccion"));
        colTurno.setCellValueFactory(new PropertyValueFactory<Empleados, String>("turno"));
        colCodigoCargoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoCargoEmpleado"));
    }

    //METODO LISTAR 
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

    //METODO LISTAR  CRG EMPLEADO
    public ObservableList<CargoEmpleado> mostrarCargoEmpleados() {
        ArrayList<CargoEmpleado> listadoCrgEmp = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCargosEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listadoCrgEmp.add(new CargoEmpleado(resultado.getInt("codigoCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")));
            }//WHILE
        } catch (Exception e) {
            e.printStackTrace();
        }//CATCH TRY
        return listadoCargoEmpleado = FXCollections.observableArrayList(listadoCrgEmp);
    } //METODO
    
    public CargoEmpleado buscarCargoEmpleado(int codigoCargoEmpleado){
        CargoEmpleado resultado = null;
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarCargoEmpleado(?)}");
           procedimiento.setInt(1, codigoCargoEmpleado);
           ResultSet cargoEncontrado = procedimiento.executeQuery();
           while(cargoEncontrado.next()){
               resultado =new CargoEmpleado(cargoEncontrado.getInt("codigoCargoEmpleado"),
                                        cargoEncontrado.getString("nombreCargo"),
                                    cargoEncontrado.getString("descripcionCargo"));
           }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public void agregarEmpleado() {
        switch (tipoDeOperaciones) {
            case NULO:
                limpiarControls();
                tblEmpleados.setDisable(false);//////////////////////////////////
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
        tblEmpleados.setDisable(true);///////////////////////////////////////////
    }//METODO

    public void eliminarEmpleado() {
        tblEmpleados.setDisable(false);//////////////////////////////////////////
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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar al cliente?", "Eliminar Clientes",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarEmpleado(?)}");
                            procedimiento.setInt(1, ((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
                            procedimiento.execute();
                            listadoEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedItem());
                            limpiarControls();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }//CATCH & TRY
                    } else {
                        tblEmpleados.getSelectionModel().clearSelection();
                        limpiarControls();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila con datos", "Seleccionar el Empleado a eliminar porfavor", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }//SWITCH
    }//METODO

    public void editarEmpleado() {
        switch (tipoDeOperaciones) {
            case NULO:
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    btnEditarC.setText("Actualizar");
                    btnReporteC.setText("Cancelar");
                    btnAgregarC.setDisable(true);
                    btnEliminarC.setDisable(true);
                    imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoActualizar.png"));
                    imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                    activarControls();
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila con datos", "Seleccionar Empleado Gracias :)",
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
        GenerarReportes.mostrarReportes("ReportesDeEmpleado.jasper", "Reporte de Empleados", parametros);
        
    }
    
    public void reportesCancelarEmpleado() {
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
            if (txtCodigoEmpleado.getText().equals("") || txtNombresEmpleado.getText().equals("") || txtApellidosEmpleado.getText().equals("")
                || txtSueldo.getText().equals("") || txtDireccion.getText().equals("") || txtTurno.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos porfavor", "Ingrese su informacion", JOptionPane.ERROR_MESSAGE);
            } else {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarEmpleado(?,?,?,?,?,?,?)}");
                Empleados empleadoGuardar = (Empleados) tblEmpleados.getSelectionModel().getSelectedItem();
                empleadoGuardar.setCodigoEmpleado(Integer.parseInt(txtCodigoEmpleado.getText()));
                empleadoGuardar.setNombresEmpleado(txtNombresEmpleado.getText());
                empleadoGuardar.setApellidosEmpleado(txtApellidosEmpleado.getText());
                empleadoGuardar.setSueldo(Double.parseDouble(txtSueldo.getText()));
                empleadoGuardar.setDireccion(txtDireccion.getText());
                empleadoGuardar.setTurno(txtTurno.getText());
                empleadoGuardar.setCodigoCargoEmpleado(((CargoEmpleado) cmbCodigoCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
                // -------------------------------------------------------------- //
                procedimiento.setInt(1, empleadoGuardar.getCodigoEmpleado());
                procedimiento.setString(2, empleadoGuardar.getNombresEmpleado());
                procedimiento.setString(3, empleadoGuardar.getApellidosEmpleado());
                procedimiento.setDouble(4, empleadoGuardar.getSueldo());
                procedimiento.setString(5, empleadoGuardar.getDireccion());
                procedimiento.setString(6, empleadoGuardar.getTurno());
                procedimiento.setInt(7, empleadoGuardar.getCodigoCargoEmpleado());
                procedimiento.execute();
            }//if else
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//METODO

    public void guardarInformacion() {
        if (txtCodigoEmpleado.getText().equals("") || txtNombresEmpleado.getText().equals("") || txtApellidosEmpleado.getText().equals("")
                || txtSueldo.getText().equals("") || txtDireccion.getText().equals("") || txtTurno.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos porfavor", "Ingrese su informacion", JOptionPane.ERROR_MESSAGE);
        } else {
            Empleados empleadoGuardar = new Empleados();
            empleadoGuardar.setCodigoEmpleado(Integer.parseInt(txtCodigoEmpleado.getText()));
            empleadoGuardar.setNombresEmpleado(txtNombresEmpleado.getText());
            empleadoGuardar.setApellidosEmpleado(txtApellidosEmpleado.getText());
            empleadoGuardar.setSueldo(Double.parseDouble(txtSueldo.getText()));
            empleadoGuardar.setDireccion(txtDireccion.getText());
            empleadoGuardar.setTurno(txtTurno.getText());
            empleadoGuardar.setCodigoCargoEmpleado(((CargoEmpleado) cmbCodigoCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
            try {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarEmpleado(?,?,?,?,?,?,?)}");
                procedimiento.setInt(1, empleadoGuardar.getCodigoEmpleado());
                procedimiento.setString(2, empleadoGuardar.getNombresEmpleado());
                procedimiento.setString(3, empleadoGuardar.getApellidosEmpleado());
                procedimiento.setDouble(4, empleadoGuardar.getSueldo());
                procedimiento.setString(5, empleadoGuardar.getDireccion());
                procedimiento.setString(6, empleadoGuardar.getTurno());
                procedimiento.setInt(7, empleadoGuardar.getCodigoCargoEmpleado());
                procedimiento.execute();
                listadoEmpleados.add(empleadoGuardar);
            } catch (Exception e) {
                e.printStackTrace();
            }//TRY CATCH
        }
    }//METODO

    public void seleccionarFila() {
        if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
            txtCodigoEmpleado.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
            txtNombresEmpleado.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado());
            txtApellidosEmpleado.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado());
            txtSueldo.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
            txtDireccion.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getDireccion());
            txtTurno.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getTurno());
            cmbCodigoCargoEmpleado.getSelectionModel().select(buscarCargoEmpleado(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
        } else {
            JOptionPane.showMessageDialog(null, "Seleccionar fila con datos", "Seleccionar Empleado", JOptionPane.ERROR_MESSAGE);
            tblEmpleados.getSelectionModel().clearSelection();
        }
    }

    //METODO ACTIVAR LLENADO CONTROLES TXTS
    public void activarControls() {
        txtCodigoEmpleado.setEditable(true);
        txtNombresEmpleado.setEditable(true);
        txtApellidosEmpleado.setEditable(true);
        txtSueldo.setEditable(true);
        txtDireccion.setEditable(true);
        txtTurno.setEditable(true);
        cmbCodigoCargoEmpleado.setDisable(false);
    }

    //METODO DESACTIVAR LLENADO CONTROLES TXTS
    public void desactivarControls() {
        txtCodigoEmpleado.setEditable(false);
        txtNombresEmpleado.setEditable(false);
        txtApellidosEmpleado.setEditable(false);
        txtSueldo.setEditable(false);
        txtDireccion.setEditable(false);
        txtTurno.setEditable(false);
        cmbCodigoCargoEmpleado.setDisable(true);
    }

    //METODO LIMPIAR CONTROLES TXTS DE LLENADO
    public void limpiarControls() {
        txtCodigoEmpleado.clear();
        txtNombresEmpleado.clear();
        txtApellidosEmpleado.clear();
        txtSueldo.clear();
        txtDireccion.clear();
        txtTurno.clear();
        cmbCodigoCargoEmpleado.setValue(null);
    }

    @FXML
    public void ButtonsActionsMenuClientes(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}//TABULAR TODO CON ALT SHIFT + F

