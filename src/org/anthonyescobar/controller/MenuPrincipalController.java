
package org.anthonyescobar.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import org.anthonyescobar.system.Main;

/**
 *
 * @author Anthony Josue Escobar Ponce
 */
public class MenuPrincipalController implements Initializable {
    private Main escenarioPrincipal;
    @FXML MenuItem btnVentanaProgramador;
    @FXML Button btnClientes;
    @FXML Button btnProveedores;
    @FXML Button btnTipoProductos;
    @FXML Button btnCompras;
    @FXML Button btnCargoEmpleados;
    //NOTAS: EXECUTE CUANDO DEUELVE ALGO Y EXECUTE QUERY CUANDO SOLO VA A MOSTRAR
    //TABULAR TODO CON ALT SHIFT + F
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    
    @FXML
    public void ButtonsActionsMenuPrincipal (ActionEvent event){
        if (event.getSource() == btnClientes){
            escenarioPrincipal.menuClientesView();
        }else if (event.getSource() == btnVentanaProgramador){
           escenarioPrincipal.ventanaProgramadorView();
        }else if (event.getSource() == btnProveedores){
           escenarioPrincipal.menuProveedoresView();
        }else if (event.getSource() == btnTipoProductos){
           escenarioPrincipal.menuTipoProductoView();
        }else if (event.getSource() == btnCompras){
           escenarioPrincipal.menuComprasView();
        }else if (event.getSource() == btnCargoEmpleados){
           escenarioPrincipal.menuCargoEmpleadoView();
        }//
    }
}
