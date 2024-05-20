-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
/*
 * Documentacion
 * Programador: Anthony Josue Escobar Ponce
 * Carnet: 2020229
 * COD.Tecnico: IN5BM
 * Fecha Creacion: 10/04/2024
 * Fecha de modificaiones:15/04/2024,16/04/2024,23/04/2024,7/05/2024
*/
Drop database if exists DBGenesis_Kinal2020229;
Create database DBGenesis_Kinal2020229;
/* SIEMPRE SELECCIONAR AMBOS AL INICIO*/
Use DBGenesis_Kinal2020229;

set global time_zone = "-6:00";
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_inicioPrograma()
begin
    set global time_zone = "-6:00";
end $$
delimiter ;
call sp_inicioPrograma();
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
Create table Clientes(
	clienteID int not null auto_increment,
    nombreClientes varchar(50) not null,
    apellidoClientes varchar(50) not null,
    nitClientes varchar(10) not null,	
    direccionClientes varchar(150) not null,
    telefonoClientes varchar(45) not null,
    correoClientes varchar(45) not null,
    primary key PK_codigoCliente (clienteID)
);
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO agregar clientes --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_agregarCliente(in nombreClientesIn varchar(50), in apellidoClientesIn varchar(50), in nitClientesIn varchar(10), 
in direccionClientesIn varchar(150), in telefonoClientesIn varchar(45), in correoClientesIn varchar(45))
begin
	insert into Clientes (nombreClientes,apellidoClientes,nitClientes,direccionClientes,telefonoClientes,correoClientes)
    values(nombreClientesIn, apellidoClientesIn, nitClientesIn, direccionClientesIn, telefonoClientesIn, correoClientesIn);
end $$
delimiter ;
call sp_agregarCliente("Anthony Josue","Escobar Ponce","2020229","calle tukituki","555678910","aescobar@kinal.edu.gt");
call sp_agregarCliente("Erick Jonathan","Hancer Arriaga","2020005","calle tururu","578969875","eHancer@Outlook.com");
call sp_agregarCliente("Darlin Rocio","Hancer Ponce","2020656","calle isitikit","57968969","dPonce@yahoo.com");

-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO listar clientes --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_listarClientes()
begin
	select nombreClientes,apellidoClientes,nitClientes,direccionClientes,telefonoClientes,
    correoClientes from Clientes;
end $$
delimiter ;
call sp_listarClientes();
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO buscar clientes --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_buscarCliente(in clienteIDIn int)
begin
	select nombreClientes,apellidoClientes,nitClientes,direccionClientes,telefonoClientes,
    correoClientes from Clientes where clienteID=clienteIDIn;
end $$
delimiter ; 
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO actualizar clientes --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_actualizarCliente(in clienteIDIn int,in nombreClientesIn varchar(50), in apellidoClientesIn varchar(50), in nitClientesIn varchar(10), in direccionClientesIn varchar(150), in telefonoClientesIn varchar(45), in correoClientesIn varchar(45))
begin
	update Clientes
    set
    nombreClientes = nombreClientesIn,
    apellidoClientes = apellidoClientesIn,
    nitClientes = nitClientesIn,
    direccionClientes = direccionClientesIn,
    telefonoClientes = telefonoClientesIn,
    correoClientes = correoClientesIn
    where
    clienteID = clienteIDIn;
end $$
delimiter ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO eliminar clientes --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_eliminarCliente(in clienteIDIn int)
begin
	delete from Clientes where clienteID=clienteIDIn;
end $$
delimiter ;

-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
CREATE TABLE TipoProducto(
	
    codigoTipoProducto int,
    descripcion varchar(45),
    PRIMARY KEY PK_codigoTipoProducto (codigoTipoProducto)
    
)Engine InnoDB;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO agregar TipoProducto --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_agregarTipoProducto(in codigoTipoProductoIn int, in descripcionIn varchar(15))
BEGIN
	INSERT INTO TipoProducto(codigoTipoProducto,descripcion)
		VALUES(codigoTipoProductoIn,descripcionIn);
END$$
DELIMITER ;
CALL sp_agregarTipoProducto(1,"Proteínas");
CALL sp_agregarTipoProducto(2,"Frutas ");
CALL sp_agregarTipoProducto(3,"Bebidas");
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO listar TipoProductos --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_listarTiposProductos()
BEGIN
	SELECT codigoTipoProducto,descripcion FROM TipoProducto;
END$$
DELIMITER ;
call sp_listarTiposProductos();
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO buscar TipoProductos --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_buscarTipoProducto(in codigoTipoProductoIn int)
begin
	select codigoTipoProducto,descripcion from TipoProducto
	WHERE codigoTipoProducto = codigoTipoProductoIn;
end $$
delimiter ; 
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO actualizar TipoProductos --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_actualizarTipoProducto(IN codigoTipoProductoIn INT,IN descripcionIn VARCHAR(45))
BEGIN
    UPDATE TipoProducto
    SET descripcion = descripcionIn
    WHERE codigoTipoProducto = codigoTipoProductoIn;
END$$
DELIMITER ;

-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO eliminar TipoProductos --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_eliminarTipoProducto(in codigoTipoProductoIn int)
BEGIN
	DELETE FROM TipoProducto
    WHERE codigoTipoProducto = codigoTipoProductoIn;
END$$
DELIMITER ;

-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
CREATE TABLE Compras(

	numeroDocumento int,
    fechaDocumento date,
    descripcion varchar(60),
    totalDocumento decimal(10,2),
    PRIMARY KEY PK_numeroDocumento (numeroDocumento)

)Engine InnoDB;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO agregar Compras --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_agregarCompra(IN numeroDocumentoIn INT,IN fechaDocumentoIn DATE,IN descripcionIn VARCHAR(60),IN totalDocumentoIn DECIMAL(10,2))
BEGIN
    INSERT INTO Compras(numeroDocumento, fechaDocumento, descripcion, totalDocumento)
    VALUES(numeroDocumentoIn, fechaDocumentoIn, descripcionIn, totalDocumentoIn);
END$$
DELIMITER ;
CALL sp_agregarCompras(1234, '2024-02-06', "ejemplo", 05.00);
CALL sp_agregarCompras(4567, '2024-04-08', "ejemplo2", 45.00);
CALL sp_agregarCompras(8910, '2024-05-10', "ejemplo3", 15.00);
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO listar Compras --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_listarCompras()
BEGIN
    SELECT numeroDocumento, fechaDocumento, descripcion, totalDocumento FROM Compras;
END$$
DELIMITER ;
CALL sp_listarCompras();
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO buscar Compras --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_buscarCompra(in numeroDocumentoIN int)
BEGIN
    SELECT numeroDocumento, fechaDocumento, descripcion, totalDocumento FROM Compras
    where numeroDocumento = numeroDocumentoIN; 
END$$
DELIMITER ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO actualizar Compras --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_actualizarCompra(IN numeroDocumentoIn INT,IN fechaDocumentoIn DATE,IN descripcionIn VARCHAR(60),IN totalDocumentoIn DECIMAL(10,2))
BEGIN
    UPDATE Compras
    SET fechaDocumento = fechaDocumentoIn,
        descripcion = descripcionIn,
        totalDocumento = totalDocumentoIn
    WHERE numeroDocumento = numeroDocumentoIn;
END$$
DELIMITER ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO eliminar Compras --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_eliminarCompra(IN numeroDocumentoIn INT)
BEGIN
    DELETE FROM Compras
    WHERE numeroDocumento = numeroDocumentoIn;
END$$
DELIMITER ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
CREATE TABLE CargoEmpleado(

	codigoCargoEmpleado int,
    nombreCargo varchar(45),
    descripcionCargo varchar(45),
    PRIMARY KEY PK_codigoCargoEmpleado (codigoCargoEmpleado)

)Engine InnoDB;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO agregar CargoEmpleado --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$

CREATE PROCEDURE sp_agregarCargoEmpleado(IN codigoCargoEmpleadoIn INT,IN nombreCargoIn VARCHAR(45),IN descripcionCargoIn VARCHAR(45))
BEGIN
    INSERT INTO CargoEmpleado(codigoCargoEmpleado, nombreCargo, descripcionCargo)
    VALUES(codigoCargoEmpleadoIn, nombreCargoIn, descripcionCargoIn);
END$$
DELIMITER ;
CALL sp_agregarCargoEmpleado(12,"Gerente online","administrador de tienda online");
CALL sp_agregarCargoEmpleado(15,"Marketing administrativo","administrador dde ventas y publicidad");
CALL sp_agregarCargoEmpleado(16,"Supervisor general","manejo y supervision de almacenes");
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO listar CargoEmpleado --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_listarCargosEmpleado()
BEGIN
    SELECT codigoCargoEmpleado, nombreCargo, descripcionCargo FROM CargoEmpleado;
END$$
DELIMITER ;
CALL sp_listarCargoEmpleado();
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO buscar CargoEmpleado --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_buscarCargoEmpleado(in codigoCargoEmpleadoIN int)
BEGIN
    SELECT codigoCargoEmpleado, nombreCargo, descripcionCargo FROM CargoEmpleado
    where codigoCargoEmpleado = codigoCargoEmpleadoIN;
END$$
DELIMITER ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO actualizar CargoEmpleado --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_actualizarCargoEmpleado(IN codigoCargoEmpleadoIn INT,IN nombreCargoIn VARCHAR(45),IN descripcionCargoIn VARCHAR(45))
BEGIN
    UPDATE CargoEmpleado
    SET nombreCargo = nombreCargoIn,
        descripcionCargo = descripcionCargoIn
    WHERE codigoCargoEmpleado = codigoCargoEmpleadoIn;
END$$
DELIMITER ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO eliminar CargoEmpleado --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_eliminarCargoEmpleado(IN codigoCargoEmpleadoIn INT)
BEGIN
    DELETE FROM CargoEmpleado
    WHERE codigoCargoEmpleado = codigoCargoEmpleadoIn;
END$$
DELIMITER ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
CREATE TABLE Proveedores(
codigoProveedor int,
nitProveedor varchar(10),
nombreProveedor varchar(60),
apellidosProveedor varchar(60),
direccionProveedor varchar(150),
razonSocial varchar(60),
contactoPrincipal varchar(100),
paginaWeb varchar(50),
PRIMARY KEY PK_codigoProveedor (codigoProveedor)
)Engine InnoDB;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO AGREGAR Proveedores --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_agregarProveedor(IN codigoProveedorIn INT,IN nitProveedorIn VARCHAR(10),IN nombreProveedorIn VARCHAR(60),IN apellidosProveedorIn VARCHAR(60),
IN direccionProveedorIn VARCHAR(150),IN razonSocialIn VARCHAR(60), IN contactoPrincipalIin VARCHAR(100),IN paginaWebIn VARCHAR(50)
)
BEGIN
    INSERT INTO Proveedores(codigoProveedor, nitProveedor, nombreProveedor, apellidosProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
    VALUES(codigoProveedorIn, nitProveedorIn, nombreProveedorIn, apellidosProveedorIn, direccionProveedorIn, razonSocialIn, contactoPrincipalIin, paginaWebIn);
END$$
DELIMITER ;
CALL sp_agregarProveedor(1, '123456-e5', 'Proveedor 1', 'Apellidos Proveedor', '123 Calle kinal, Ciudad', 'Empresa 1', 'Caspers Kinals', 'www.proveedor1.com');
CALL sp_agregarProveedor(2, '18181-e8', 'Proveedor 2', 'Apellidos Proveedor 1', '15 Calle avenida 65, Ciudad Guatemala', 'Empresa 2', 'Anthony Escobar Kinals', 'www.proveedor2.com');
CALL sp_agregarProveedor(3, '8484d-8w', 'Proveedor 3', 'Apellidos Proveedor 2', 'Calle 85 avenida 57 kinals-lops, Guatemala', 'Empresa 3', 'lisa Kinals', 'www.proveedor3.com');
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO LISTAR Proveedores --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_listarProveedores()
BEGIN
    SELECT codigoProveedor, nitProveedor, nombreProveedor, apellidosProveedor, direccionProveedor, razonSocial, 
    contactoPrincipal, paginaWeb FROM Proveedores;
END$$
DELIMITER ;

CALL sp_listarProveedores();
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO buscar Proveedores --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_buscarProveedor(in codigoProveedorIn int)
begin
	select codigoProveedor, nitProveedor, nombreProveedor, apellidosProveedor, direccionProveedor, 
    razonSocial, contactoPrincipal, paginaWeb from Proveedores where codigoProveedor=codigoProveedorIn;
end $$
delimiter ; 
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO ACTUALIZAR Proveedores --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_actualizarProveedor(IN codigoProveedorIn INT,IN nitProveedorIn VARCHAR(10),IN nombreProveedorIn VARCHAR(60),IN apellidosProveedorIn VARCHAR(60),
IN direccionProveedorIn VARCHAR(150),IN razonSocialIn VARCHAR(60), IN contactoPrincipalIin VARCHAR(100),IN paginaWebIn VARCHAR(50))
BEGIN
    UPDATE Proveedores
    SET nitProveedor = nitProveedorIn,
        nombreProveedor = nombreProveedorIn,
        apellidosProveedor = apellidosProveedorIn,
        direccionProveedor = direccionProveedorIn,
        razonSocial = razonSocialIn,
        contactoPrincipal = contactoPrincipalIin,
        paginaWeb = paginaWebIn
    WHERE codigoProveedor = codigoProveedorIn;
END$$
DELIMITER ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO ELIMINAR Proveedores --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_eliminarProveedor(IN codigoProveedorIn INT)
BEGIN
    DELETE FROM Proveedores
    WHERE codigoProveedor = codigoProveedorIn;
END$$

DELIMITER ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
CREATE TABLE TelefonoProveedor(

codigoTelefonoProveedor int,
numeroPincipal varchar(8),
numeroSecundario varchar(8),
observaciones varchar(45),
codigoProveedor int,
PRIMARY KEY PK_codigoTelefonoProveedor (codigoTelefonoProveedor),
FOREIGN KEY (codigoProveedor) REFERENCES Proveedores (codigoProveedor)

);

CREATE TABLE EmailProveedor(

codigoEmailProveedor int,
emailproveedor varchar(50),
descripcion varchar(100),
codigoProveedor int,
PRIMARY KEY PK_codigoEmailProveedor (codigoEmailProveedor),
FOREIGN KEY (codigoProveedor) REFERENCES Proveedores (codigoProveedor)

);

CREATE TABLE Productos(
	
codigoProducto varchar(15),
descripcionProducto varchar(15),
precioUnitario decimal(10,2),
precioDocena decimal(10,2),
precioMayor decimal(10,2),
-- imagenProducto varchar(45),
existencia int,
codigoTipoProducto int,
codigoProveedor int,
PRIMARY KEY PK_codigoProducto (codigoProducto),
FOREIGN KEY (codigoTipoProducto) REFERENCES TipoProducto(codigoTipoProducto),
FOREIGN KEY (codigoProveedor) REFERENCES Proveedores(codigoProveedor)
    
);
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO agregar Productos --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_agregarProducto(in codigoProductoIN varchar(15), in descripcionProductoIN varchar(15), 
in precioUnitarioIN decimal(10,2), in precioDocenaIN decimal(10,2), in precioMayorIN decimal(10,2),
in existenciaIN int,in codigoTipoProductoIN int,in codigoProveedorIN int)
begin
	insert into Productos (codigoProducto,descripcionProducto,precioUnitario,precioDocena,precioMayor,existencia,
    codigoTipoProducto,codigoProveedor)
    
    values(codigoProductoIN, descripcionProductoIN, precioUnitarioIN, precioDocenaIN, precioMayorIN,existenciaIN,
    codigoTipoProductoIN,codigoProveedorIN);
end $$
delimiter ;
call sp_agregarProducto("04254f-5","morral/ frutas",52.50,142.30,800.50,58,2,2);
call sp_agregarProducto("00258-e6","cereales",12,40.30,80.90,48,2,1);
call sp_agregarProducto("184fg-2","bombones",5.00,15.30,40.50,58,1,3);

-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO listar Productos --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_listarProductos()
begin
	select codigoProducto,descripcionProducto,precioUnitario,precioDocena,precioMayor,existencia,
    codigoTipoProducto,codigoProveedor from Productos;
end $$
delimiter ;
call sp_listarProductos();
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO buscar Productos --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_buscarProducto(in codigoProductoIN int)
begin
	select codigoProducto,descripcionProducto,precioUnitario,precioDocena,precioMayor,existencia,
    codigoTipoProducto,codigoProveedor from Productos
    where codigoProducto=codigoProductoIN;
end $$
delimiter ; 
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO actualizar Productos --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_actualizarProducto(in codigoProductoIN varchar(15), in descripcionProductoIN varchar(15), 
in precioUnitarioIN decimal(10,2), in precioDocenaIN decimal(10,2), in precioMayorIN decimal(10,2), 
in existenciaIN int,in codigoTipoProductoIN int,in codigoProveedorIN int)
begin
	update Productos
    set
    descripcionProducto = descripcionProductoIN,
    precioUnitario = precioUnitarioIN,
    precioDocena = precioDocenaIN,
    precioMayor = precioMayorIN,
    existencia = existenciaIN,
    codigoTipoProducto = codigoTipoProductoIN,
    codigoProveedorIN = codigoProveedorIN
    where
    codigoProducto = codigoProductoIN;
end $$
delimiter ;


-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO eliminar Productos --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_eliminarProducto(in codigoProductoIN int)
begin
	delete from Productos where codigoProducto = codigoProductoIN;
end $$
delimiter ;

-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
CREATE TABLE DetalleCompra(

codigoDetalleCompra int,
costoUnitario decimal(10,2),
cantidad int,
codigoProducto varchar(15),
numeroDocumento int,
PRIMARY KEY PK_codigoDetalleCompra (codigoDetalleCompra),
FOREIGN KEY (codigoProducto) REFERENCES Productos(codigoProducto),
FOREIGN KEY (numeroDocumento) REFERENCES Compras(numeroDocumento)
    
);

CREATE TABLE Empleados(

codigoEmpleado int,
nombresEmpleado varchar(50),
apellidosEmpleado varchar(50),
sueldo decimal(10,2),
direccion varchar(150),
turno varchar(15),
codigoCargoEmpleado int,
PRIMARY KEY PK_codigoEmpleado (codigoEmpleado),
FOREIGN KEY (codigoCargoEmpleado) REFERENCES CargoEmpleado(codigoCargoEmpleado)

);

CREATE TABLE Factura(

numeroDeFactura int,
estado varchar(50),
totalFactura decimal(10,2),
fechaFactura varchar(45),
codigoCliente int,
codigoEmpleado int,
PRIMARY KEY PK_numeroDeFactura (numeroDeFactura),
FOREIGN KEY (codigoCliente) REFERENCES Clientes(clienteID),
FOREIGN KEY (codigoEmpleado) REFERENCES Empleados(codigoEmpleado)

);

CREATE TABLE DetalleFactura(

codigoDetalleFactura int,
precioUnitario decimal(10,2),
cantidad int,
numeroDeFactura int,
codigoProducto varchar(15),
PRIMARY KEY PK_codigoDetalleFactura (codigoDetalleFactura),
FOREIGN KEY (numeroDeFactura) REFERENCES Factura(numeroDeFactura),
FOREIGN KEY (codigoProducto) REFERENCES Productos(codigoProducto)

);