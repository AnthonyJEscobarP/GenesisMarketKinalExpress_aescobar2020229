package org.anthonyescobar.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import org.anthonyescobar.bean.TipoProducto;
import org.anthonyescobar.db.Conexion;
import org.anthonyescobar.system.Main;

/**
 *
 * @author Anthony Josue Escobar Ponce
 */
public class MenuTipoProductoController implements Initializable {

    // -------------------------------------------- //
    private Main escenarioPrincipal;
    private ObservableList<TipoProducto> listadoTipoProducto;

    private enum operaciones {

        NULO, ACTUALIZAR, ELIMINAR
    }
    private operaciones tipoDeOperaciones = operaciones.NULO;
    // -------------------------------------------- //
    @FXML
    private TableView tblTipoProducto;
    @FXML
    private TableColumn colCodigoTipoProducto;
    @FXML
    private TableColumn colDescripcion;
    // -------------------------------------------- //
    @FXML
    private TextField txtCodigoTipoProducto;
    @FXML
    private TextField txtDescripcion;
    // -------------------------------------------- //
    @FXML
    private Button btnAgregarTp;
    @FXML
    private Button btnEditarTp;
    @FXML
    private Button btnEliminarTp;
    @FXML
    private Button btnReporteTp;
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
        tblTipoProducto.setItems(mostrarTipoProducto());
        colCodigoTipoProducto.setCellValueFactory(new PropertyValueFactory<TipoProducto, Integer>("codigoTipoProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoProducto, String>("descripcion"));
    }

    //METODO LISTAR 
    public ObservableList<TipoProducto> mostrarTipoProducto() {
        ArrayList<TipoProducto> listadoTipoPrdct = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarTiposProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listadoTipoPrdct.add(new TipoProducto(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcion")));
            }//WHILE
        } catch (Exception e) {
            e.printStackTrace();
        }//CATCH TRY
        return listadoTipoProducto = FXCollections.observableArrayList(listadoTipoPrdct);
    } //METODO

    public void agregarTipoProducto() {
        switch (tipoDeOperaciones) {
            case NULO:
                limpiarControls();
                tblTipoProducto.setDisable(false);//////////////////////////////////
                activarControls();
                btnAgregarTp.setText("Guardar");
                btnEliminarTp.setText("Cancelar");
                btnEditarTp.setDisable(true);
                btnReporteTp.setDisable(true);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardarInformacion();
                cargarDatos();
                desactivarControls();
                limpiarControls();
                btnAgregarTp.setText("Agregar");
                btnEliminarTp.setText("Eliminar");
                btnEditarTp.setDisable(false);
                btnReporteTp.setDisable(false);
                //imgAgregar.setImage(new Image("/org/anthonyescobar/images/iconoAgregar.png"));
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoEliminar.png"));
                tipoDeOperaciones = operaciones.NULO;

                break;
        }//SWITVH
        tblTipoProducto.setDisable(true);///////////////////////////////////////
    }//METODO

    public void eliminarTipoProducto() {
        tblTipoProducto.setDisable(false);
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControls();
                limpiarControls();
                btnAgregarTp.setText("Agregar");
                btnEliminarTp.setText("Eliminar");
                btnEditarTp.setDisable(false);
                btnReporteTp.setDisable(false);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoEliminar.png"));
                tipoDeOperaciones = operaciones.NULO;
                break;
            default:
                if (tblTipoProducto.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar el Tipo de Producto?", "Eliminar Tipo de Producto",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarTipoProducto(?)}");
                            procedimiento.setInt(1, ((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
                            procedimiento.execute();
                            listadoTipoProducto.remove(tblTipoProducto.getSelectionModel().getSelectedItem());
                            limpiarControls();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }//CATCH & TRY
                    } else {
                        tblTipoProducto.getSelectionModel().clearSelection();
                        limpiarControls();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila con datos", "Seleccionar el Tipo de Producto a eliminar porfavor", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }//SWITCH
    }//METODO

    public void editarTipoProducto() {
        switch (tipoDeOperaciones) {
            case NULO:
                if (tblTipoProducto.getSelectionModel().getSelectedItem() != null) {
                    btnEditarTp.setText("Actualizar");
                    btnReporteTp.setText("Cancelar");
                    //tipoDeOperaciones = operaciones.NULO;
                    btnAgregarTp.setDisable(true);
                    btnEliminarTp.setDisable(true);
                    imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoActualizar.png"));
                    imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                    activarControls();
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila con datos", "Seleccionar el Tipo de Producto Gracias :)",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case ACTUALIZAR:
                actualizarInformacion();
                btnEditarTp.setText("Editar");
                btnReporteTp.setText("Reporte");
                btnAgregarTp.setDisable(false);
                btnEliminarTp.setDisable(false);
                desactivarControls();
                limpiarControls();
                imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoEditar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoListar.png"));
                tipoDeOperaciones = operaciones.NULO;
                cargarDatos();
                break;
        }
    }

    public void reportesTipoProducto() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControls();
                limpiarControls();
                btnEditarTp.setText("Editar");
                btnReporteTp.setText("Reporte");
                btnAgregarTp.setDisable(false);
                btnEliminarTp.setDisable(false);
                imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoEditar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoListar.png"));
                tipoDeOperaciones = operaciones.NULO;
                break;

        }//SWITVH
    }//METODO

    public void actualizarInformacion() {
        try {
            if (txtCodigoTipoProducto.getText().equals("") || txtDescripcion.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos porfavor", "Ingrese su informacion", JOptionPane.ERROR_MESSAGE);
            } else {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarTipoProducto(?,?)}");
                TipoProducto tipoProductoGuardar = (TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem();
                tipoProductoGuardar.setCodigoTipoProducto(Integer.parseInt(txtCodigoTipoProducto.getText()));
                tipoProductoGuardar.setDescripcion(txtDescripcion.getText());
                // -------------------------------------------------------------- //
                procedimiento.setInt(1, tipoProductoGuardar.getCodigoTipoProducto());
                procedimiento.setString(2, tipoProductoGuardar.getDescripcion());
                procedimiento.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//METODO

    public void guardarInformacion() {
        if (txtCodigoTipoProducto.getText().equals("") || txtDescripcion.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos porfavor", "Ingrese su informacion", JOptionPane.ERROR_MESSAGE);
        } else {
            TipoProducto tipoProductoGuardar = (TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem();
            tipoProductoGuardar.setCodigoTipoProducto(Integer.parseInt(txtCodigoTipoProducto.getText()));
            tipoProductoGuardar.setDescripcion(txtDescripcion.getText());
            try {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarTipoProducto(?,?)}");
                procedimiento.setInt(1, tipoProductoGuardar.getCodigoTipoProducto());
                procedimiento.setString(2, tipoProductoGuardar.getDescripcion());
                procedimiento.execute();
                listadoTipoProducto.add(tipoProductoGuardar);
            } catch (Exception e) {
                e.printStackTrace();
            }//TRY CATCH
        }
    }//METODO

    public void seleccionarFila() {
        if (tblTipoProducto.getSelectionModel().getSelectedItem() != null) {
            txtCodigoTipoProducto.setText(String.valueOf(((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
            txtDescripcion.setText(((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getDescripcion());
        } else {
            JOptionPane.showMessageDialog(null, "Seleccionar fila con datos", "Seleccionar el Tipo de Producto", JOptionPane.ERROR_MESSAGE);
            tblTipoProducto.getSelectionModel().clearSelection();
        }
    }

    //METODO ACTIVAR LLENADO CONTROLES TXTS
    public void activarControls() {
        txtCodigoTipoProducto.setEditable(true);
        txtDescripcion.setEditable(true);
    }

    //METODO DESACTIVAR LLENADO CONTROLES TXTS
    public void desactivarControls() {
        txtCodigoTipoProducto.setEditable(false);
        txtDescripcion.setEditable(false);
    }

    //METODO LIMPIAR CONTROLES TXTS DE LLENADO
    public void limpiarControls() {
        txtCodigoTipoProducto.clear();
        txtDescripcion.clear();
    }

    @FXML
    public void ButtonsActionsMenuTipoProducto(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}//TABULAR TODO CON ALT SHIFT + F

