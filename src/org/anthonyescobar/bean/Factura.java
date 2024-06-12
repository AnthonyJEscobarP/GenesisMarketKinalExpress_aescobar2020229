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
public class Factura {
    private int numeroDeFactura;
    private String estado;
    private Double totalFactura;
    private String fechaFactura;
    private int codigoCliente;
    private int codigoEmpleado;

    public Factura() {
    }

    public Factura(int numeroFactura, String estado, Double totalFactura, String fechaFactura, int codigoCliente, int codigoEmpleado) {
        this.numeroDeFactura = numeroFactura;
        this.estado = estado;
        this.totalFactura = totalFactura;
        this.fechaFactura = fechaFactura;
        this.codigoCliente = codigoCliente;
        this.codigoEmpleado = codigoEmpleado;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public int getNumeroDeFactura() {
        return numeroDeFactura;
    }

    public void setNumeroDeFactura(int numeroDeFactura) {
        this.numeroDeFactura = numeroDeFactura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(Double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    @Override
    public String toString() {
        return  numeroDeFactura + "| " + fechaFactura ;
    }
    
    
}
