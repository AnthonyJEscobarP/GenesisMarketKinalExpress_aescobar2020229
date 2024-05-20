
package org.anthonyescobar.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.anthonyescobar.system.Main;

/**
 *
 * @author Anthony Josue Escobar Ponce
 */
public class VentanaProgramadorController implements Initializable {
    private Main escenarioPrincipal;
    @FXML Button btnRegresar;
    
    
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
    public void ButtonsActionsVentanaProgramador (ActionEvent event){
        if (event.getSource() == btnRegresar){
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
