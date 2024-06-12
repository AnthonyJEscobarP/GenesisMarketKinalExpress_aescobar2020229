package org.anthonyescobar.bean;

/**
 *
 * @author Anthony Escobar
 */
public class Proveedores {
    private int codigoProveedor;
    private String nitProveedor;
    private String nombreProveedor;
    private String apellidosProveedor;
    private String direccionProveedor;
    private String telefonoProveedor;
    private String emailProveedor;
    private String razonSocial;
    private String contactoPrincipal;
    private String paginaWeb;
    
 
    public Proveedores() {
    }

    public Proveedores(int codigoProveedor, String nitProveedor, String nombreProveedor, String apellidosProveedor, String direccionProveedor, String telefonoProveedor, String emailProveedor, String razonSocial, String contactoPrincipal, String paginaWeb) {
        this.codigoProveedor = codigoProveedor;
        this.nitProveedor = nitProveedor;
        this.nombreProveedor = nombreProveedor;
        this.apellidosProveedor = apellidosProveedor;
        this.direccionProveedor = direccionProveedor;
        this.telefonoProveedor = telefonoProveedor;
        this.emailProveedor = emailProveedor;
        this.razonSocial = razonSocial;
        this.contactoPrincipal = contactoPrincipal;
        this.paginaWeb = paginaWeb;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getNitProveedor() {
        return nitProveedor;
    }

    public void setNitProveedor(String nitProveedor) {
        this.nitProveedor = nitProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getApellidosProveedor() {
        return apellidosProveedor;
    }

    public void setApellidosProveedor(String apellidosProveedor) {
        this.apellidosProveedor = apellidosProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public String getEmailProveedor() {
        return emailProveedor;
    }

    public void setEmailProveedor(String emailProveedor) {
        this.emailProveedor = emailProveedor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getContactoPrincipal() {
        return contactoPrincipal;
    }

    public void setContactoPrincipal(String contactoPrincipal) {
        this.contactoPrincipal = contactoPrincipal;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }
    
    @Override
    public String toString() {
        return "|| " + codigoProveedor + " || "  + nitProveedor + " || "  + apellidosProveedor + " || "  + contactoPrincipal + " || " + razonSocial+ " ||";
    }
    
 }
 