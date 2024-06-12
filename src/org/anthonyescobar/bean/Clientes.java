/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anthonyescobar.bean;

/**
 *
 * @author Anthony Escobar
 */
public class Clientes {
    private int clienteID;
    private String nombreCliente;
    private String apellidoCliente;
    private String nitCliente;
    private String direccionCliente;
    private String telefonoCliente;
    private String correoClientes;
    
 
    public Clientes() {
    }

    public Clientes(int clienteID, String nombreClientes, String apellidoClientes, String nitClientes, String direccionClientes, String telefonoClientes, String correoClientes) {
        this.clienteID = clienteID;
        this.nombreCliente = nombreClientes;
        this.apellidoCliente = apellidoClientes;
        this.nitCliente = nitClientes;
        this.direccionCliente = direccionClientes;
        this.telefonoCliente = telefonoClientes;
        this.correoClientes = correoClientes;
    }

    public int getClienteID() {
        return clienteID;
    }

    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getCorreoClientes() {
        return correoClientes;
    }

    public void setCorreoClientes(String correoClientes) {
        this.correoClientes = correoClientes;
    }

    @Override
    public String toString() {
        return "||"  + clienteID + "||"  + nombreCliente + "||"  + apellidoCliente + "||"  + nitCliente + "||"  + telefonoCliente + "||";
    }
    
    
    
 }
 