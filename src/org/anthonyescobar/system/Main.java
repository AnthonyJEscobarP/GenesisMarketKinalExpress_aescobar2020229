package org.anthonyescobar.system;

import java.io.InputStream;
import java.security.Principal;
import java.sql.PreparedStatement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.anthonyescobar.controller.MenuCargoEmpleadoController;
import org.anthonyescobar.controller.MenuClientesController;
import org.anthonyescobar.controller.MenuComprasController;
import org.anthonyescobar.controller.MenuFacturasController;
import org.anthonyescobar.controller.MenuPrincipalController;
import org.anthonyescobar.controller.MenuProductosController;
import org.anthonyescobar.controller.MenuProveedoresController;
import org.anthonyescobar.controller.MenuTipoProductoController;
import org.anthonyescobar.controller.VentanaProgramadorController;
import org.anthonyescobar.db.Conexion;

/**
 * Documentacion
 *
 * @author Anthony Josue Escobar Ponce Carnet: 2020229 COD.Tecnico: IN5BM Fecha
 * Creacion: 10/04/2024 Fecha de modificaiones:15/04/2024, 16/04/2024, 23/04/2024, 24/04/2024, 30/04/2024, 31/04/2024,7/05/2024,8-10/05/2024
 */
public class Main extends Application {

    private final String Views = "/org/anthonyescobar/view/";
    private Stage escenarioPrincipal;
    private Scene escena;

    @Override
    public void start(Stage escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("GENESIS MARKET - KINAL EXPRESS");
        /*login();*/
        menuPrincipalView();
        escenarioPrincipal.show();
        //startProgram();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Initializable cambiarEscena(String fxmlArchivo, int ancho, int alto) throws Exception {
        Initializable resultado = null;
        FXMLLoader cargador = new FXMLLoader();

        InputStream archivo = Main.class.getResourceAsStream(Views + fxmlArchivo);
        cargador.setBuilderFactory(new JavaFXBuilderFactory());
        cargador.setLocation(Main.class.getResource(Views + fxmlArchivo));

        escena = new Scene((AnchorPane) cargador.load(archivo), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable) cargador.getController();
        return resultado;
    }

    public void menuPrincipalView() {
        try {
            MenuPrincipalController principalVw = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 900, 475);
            principalVw.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaProgramadorView() {
        try {
            VentanaProgramadorController programadorVw = (VentanaProgramadorController) cambiarEscena("VentanaProgramadorView.fxml", 935, 500);
            programadorVw.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e.getMessage()); 
        }
    }

    public void menuClientesView() {
        try {
            MenuClientesController clientesVw = (MenuClientesController) cambiarEscena("MenuClientesView.fxml", 1124, 600);
            clientesVw.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e.getMessage()); 
        }
    }
    
    public void menuProveedoresView() {
        try {
            MenuProveedoresController proveedoresVw = (MenuProveedoresController) cambiarEscena("MenuProveedoresView.fxml", 1205, 641);
            proveedoresVw.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e.getMessage()); 
        }
    }
    
    public void menuTipoProductoView() {
        try {
            MenuTipoProductoController tipoProductoVw = (MenuTipoProductoController) cambiarEscena("MenuTipoProductoView.fxml", 1110, 592);
            tipoProductoVw.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e.getMessage()); 
        }
    }
    
    public void menuComprasView() {
        try {
            MenuComprasController comprasVw = (MenuComprasController) cambiarEscena("MenuComprasView.fxml", 1110, 592);
            comprasVw.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e.getMessage()); 
        }
    }
    
    public void menuCargoEmpleadoView() {
        try {
            MenuCargoEmpleadoController cargoEmpleadoVw = (MenuCargoEmpleadoController) cambiarEscena("MenuCargoEmpleadoView.fxml", 1110, 592);
            cargoEmpleadoVw.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e.getMessage()); 
        }
    }
    
    public void menuProductosView() {
        try {
            MenuProductosController productosVw = (MenuProductosController) cambiarEscena("MenuProductosView.fxml", 1110, 592);
            productosVw.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e.getMessage()); 
        }
    }
    
    public void menuFacturasView() {
        try {
            MenuFacturasController facturasVw = (MenuFacturasController) cambiarEscena("MenuFacturasView.fxml", 1110, 592);
            facturasVw.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e.getMessage()); 
        }
    }
    
    /*public void startProgram(){
        try{
        PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{sp_inicioPrograma()}");
        sp.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    
}
