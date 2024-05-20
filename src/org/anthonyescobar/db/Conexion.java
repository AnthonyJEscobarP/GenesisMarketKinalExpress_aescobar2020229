package org.anthonyescobar.db;
import java.sql.*;
/**
 * CLASE CONEXION PARA FUNCIONALIDAD JDBC
 * @author Anthony Escobar
 */
public class Conexion {
    private Connection conexion;
    private static Conexion instancia;

    //AQUI EL PROFESOR LUIS LO HIZO PUBLICO
    public Conexion(){
        try{ // CLASE DRIVER PARA CONEXION Y DIRECCIONAMIENTO PARA BASE DE DATOS 
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBGenesis_Kinal2020229?useSSL=false","root","2007");
                    // localhost o la ip loopBack (en este caso 127.0.0.1). y MANEJO DE EXCEPCIONES PARA CLASE NO ENCONTRA,SQL, ACCESO ILEGAL E ISNTANCIA.
        }catch(ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException error){
            error.printStackTrace();
            
        }
    }
    // SINGLETON 
    public static Conexion getInstance() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    
    
    
}
