package org.anthonyescobar.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.anthonyescobar.bean.CargoEmpleado;
import org.anthonyescobar.db.Conexion;
import org.anthonyescobar.system.Main;

/**
 *
 * @author Anthony Josue Escobar Ponce
 */
public class MenuCargoEmpleadoController implements Initializable {

    // -------------------------------------------- //
    private Main escenarioPrincipal;
    private ObservableList<CargoEmpleado> listadoCargoEmpleado;

    private enum operaciones {

        NULO, ACTUALIZAR, ELIMINAR
    }
    private operaciones tipoDeOperaciones = operaciones.NULO;
    // -------------------------------------------- //
    @FXML
    private TableView tblCargoEmpleado;
    @FXML
    private TableColumn colCodigoCargoEmpleado;
    @FXML
    private TableColumn colNombreCargo;
    @FXML
    private TableColumn colDescripcionCargo;
    // -------------------------------------------- //
    @FXML
    private TextField txtCodigoCargoEmpleado;
    @FXML
    private TextField txtNombreCargo;
    @FXML
    private TextField txtDescripcionCargo;
    // -------------------------------------------- //
    @FXML
    private Button btnAgregarCrgEmp;
    @FXML
    private Button btnEditarCrgEmp;
    @FXML
    private Button btnEliminarCrgEmp;
    @FXML
    private Button btnReporteCrgEmp;
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
        tblCargoEmpleado.setItems(mostrarCargoEmpleados());
        colCodigoCargoEmpleado.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, Integer>("codigoCargoEmpleado"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("nombreCargo"));
        colDescripcionCargo.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("descripcionCargo"));
    }

    //METODO LISTAR 
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

    public void agregarCargoEmpleado() {
        switch (tipoDeOperaciones) {
            case NULO:
                limpiarControls();
                tblCargoEmpleado.setDisable(false);//////////////////////////////////
                activarControls();
                btnAgregarCrgEmp.setText("Guardar");
                btnEliminarCrgEmp.setText("Cancelar");
                btnEditarCrgEmp.setDisable(true);
                btnReporteCrgEmp.setDisable(true);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardarInformacion();
                cargarDatos();
                desactivarControls();
                limpiarControls();
                btnAgregarCrgEmp.setText("Agregar");
                btnEliminarCrgEmp.setText("Eliminar");
                btnEditarCrgEmp.setDisable(false);
                btnReporteCrgEmp.setDisable(false);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoEliminar.png"));
                tipoDeOperaciones = operaciones.NULO;
                break;
        }//SWITVH
        tblCargoEmpleado.setDisable(true);
    }//METODO

    public void eliminarCargoEmpleado() {
        tblCargoEmpleado.setDisable(false);
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControls();
                limpiarControls();
                btnAgregarCrgEmp.setText("Agregar");
                btnEliminarCrgEmp.setText("Eliminar");
                btnEditarCrgEmp.setDisable(false);
                btnReporteCrgEmp.setDisable(false);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoEliminar.png"));
                tipoDeOperaciones = operaciones.NULO;
                break;
            default:
                if (tblCargoEmpleado.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar el Cargo de Empleado?", "Eliminar Cargo de Empleado",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCargoEmpleado(?)}");
                            procedimiento.setInt(1, ((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
                            procedimiento.execute();
                            listadoCargoEmpleado.remove(tblCargoEmpleado.getSelectionModel().getSelectedItem());
                            limpiarControls();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }//CATCH & TRY
                    } else {
                        tblCargoEmpleado.getSelectionModel().clearSelection();
                        limpiarControls();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila con datos", "Seleccionar el Cargo de Empleado a eliminar porfavor", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }//SWITCH
    }//METODO

    public void editarCargoEmpleado() {
        switch (tipoDeOperaciones) {
            case NULO:
                if (tblCargoEmpleado.getSelectionModel().getSelectedItem() != null) {
                    btnEditarCrgEmp.setText("Actualizar");
                    btnReporteCrgEmp.setText("Cancelar");
                    btnAgregarCrgEmp.setDisable(true);
                    btnEliminarCrgEmp.setDisable(true);
                    imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoActualizar.png"));
                    imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                    activarControls();
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila con datos", "Seleccionar el Cargo de Empleado, Gracias :)",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case ACTUALIZAR:
                actualizarInformacion();
                btnEditarCrgEmp.setText("Editar");
                btnReporteCrgEmp.setText("Reporte");
                btnAgregarCrgEmp.setDisable(false);
                btnEliminarCrgEmp.setDisable(false);
                desactivarControls();
                limpiarControls();
                imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoEditar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoListar.png"));
                tipoDeOperaciones = operaciones.NULO;
                cargarDatos();
                break;
        }
    }

    public void reportesCancelarCargoEmpleado() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControls();
                limpiarControls();
                btnEditarCrgEmp.setText("Editar");
                btnReporteCrgEmp.setText("Reporte");
                btnAgregarCrgEmp.setDisable(false);
                btnEliminarCrgEmp.setDisable(false);
                imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoEditar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoListar.png"));
                tipoDeOperaciones = operaciones.NULO;
                break;

        }//SWITVH
    }//METODO

    public void actualizarInformacion() {
        try {
            if (txtCodigoCargoEmpleado.getText().equals("") || txtNombreCargo.getText().equals("")
                || txtDescripcionCargo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos porfavor", "Ingrese su informacion", JOptionPane.ERROR_MESSAGE);
            } else {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarCargoEmpleado(?,?,?)}");
                CargoEmpleado cargoEmpleadoGuardar = (CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem();
                cargoEmpleadoGuardar.setCodigoCargoEmpleado(Integer.parseInt(txtCodigoCargoEmpleado.getText()));
                cargoEmpleadoGuardar.setNombreCargo(txtNombreCargo.getText());
                cargoEmpleadoGuardar.setDescripcionCargo(txtDescripcionCargo.getText());
                // -------------------------------------------------------------- //
                procedimiento.setInt(1, cargoEmpleadoGuardar.getCodigoCargoEmpleado());
                procedimiento.setString(2, cargoEmpleadoGuardar.getNombreCargo());
                procedimiento.setString(3, cargoEmpleadoGuardar.getDescripcionCargo());
                procedimiento.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//METODO

    public void guardarInformacion() {
        if (txtCodigoCargoEmpleado.getText().equals("") || txtNombreCargo.getText().equals("")
                || txtDescripcionCargo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos porfavor", "Ingrese su informacion", JOptionPane.ERROR_MESSAGE);
        } else {
            CargoEmpleado cargoEmpleadoGuardar = (CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem();
            cargoEmpleadoGuardar.setCodigoCargoEmpleado(Integer.parseInt(txtCodigoCargoEmpleado.getText()));
            cargoEmpleadoGuardar.setNombreCargo(txtNombreCargo.getText());
            cargoEmpleadoGuardar.setDescripcionCargo(txtDescripcionCargo.getText());
            try {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarCargoEmpleado(?,?,?)}");
                procedimiento.setInt(1, cargoEmpleadoGuardar.getCodigoCargoEmpleado());
                procedimiento.setString(2, cargoEmpleadoGuardar.getNombreCargo());
                procedimiento.setString(3, cargoEmpleadoGuardar.getDescripcionCargo());
                procedimiento.execute();
                listadoCargoEmpleado.add(cargoEmpleadoGuardar);
            } catch (Exception e) {
                e.printStackTrace();
            }//TRY CATCH
        }
    }//METODO

    public void seleccionarFila() {
        if (tblCargoEmpleado.getSelectionModel().getSelectedItem() != null) {
            txtCodigoCargoEmpleado.setText(String.valueOf(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
            txtNombreCargo.setText(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getNombreCargo());
            txtDescripcionCargo.setText(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getDescripcionCargo());
        } else {
            JOptionPane.showMessageDialog(null, "Seleccionar fila con datos", "Seleccionar el cargo de empleado", JOptionPane.ERROR_MESSAGE);
            tblCargoEmpleado.getSelectionModel().clearSelection();
        }
    }

    //METODO ACTIVAR LLENADO CONTROLES TXTS
    public void activarControls() {
        txtCodigoCargoEmpleado.setEditable(true);
        txtNombreCargo.setEditable(true);
        txtDescripcionCargo.setEditable(true);
    }

    //METODO DESACTIVAR LLENADO CONTROLES TXTS
    public void desactivarControls() {
        txtCodigoCargoEmpleado.setEditable(false);
        txtNombreCargo.setEditable(false);
        txtDescripcionCargo.setEditable(false);
    }

    //METODO LIMPIAR CONTROLES TXTS DE LLENADO
    public void limpiarControls() {
        txtCodigoCargoEmpleado.clear();
        txtNombreCargo.clear();
        txtDescripcionCargo.clear();
    }

    @FXML
    public void ButtonsActionsMenuCargoEmpleado(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}//TABULAR TODO CON ALT SHIFT + F

