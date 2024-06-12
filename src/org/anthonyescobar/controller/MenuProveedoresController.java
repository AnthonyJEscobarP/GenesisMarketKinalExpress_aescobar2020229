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
import org.anthonyescobar.bean.Proveedores;
import org.anthonyescobar.db.Conexion;
import org.anthonyescobar.report.GenerarReportes;
import org.anthonyescobar.system.Main;

/**
 *
 * @author Anthony Josue Escobar Ponce
 */
public class MenuProveedoresController implements Initializable {

    // -------------------------------------------- //
    private Main escenarioPrincipal;
    private ObservableList<Proveedores> listadoProveedores;

    private enum operaciones {

        NULO, ACTUALIZAR, ELIMINAR
    }
    private operaciones tipoDeOperaciones = operaciones.NULO;
    // -------------------------------------------- //
    @FXML
    private TableView tblProveedores;
    @FXML
    private TableColumn colCodigoProveedor;
    @FXML
    private TableColumn colNitProveedor;
    @FXML
    private TableColumn colNombreProveedor;
    @FXML
    private TableColumn colApellidosProveedor;
    @FXML
    private TableColumn colDireccionProveedor;
    @FXML
    private TableColumn colTelefonoProveedor;
    @FXML
    private TableColumn colEmailProveedor;
    @FXML
    private TableColumn colRazonSocial;
    @FXML
    private TableColumn colContactoPrincipal;
    @FXML
    private TableColumn colPaginaWeb;
    // -------------------------------------------- //
    @FXML
    private TextField txtCodigoProveedor;
    @FXML
    private TextField txtNitProveedor;
    @FXML
    private TextField txtNombreProveedor;
    @FXML
    private TextField txtApellidosProveedor;
    @FXML
    private TextField txtDireccionProveedor;
    @FXML
    private TextField txtTelefonoProveedor;
    @FXML
    private TextField txtEmailProveedor;
    @FXML
    private TextField txtRazonSocial;
    @FXML
    private TextField txtContactoPrincipal;
    @FXML
    private TextField txtPaginaWeb;
    // -------------------------------------------- //
    @FXML
    private Button btnAgregarPrv;
    @FXML
    private Button btnEditarPrv;
    @FXML
    private Button btnEliminarPrv;
    @FXML
    private Button btnReportePrv;
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
        tblProveedores.setItems(mostrarProveedores());
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNitProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nitProveedor"));
        colNombreProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombreProveedor"));
        colApellidosProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidosProveedor"));
        colDireccionProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colTelefonoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("telefonoProveedor"));
        colEmailProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("emailProveedor"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
        colContactoPrincipal.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
        colPaginaWeb.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
    }

    //METODO LISTAR 
    public ObservableList<Proveedores> mostrarProveedores() {
        ArrayList<Proveedores> listadoPrv = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listadoPrv.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("nitProveedor"),
                        resultado.getString("nombreProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("telefonoProveedor"),
                        resultado.getString("emailProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")));
            }//WHILE
        } catch (Exception e) {
            e.printStackTrace();
        }//CATCH TRY
        return listadoProveedores = FXCollections.observableArrayList(listadoPrv);
    } //METODO

    public void agregarProveedor() {
        switch (tipoDeOperaciones) {
            case NULO:
                limpiarControls();
                tblProveedores.setDisable(false);//////////////////////////////////
                activarControls();
                btnAgregarPrv.setText("Guardar");
                btnEliminarPrv.setText("Cancelar");
                btnEditarPrv.setDisable(true);
                btnReportePrv.setDisable(true);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardarInformacion();
                cargarDatos();
                desactivarControls();
                limpiarControls();
                btnAgregarPrv.setText("Agregar");
                btnEliminarPrv.setText("Eliminar");
                btnEditarPrv.setDisable(false);
                btnReportePrv.setDisable(false);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoEliminar.png"));
                tipoDeOperaciones = operaciones.NULO;
                break;
        }//SWITVH
        tblProveedores.setDisable(true);
    }//METODO

    public void eliminarProveedor() {
        tblProveedores.setDisable(false);
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControls();
                limpiarControls();
                btnAgregarPrv.setText("Agregar");
                btnEliminarPrv.setText("Eliminar");
                btnEditarPrv.setDisable(false);
                btnReportePrv.setDisable(false);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoEliminar.png"));
                tipoDeOperaciones = operaciones.NULO;
                break;
            default:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar al proveedor?", "Eliminar Proveedor",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarProveedor(?)}");
                            procedimiento.setInt(1, ((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listadoProveedores.remove(tblProveedores.getSelectionModel().getSelectedItem());
                            limpiarControls();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }//CATCH & TRY
                    } else {
                        tblProveedores.getSelectionModel().clearSelection();
                        limpiarControls();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila con datos", "Seleccionar el Proveedor a eliminar porfavor", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }//SWITCH
    }//METODO

    public void editarProveedor() {
        switch (tipoDeOperaciones) {
            case NULO:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    btnEditarPrv.setText("Actualizar");
                    btnReportePrv.setText("Cancelar");
                    //tipoDeOperaciones = operaciones.NULO;
                    btnAgregarPrv.setDisable(true);
                    btnEliminarPrv.setDisable(true);
                    imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoActualizar.png"));
                    imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                    activarControls();
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila con datos", "Seleccionar Proveedor Gracias :)",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case ACTUALIZAR:
                actualizarInformacion();
                btnEditarPrv.setText("Editar");
                btnReportePrv.setText("Reporte");
                btnAgregarPrv.setDisable(false);
                btnEliminarPrv.setDisable(false);
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
         parametros.put("codigoProveedor", null);        
         GenerarReportes.mostrarReportes("ReporteProveedores.jasper", "Reporte de proveedores", parametros);            
     }
    
    public void reportesProveedor() {
        switch (tipoDeOperaciones) {
            case NULO:
                imprimirReporte();
                break;
            case ACTUALIZAR:
                desactivarControls();
                limpiarControls();
                btnEditarPrv.setText("Editar");
                btnReportePrv.setText("Reporte");
                btnAgregarPrv.setDisable(false);
                btnEliminarPrv.setDisable(false);
                imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoEditar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoListar.png"));
                tipoDeOperaciones = operaciones.NULO;
                break;

        }//SWITVH
    }//METODO

    public void actualizarInformacion() {
        try {
            if (txtCodigoProveedor.getText().equals("") || txtNitProveedor.getText().equals("") || txtNombreProveedor.getText().equals("")
                || txtApellidosProveedor.getText().equals("") || txtDireccionProveedor.getText().equals("") || txtRazonSocial.getText().equals("")
                || txtContactoPrincipal.getText().equals("")|| txtPaginaWeb.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos porfavor", "Ingrese su informacion", JOptionPane.ERROR_MESSAGE);
            } else {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarProveedor(?,?,?,?,?,?,?,?)}");
                Proveedores proveedorGuardar = (Proveedores) tblProveedores.getSelectionModel().getSelectedItem();
                proveedorGuardar.setCodigoProveedor(Integer.parseInt(txtCodigoProveedor.getText()));
                proveedorGuardar.setNitProveedor(txtNitProveedor.getText());
                proveedorGuardar.setNombreProveedor(txtNombreProveedor.getText());
                proveedorGuardar.setApellidosProveedor(txtApellidosProveedor.getText());
                proveedorGuardar.setDireccionProveedor(txtDireccionProveedor.getText());
                proveedorGuardar.setRazonSocial(txtRazonSocial.getText());
                proveedorGuardar.setContactoPrincipal(txtContactoPrincipal.getText());
                proveedorGuardar.setPaginaWeb(txtPaginaWeb.getText());
                // -------------------------------------------------------------- //
                procedimiento.setInt(1, proveedorGuardar.getCodigoProveedor());
                procedimiento.setString(2, proveedorGuardar.getNitProveedor());
                procedimiento.setString(3, proveedorGuardar.getNombreProveedor());
                procedimiento.setString(4, proveedorGuardar.getApellidosProveedor());
                procedimiento.setString(5, proveedorGuardar.getDireccionProveedor());
                procedimiento.setString(6, proveedorGuardar.getRazonSocial());
                procedimiento.setString(7, proveedorGuardar.getContactoPrincipal());
                procedimiento.setString(8, proveedorGuardar.getPaginaWeb());
                procedimiento.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//METODO

    public void guardarInformacion() {
        if (txtCodigoProveedor.getText().equals("") || txtNitProveedor.getText().equals("") || txtNombreProveedor.getText().equals("")
                || txtApellidosProveedor.getText().equals("") || txtDireccionProveedor.getText().equals("") || txtRazonSocial.getText().equals("")
                || txtContactoPrincipal.getText().equals("")|| txtPaginaWeb.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos porfavor", "Ingrese su informacion", JOptionPane.ERROR_MESSAGE);
        } else {
            Proveedores proveedorGuardar = new Proveedores();
            proveedorGuardar.setCodigoProveedor(Integer.parseInt(txtCodigoProveedor.getText()));
            proveedorGuardar.setNitProveedor(txtNitProveedor.getText());
            proveedorGuardar.setNombreProveedor(txtNombreProveedor.getText());
            proveedorGuardar.setApellidosProveedor(txtApellidosProveedor.getText());
            proveedorGuardar.setDireccionProveedor(txtDireccionProveedor.getText());
            proveedorGuardar.setRazonSocial(txtRazonSocial.getText());
            proveedorGuardar.setContactoPrincipal(txtContactoPrincipal.getText());
            proveedorGuardar.setPaginaWeb(txtPaginaWeb.getText());
            try {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarProveedor(?,?,?,?,?,?,?,?)}");
                procedimiento.setInt(1, proveedorGuardar.getCodigoProveedor());
                procedimiento.setString(2, proveedorGuardar.getNitProveedor());
                procedimiento.setString(3, proveedorGuardar.getNombreProveedor());
                procedimiento.setString(4, proveedorGuardar.getApellidosProveedor());
                procedimiento.setString(5, proveedorGuardar.getDireccionProveedor());
                procedimiento.setString(6, proveedorGuardar.getRazonSocial());
                procedimiento.setString(7, proveedorGuardar.getContactoPrincipal());
                procedimiento.setString(8, proveedorGuardar.getPaginaWeb());
                procedimiento.execute();
                listadoProveedores.add(proveedorGuardar);
            } catch (Exception e) {
                e.printStackTrace();
            }//TRY CATCH
        }
    }//METODO

    public void seleccionarFila() {
        if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
            txtCodigoProveedor.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
            txtNitProveedor.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNitProveedor());
            txtNombreProveedor.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNombreProveedor());
            txtApellidosProveedor.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getApellidosProveedor());
            txtDireccionProveedor.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor());
            txtRazonSocial.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocial());
            txtContactoPrincipal.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal());
            txtPaginaWeb.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb());
        } else {
            JOptionPane.showMessageDialog(null, "Seleccionar fila con datos", "Seleccionar Cliente", JOptionPane.ERROR_MESSAGE);
            tblProveedores.getSelectionModel().clearSelection();
        }
    }

    //METODO ACTIVAR LLENADO CONTROLES TXTS
    public void activarControls() {
        txtCodigoProveedor.setEditable(true);
        txtNitProveedor.setEditable(true);
        txtNombreProveedor.setEditable(true);
        txtApellidosProveedor.setEditable(true);
        txtDireccionProveedor.setEditable(true);
        txtTelefonoProveedor.setEditable(true);
        txtEmailProveedor.setEditable(true);
        txtRazonSocial.setEditable(true);
        txtContactoPrincipal.setEditable(true);
        txtPaginaWeb.setEditable(true);
    }

    //METODO DESACTIVAR LLENADO CONTROLES TXTS
    public void desactivarControls() {
        txtCodigoProveedor.setEditable(false);
        txtNitProveedor.setEditable(false);
        txtNombreProveedor.setEditable(false);
        txtApellidosProveedor.setEditable(false);
        txtDireccionProveedor.setEditable(false);
        txtTelefonoProveedor.setEditable(false);
        txtEmailProveedor.setEditable(false);
        txtRazonSocial.setEditable(false);
        txtContactoPrincipal.setEditable(false);
        txtPaginaWeb.setEditable(false);
    }

    //METODO LIMPIAR CONTROLES TXTS DE LLENADO
    public void limpiarControls() {
        txtCodigoProveedor.clear();
        txtNitProveedor.clear();
        txtNombreProveedor.clear();
        txtApellidosProveedor.clear();
        txtDireccionProveedor.clear();
        txtTelefonoProveedor.clear();
        txtEmailProveedor.clear();
        txtRazonSocial.clear();
        txtContactoPrincipal.clear();
        txtPaginaWeb.clear();
    }

    @FXML
    public void ButtonsActionsMenuProveedores(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}//TABULAR TODO CON ALT SHIFT + F

