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
import org.anthonyescobar.bean.Compras;
import org.anthonyescobar.db.Conexion;
import org.anthonyescobar.system.Main;

/**
 *
 * @author Anthony Josue Escobar Ponce
 */
public class MenuComprasController implements Initializable {

    // -------------------------------------------- //
    private Main escenarioPrincipal;
    private ObservableList<Compras> listadoCompras;

    private enum operaciones {

        NULO, ACTUALIZAR, ELIMINAR
    }
    private operaciones tipoDeOperaciones = operaciones.NULO;
    // -------------------------------------------- //
    @FXML
    private TableView tblCompras;
    @FXML
    private TableColumn colNumeroDocumento;
    @FXML
    private TableColumn colFechaDocumento;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colTotalDocumento;
    // -------------------------------------------- //
    @FXML
    private TextField txtNumeroDocumento;
    @FXML
    private TextField txtFechaDocumento;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtTotalDocumento;
    // -------------------------------------------- //
    @FXML
    private Button btnAgregarCmp;
    @FXML
    private Button btnEditarCmp;
    @FXML
    private Button btnEliminarCmp;
    @FXML
    private Button btnReporteCmp;
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
        tblCompras.setItems(mostrarCompras());
        colNumeroDocumento.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colFechaDocumento.setCellValueFactory(new PropertyValueFactory<Compras, String>("fechaDocumento"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Compras, String>("descripcion"));
        colTotalDocumento.setCellValueFactory(new PropertyValueFactory<Compras, String>("totalDocumento"));
    }

    //METODO LISTAR 
    public ObservableList<Compras> mostrarCompras() {
        ArrayList<Compras> listadoCmp = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listadoCmp.add(new Compras(resultado.getInt("numeroDocumento"),
                        resultado.getDate("fechaDocumento"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("totalDocumento")));
            }//WHILE
        } catch (Exception e) {
            e.printStackTrace();
        }//CATCH TRY
        return listadoCompras = FXCollections.observableArrayList(listadoCmp);
    } //METODO

    public void agregarCompra() {
        switch (tipoDeOperaciones) {
            case NULO:
                limpiarControls();
                tblCompras.setDisable(false);//////////////////////////////////
                activarControls();
                btnAgregarCmp.setText("Guardar");
                btnEliminarCmp.setText("Cancelar");
                btnEditarCmp.setDisable(true);
                btnReporteCmp.setDisable(true);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardarInformacion();
                cargarDatos();
                desactivarControls();
                limpiarControls();
                btnAgregarCmp.setText("Agregar");
                btnEliminarCmp.setText("Eliminar");
                btnEditarCmp.setDisable(false);
                btnReporteCmp.setDisable(false);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoEliminar.png"));
                tipoDeOperaciones = operaciones.NULO;

                break;
        }//SWITVH
        tblCompras.setDisable(true);//////////////////////////////////
    }//METODO

    public void eliminarCompra() {
        tblCompras.setDisable(false);//////////////////////////////////
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControls();
                limpiarControls();
                btnAgregarCmp.setText("Agregar");
                btnEliminarCmp.setText("Eliminar");
                btnEditarCmp.setDisable(false);
                btnReporteCmp.setDisable(false);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoEliminar.png"));
                tipoDeOperaciones = operaciones.NULO;
                break;
            default:
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar la compra?", "Eliminar compra",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCompra(?)}");
                            procedimiento.setInt(1, ((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                            procedimiento.execute();
                            listadoCompras.remove(tblCompras.getSelectionModel().getSelectedItem());
                            limpiarControls();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }//CATCH & TRY
                    } else {
                        tblCompras.getSelectionModel().clearSelection();
                        limpiarControls();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila con datos", "Seleccionar la compra a eliminar porfavor", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }//SWITCH
    }//METODO

    public void editarCompra() {
        switch (tipoDeOperaciones) {
            case NULO:
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    btnEditarCmp.setText("Actualizar");
                    btnReporteCmp.setText("Cancelar");
                    btnAgregarCmp.setDisable(true);
                    btnEliminarCmp.setDisable(true);
                    imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoActualizar.png"));
                    imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                    activarControls();
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila con datos", "Seleccionar la compra Gracias :)",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case ACTUALIZAR:
                actualizarInformacion();
                btnEditarCmp.setText("Editar");
                btnReporteCmp.setText("Reporte");
                btnAgregarCmp.setDisable(false);
                btnEliminarCmp.setDisable(false);
                desactivarControls();
                limpiarControls();
                imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoEditar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoListar.png"));
                tipoDeOperaciones = operaciones.NULO;
                cargarDatos();
                break;
        }
    }

    public void reportesCancelarCompra() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControls();
                limpiarControls();
                btnEditarCmp.setText("Editar");
                btnReporteCmp.setText("Reporte");
                btnAgregarCmp.setDisable(false);
                btnEliminarCmp.setDisable(false);
                imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoEditar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoListar.png"));
                tipoDeOperaciones = operaciones.NULO;
                break;

        }//SWITVH
    }//METODO

    public void actualizarInformacion() {
        try {
            if (txtNumeroDocumento.getText().equals("") || txtFechaDocumento.getText().equals("")
                || txtDescripcion.getText().equals("") || txtTotalDocumento.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos porfavor", "Ingrese su informacion", JOptionPane.ERROR_MESSAGE);
            } else {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarCompra(?,?,?,?)}");
                Compras comprasGuardar = (Compras) tblCompras.getSelectionModel().getSelectedItem();
                comprasGuardar.setNumeroDocumento(Integer.parseInt(txtNumeroDocumento.getText()));
                comprasGuardar.setFechaDocumento(Date.valueOf(txtFechaDocumento.getText()));
                comprasGuardar.setDescripcion(txtDescripcion.getText());
                comprasGuardar.setTotalDocumento(Double.parseDouble(txtTotalDocumento.getText()));
                // -------------------------------------------------------------- //
                procedimiento.setInt(1, comprasGuardar.getNumeroDocumento());
                procedimiento.setDate(2, comprasGuardar.getFechaDocumento());
                procedimiento.setString(3, comprasGuardar.getDescripcion());
                procedimiento.setDouble(4, comprasGuardar.getTotalDocumento());
                procedimiento.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//METODO

    public void guardarInformacion() {
        if (txtNumeroDocumento.getText().equals("") || txtFechaDocumento.getText().equals("")
                || txtDescripcion.getText().equals("") || txtTotalDocumento.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos porfavor", "Ingrese su informacion", JOptionPane.ERROR_MESSAGE);
        } else {
            Compras comprasGuardar = (Compras) tblCompras.getSelectionModel().getSelectedItem();
            comprasGuardar.setNumeroDocumento(Integer.parseInt(txtNumeroDocumento.getText()));
            comprasGuardar.setFechaDocumento(Date.valueOf(txtFechaDocumento.getText()));
            comprasGuardar.setDescripcion(txtDescripcion.getText());
            comprasGuardar.setTotalDocumento(Double.parseDouble(txtTotalDocumento.getText()));
            try {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarCompra(?,?,?,?)}");
                procedimiento.setInt(1, comprasGuardar.getNumeroDocumento());
                procedimiento.setDate(2, comprasGuardar.getFechaDocumento());
                procedimiento.setString(3, comprasGuardar.getDescripcion());
                procedimiento.setDouble(4, comprasGuardar.getTotalDocumento());
                procedimiento.execute();
                listadoCompras.add(comprasGuardar);
            } catch (Exception e) {
                e.printStackTrace();
            }//TRY CATCH
        }
    }//METODO

    public void seleccionarFila() {
        if (tblCompras.getSelectionModel().getSelectedItem() != null) {
            txtNumeroDocumento.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
            txtFechaDocumento.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getFechaDocumento()));
            txtDescripcion.setText(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getDescripcion());
            txtTotalDocumento.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getTotalDocumento()));
        } else {
            JOptionPane.showMessageDialog(null, "Seleccionar fila con datos", "Seleccionar la compra", JOptionPane.ERROR_MESSAGE);
            tblCompras.getSelectionModel().clearSelection();
        }
    }

    //METODO ACTIVAR LLENADO CONTROLES TXTS
    public void activarControls() {
        txtNumeroDocumento.setEditable(true);
        txtFechaDocumento.setEditable(true);
        txtDescripcion.setEditable(true);
        txtTotalDocumento.setEditable(true);
    }

    //METODO DESACTIVAR LLENADO CONTROLES TXTS
    public void desactivarControls() {
        txtNumeroDocumento.setEditable(false);
        txtFechaDocumento.setEditable(false);
        txtDescripcion.setEditable(false);
        txtTotalDocumento.setEditable(false);
    }

    //METODO LIMPIAR CONTROLES TXTS DE LLENADO
    public void limpiarControls() {
        txtNumeroDocumento.clear();
        txtFechaDocumento.clear();
        txtDescripcion.clear();
        txtTotalDocumento.clear();
    }

    @FXML
    public void ButtonsActionsMenuCompras(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}//TABULAR TODO CON ALT SHIFT + F

