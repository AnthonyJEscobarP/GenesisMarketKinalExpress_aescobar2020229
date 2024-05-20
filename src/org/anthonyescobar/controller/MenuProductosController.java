package org.anthonyescobar.controller;

import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.anthonyescobar.bean.Productos;
import org.anthonyescobar.bean.Proveedores;
import org.anthonyescobar.bean.TipoProducto;
import org.anthonyescobar.db.Conexion;
import org.anthonyescobar.system.Main;

/**
 *
 * @author Anthony Josue Escobar Ponce
 */
public class MenuProductosController implements Initializable {

    // -------------------------------------------- //
    private Main escenarioPrincipal;
    private ObservableList<Productos> listadoProductos;
    private ObservableList<TipoProducto> listadoTipoProducto;
    private ObservableList<Proveedores> listadoProveedores;

    private enum operaciones {

        NULO, ACTUALIZAR, ELIMINAR
    }
    private operaciones tipoDeOperaciones = operaciones.NULO;
    // -------------------------------------------- //
    @FXML
    private TableView tblProductos;
    @FXML
    private TableColumn colCodigoProducto;
    @FXML
    private TableColumn colDescripcionProducto;
    @FXML
    private TableColumn colPrecioUnitario;
    @FXML
    private TableColumn colPrecioDocena;
    @FXML
    private TableColumn colPrecioMayor;
    @FXML
    private TableColumn colExistencia;
    @FXML
    private TableColumn colCodigoTipoProducto;
    @FXML
    private TableColumn colCodigoProveedor;
    // -------------------------------------------- //
    @FXML
    private TextField txtCodigoProducto;
    @FXML
    private TextField txtDescripcionProducto;
    @FXML
    private TextField txtPrecioUnitario;
    @FXML
    private TextField txtPrecioDocena;
    @FXML
    private TextField txtPrecioMayor;
    @FXML
    private TextField txtExistencia;
    @FXML
    private ComboBox cmbCodigoTipoProducto;
    @FXML
    private ComboBox cmbCodigoProveedor;
    // -------------------------------------------- //
    @FXML
    private Button btnAgregarPrd;
    @FXML
    private Button btnEditarPrd;
    @FXML
    private Button btnEliminarPrd;
    @FXML
    private Button btnReportePrd;
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
        cmbCodigoTipoProducto.setItems(mostrarTipoProducto());
        cmbCodigoProveedor.setItems(mostrarProveedores());
    }

    //METODO CARGAR DATOS EN JAVA
    public void cargarDatos() {
        tblProductos.setItems(mostrarProductos());
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<Productos, String>("codigoProducto"));
        colDescripcionProducto.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioUnitario"));
        colPrecioDocena.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioDocena"));
        colPrecioMayor.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioMayor"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("existencia"));
        colCodigoTipoProducto.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoTipoProducto"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoProveedor"));
    }

    
    //METODO BUSCAR TIPOS DE PRODUCTOS
    public TipoProducto buscarTipoProducto(int codigoTipoProducto) {
        TipoProducto resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarTipoProducto(?)}");
            procedimiento.setInt(1, codigoTipoProducto);
            ResultSet tipoProductoEncontrado = procedimiento.executeQuery();
            while (tipoProductoEncontrado.next()) {
                resultado = new TipoProducto(tipoProductoEncontrado.getInt("codigoTipoProducto"),
                        tipoProductoEncontrado.getString("descripcionProducto"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }//METODO BUSCAR TIPOS DE PRODUCTOS

    //METODO BUSCAR PROVEEDOR
    public Proveedores buscarProveedor(int codigoProveedor) {
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarProveedor(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet proveedorEncontrado = procedimiento.executeQuery();
            while (proveedorEncontrado.next()) {
                resultado = new Proveedores(proveedorEncontrado.getInt("codigoProveedor"),
                        proveedorEncontrado.getString("nitProveedor"),
                        proveedorEncontrado.getString("nombreProveedor"),
                        proveedorEncontrado.getString("apellidosProveedor"),
                        proveedorEncontrado.getString("direccionProveedor"),
                        proveedorEncontrado.getString("razonSocial"),
                        proveedorEncontrado.getString("contactoPrincipal"),
                        proveedorEncontrado.getString("paginaWeb"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }//METODO BUSCAR PROVEEDOR
    
    
    
    
    
    //METODO MOSTRAR PRODUCTOS 
    public ObservableList<Productos> mostrarProductos() {
        ArrayList<Productos> listadoPrd = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listadoPrd.add(new Productos(resultado.getString("codigoProducto"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getDouble("precioDocena"),
                        resultado.getDouble("precioMayor"),
                        resultado.getInt("existencia"),
                        resultado.getInt("codigoTipoProducto"),
                        resultado.getInt("codigoProveedor")));
            }//WHILE
        } catch (Exception e) {
            e.printStackTrace();
        }//CATCH TRY
        return listadoProductos = FXCollections.observableArrayList(listadoPrd);
    } //METODO PARA MOSTRAR PRODUCTOS

    //MOSTRAR TIPO DE PRODUCTOS
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
    } //METODO PARA MOSTRAR TIPO DE PRODUCTOS

    //METODO MOSTRAR PROVEEDORES 
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
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")));
            }//WHILE
        } catch (Exception e) {
            e.printStackTrace();
        }//CATCH TRY
        return listadoProveedores = FXCollections.observableArrayList(listadoPrv);
    } //METODO PARA MOSTRAR PROVEEDORES

    public void agregarProducto() {
        switch (tipoDeOperaciones) {
            case NULO:
                activarControls();
                btnAgregarPrd.setText("Guardar");
                btnEliminarPrd.setText("Cancelar");
                btnEditarPrd.setDisable(true);
                btnReportePrd.setDisable(true);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardarInformacion();
                cargarDatos();
                desactivarControls();
                limpiarControls();
                btnAgregarPrd.setText("Agregar");
                btnEliminarPrd.setText("Eliminar");
                btnEditarPrd.setDisable(false);
                btnReportePrd.setDisable(false);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoEliminar.png"));
                tipoDeOperaciones = operaciones.NULO;

                break;
        }//SWITVH
    }//METODO

    public void eliminarProducto() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControls();
                limpiarControls();
                btnAgregarPrd.setText("Agregar");
                btnEliminarPrd.setText("Eliminar");
                btnEditarPrd.setDisable(false);
                btnReportePrd.setDisable(false);
                imgEliminar.setImage(new Image("/org/anthonyescobar/images/iconoEliminar.png"));
                tipoDeOperaciones = operaciones.NULO;
                break;
            default:
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar al Producto?", "Eliminar Producto",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarProducto(?)}");
                            procedimiento.setString(1, ((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
                            procedimiento.execute();
                            listadoProductos.remove(tblProductos.getSelectionModel().getSelectedItem());
                            limpiarControls();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }//CATCH & TRY
                    } else {
                        tblProductos.getSelectionModel().clearSelection();
                        limpiarControls();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila con datos", "Seleccionar el Producto a eliminar porfavor", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }//SWITCH
    }//METODO

    public void editarProducto() {
        switch (tipoDeOperaciones) {
            case NULO:
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    btnEditarPrd.setText("Actualizar");
                    btnReportePrd.setText("Cancelar");
                    btnAgregarPrd.setDisable(true);
                    btnEliminarPrd.setDisable(true);
                    imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoActualizar.png"));
                    imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                    activarControls();
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila con datos", "Seleccionar Producto Gracias :)",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case ACTUALIZAR:
                actualizarInformacion();
                btnEditarPrd.setText("Editar");
                btnReportePrd.setText("Reporte");
                btnAgregarPrd.setDisable(false);
                btnEliminarPrd.setDisable(false);
                desactivarControls();
                limpiarControls();
                imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoEditar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoListar.png"));
                tipoDeOperaciones = operaciones.NULO;
                cargarDatos();
                break;
        }
    }

    public void reportesProductos() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControls();
                limpiarControls();
                btnEditarPrd.setText("Editar");
                btnReportePrd.setText("Reporte");
                btnAgregarPrd.setDisable(false);
                btnEliminarPrd.setDisable(false);
                imgEditar.setImage(new Image("/org/anthonyescobar/images/iconoEditar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoCancelar.png"));
                imgReporte.setImage(new Image("/org/anthonyescobar/images/iconoListar.png"));
                tipoDeOperaciones = operaciones.NULO;
                break;

        }//SWITVH
    }//METODO

    public void actualizarInformacion() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarProducto(?,?,?,?,?,?,?,?)}");
            Productos productoGuardar = new Productos();
            productoGuardar.setCodigoProducto(txtCodigoProducto.getText());
            productoGuardar.setDescripcionProducto(txtDescripcionProducto.getText());
            productoGuardar.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
            productoGuardar.setPrecioDocena(Double.parseDouble(txtPrecioDocena.getText()));
            productoGuardar.setPrecioMayor(Double.parseDouble(txtPrecioMayor.getText()));
            productoGuardar.setExistencia(Integer.parseInt(txtExistencia.getText()));
            productoGuardar.setCodigoTipoProducto(((Proveedores) cmbCodigoTipoProducto.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            productoGuardar.setCodigoProveedor(((Proveedores) cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            // -------------------------------------------------------------- //
                procedimiento.setString(1, productoGuardar.getCodigoProducto());
                procedimiento.setString(2, productoGuardar.getDescripcionProducto());
                procedimiento.setDouble(3, productoGuardar.getPrecioUnitario());
                procedimiento.setDouble(4, productoGuardar.getPrecioDocena());
                procedimiento.setDouble(5, productoGuardar.getPrecioMayor());
                procedimiento.setInt(6, productoGuardar.getExistencia());
                procedimiento.setInt(7, productoGuardar.getCodigoTipoProducto());
                procedimiento.setInt(8, productoGuardar.getCodigoProveedor());
                procedimiento.execute();
                listadoProductos.add(productoGuardar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//METODO

    public void guardarInformacion() {
        if (txtCodigoProducto.getText().equals("") || txtDescripcionProducto.getText().equals("") || txtPrecioUnitario.getText().equals("")
                || txtPrecioDocena.getText().equals("") || txtPrecioMayor.getText().equals("") || txtExistencia.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos porfavor", "Ingrese su informacion", JOptionPane.ERROR_MESSAGE);
        } else {
            Productos productoGuardar = new Productos();
            productoGuardar.setCodigoProducto(txtCodigoProducto.getText());
            productoGuardar.setDescripcionProducto(txtDescripcionProducto.getText());
            productoGuardar.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
            productoGuardar.setPrecioDocena(Double.parseDouble(txtPrecioDocena.getText()));
            productoGuardar.setPrecioMayor(Double.parseDouble(txtPrecioMayor.getText()));
            productoGuardar.setExistencia(Integer.parseInt(txtExistencia.getText()));
            productoGuardar.setCodigoTipoProducto(((Proveedores) cmbCodigoTipoProducto.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            productoGuardar.setCodigoProveedor(((Proveedores) cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            try {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarProducto(?,?,?,?,?,?,?,?)}");
                procedimiento.setString(1, productoGuardar.getCodigoProducto());
                procedimiento.setString(2, productoGuardar.getDescripcionProducto());
                procedimiento.setDouble(3, productoGuardar.getPrecioUnitario());
                procedimiento.setDouble(4, productoGuardar.getPrecioDocena());
                procedimiento.setDouble(5, productoGuardar.getPrecioMayor());
                procedimiento.setInt(6, productoGuardar.getExistencia());
                procedimiento.setInt(7, productoGuardar.getCodigoTipoProducto());
                procedimiento.setInt(8, productoGuardar.getCodigoProveedor());
                procedimiento.execute();
                listadoProductos.add(productoGuardar);
            } catch (Exception e) {
                e.printStackTrace();
            }//TRY CATCH
        }
    }//METODO
    
    public void seleccionarFila() {
        if (tblProductos.getSelectionModel().getSelectedItem() != null) {
            txtCodigoProducto.setText(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
            txtDescripcionProducto.setText(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto());
            txtPrecioUnitario.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
            txtPrecioDocena.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioDocena()));
            txtPrecioMayor.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioMayor()));
            txtExistencia.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getExistencia()));
            cmbCodigoTipoProducto.getSelectionModel().select(buscarTipoProducto(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
            cmbCodigoProveedor.getSelectionModel().select(buscarProveedor(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        } else {
            JOptionPane.showMessageDialog(null, "Seleccionar fila con datos", "Seleccionar Producto", JOptionPane.ERROR_MESSAGE);
            tblProductos.getSelectionModel().clearSelection();
        }
    }

    //METODO ACTIVAR LLENADO CONTROLES TXTS
    public void activarControls() {
        txtCodigoProducto.setEditable(true);
        txtDescripcionProducto.setEditable(true);
        txtPrecioUnitario.setEditable(true);
        txtPrecioDocena.setEditable(true);
        txtPrecioMayor.setEditable(true);
        txtExistencia.setEditable(true);
        cmbCodigoTipoProducto.setDisable(false);
        cmbCodigoProveedor.setDisable(false);
    }

    //METODO DESACTIVAR LLENADO CONTROLES TXTS
    public void desactivarControls() {
        txtCodigoProducto.setEditable(false);
        txtDescripcionProducto.setEditable(false);
        txtPrecioUnitario.setEditable(false);
        txtPrecioDocena.setEditable(false);
        txtPrecioMayor.setEditable(false);
        txtExistencia.setEditable(false);
        cmbCodigoTipoProducto.setDisable(true);
        cmbCodigoProveedor.setDisable(true);
    }

    //METODO LIMPIAR CONTROLES TXTS DE LLENADO
    public void limpiarControls() {
        txtCodigoProducto.clear();
        txtDescripcionProducto.clear();
        txtPrecioUnitario.clear();
        txtPrecioDocena.clear();
        txtPrecioMayor.clear();
        txtExistencia.clear();
        cmbCodigoTipoProducto.getSelectionModel().getSelectedItem();
        cmbCodigoProveedor.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void ButtonsActionsMenuProductos(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}//TABULAR TODO CON ALT SHIFT + F
