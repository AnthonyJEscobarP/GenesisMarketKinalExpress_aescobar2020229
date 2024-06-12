-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
/*
 * Documentacion
 * Programador: Anthony Josue Escobar Ponce
 * Carnet: 2020229
 * COD.Tecnico: IN5BM
 * Fecha Creacion: 10/04/2024
 * Fecha de modificaiones:15/04/2024,16/04/2024,23/04/2024,7/05/2024,8-18/05/2024
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
    nombreCliente varchar(50) not null,
    apellidoCliente varchar(50) not null,
    nitCliente varchar(10) not null,	
    direccionCliente varchar(150) not null,
    telefonoCliente varchar(45) not null,
    correoCliente varchar(45) not null,
    primary key PK_codigoCliente (clienteID)
);
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO agregar clientes --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_agregarCliente(in nombreClienteIn varchar(50), in apellidoClienteIn varchar(50), in nitClienteIn varchar(10), 
in direccionClienteIn varchar(150), in telefonoClienteIn varchar(45), in correoClienteIn varchar(45))
begin
	insert into Clientes (nombreCliente,apellidoCliente,nitCliente,direccionCliente,telefonoCliente,correoCliente)
    values(nombreClienteIn, apellidoClienteIn, nitClienteIn, direccionClienteIn, telefonoClienteIn, correoClienteIn);
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
	select
	Clientes.clienteID,
    Clientes.NITCliente,
    Clientes.nombreCliente,
    Clientes.apellidoCliente,
    Clientes.direccionCliente,
    Clientes.telefonoCliente,
    Clientes.correoCliente
    from Clientes;
end $$
delimiter ;
call sp_listarClientes();
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO buscar clientes --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_buscarCliente(in clienteIDIn int)
begin
	select clienteID,nombreCliente,apellidoCliente,nitCliente,direccionCliente,telefonoCliente,
    correoCliente from Clientes where clienteID=clienteIDIn;
end $$
delimiter ; 
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO actualizar clientes --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_actualizarCliente(in clienteIDIn int,in nombreClienteIn varchar(50), in apellidoClienteIn varchar(50), in nitClienteIn varchar(10), in direccionClienteIn varchar(150), in telefonoClienteIn varchar(45), in correoClienteIn varchar(45))
begin
	update Clientes
    set
    nombreCliente = nombreClienteIn,
    apellidoCliente = apellidoClienteIn,
    nitCliente = nitClienteIn,
    direccionCliente = direccionClienteIn,
    telefonoCliente = telefonoClienteIn,
    correoCliente = correoClienteIn
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
CALL sp_agregarCompra(11, '2024-02-06', "ejemplo", 05.00);
CALL sp_agregarCompra(22, '2024-04-08', "ejemplo2", 45.00);
CALL sp_agregarCompra(33, '2024-05-10', "ejemplo3", 15.00);
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
CREATE PROCEDURE sp_actualizarCompra(IN numeroDocumentoIn INT,IN fechaDocumentoIn DATE,IN descripcionIn VARCHAR(60))
BEGIN
    UPDATE Compras
    SET fechaDocumento = fechaDocumentoIn,
        descripcion = descripcionIn
    WHERE numeroDocumento = numeroDocumentoIn;
END$$
DELIMITER ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
					-- PROCEDIMIENTO actualizar total de Compras(insertar) --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_actualizarTotalCompra(in numeroDocumentoIn int,in totalDocumentoIn decimal(10,2))
begin
	update Compras 
	set 
		totalDocumento = totalDocumentoIn
    where numeroDocumento = numeroDocumentoIn;
end $$
delimiter ;
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
CALL sp_listarCargosEmpleado();
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
    telefonoProveedor varchar(15),
    emailProveedor varchar(30),
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
IN direccionProveedorIn VARCHAR(150),IN telefonoProveedorIn varchar(15),IN emailProveedorIn varchar(30),IN razonSocialIn VARCHAR(60), IN contactoPrincipalIin VARCHAR(100),IN paginaWebIn VARCHAR(50)
)
BEGIN
    INSERT INTO Proveedores(codigoProveedor, nitProveedor, nombreProveedor, apellidosProveedor, direccionProveedor,telefonoProveedor,emailProveedor, razonSocial, contactoPrincipal, paginaWeb)
    VALUES(codigoProveedorIn, nitProveedorIn, nombreProveedorIn, apellidosProveedorIn, direccionProveedorIn,telefonoProveedorIn,emailProveedorIn, razonSocialIn, contactoPrincipalIin, paginaWebIn);
END$$
DELIMITER ;
CALL sp_agregarProveedor(1, '123456-e5', 'Proveedor 1', 'Apellidos Proveedor', '123 Calle kinal, Ciudad', '+502 1234-5678', 'correoProv@yahoo.com', 'Empresa 1', 'Caspers Kinals', 'www.proveedor1.com');
CALL sp_agregarProveedor(2, '18181-e8', 'Proveedor 2', 'Apellidos Proveedor 1', '15 Calle avenida 65, Ciudad Guatemala','+502 1234-5678', 'correoProv@yahoo.com', 'Empresa 2', 'Anthony Escobar Kinals', 'www.proveedor2.com');
CALL sp_agregarProveedor(3, '8484d-8w', 'Proveedor 3', 'Apellidos Proveedor 2', 'Calle 85 avenida 57 kinals-lops, Guatemala','+502 1234-5678', 'correoProv@yahoo.com', 'Empresa 3', 'lisa Kinals', 'www.proveedor3.com');
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO LISTAR Proveedores --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_listarProveedores()
BEGIN
    SELECT codigoProveedor, nitProveedor, nombreProveedor, apellidosProveedor, direccionProveedor,telefonoProveedor,
    emailProveedor, razonSocial, contactoPrincipal, paginaWeb FROM Proveedores;
END$$
DELIMITER ;

CALL sp_listarProveedores();
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO buscar Proveedores --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_buscarProveedor(in codigoProveedorIn int)
begin
	select codigoProveedor, nitProveedor, nombreProveedor, apellidosProveedor, direccionProveedor,telefonoProveedor,
    emailProveedor,razonSocial, contactoPrincipal, paginaWeb from Proveedores where codigoProveedor=codigoProveedorIn;
end $$
delimiter ; 
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO ACTUALIZAR Proveedores --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_actualizarProveedor(IN codigoProveedorIn INT,IN nitProveedorIn VARCHAR(10),IN nombreProveedorIn VARCHAR(60),IN apellidosProveedorIn VARCHAR(60),
IN direccionProveedorIn VARCHAR(150),IN telefonoProveedorIn varchar(15),IN emailProveedorIn varchar(30),IN razonSocialIn VARCHAR(60), IN contactoPrincipalIin VARCHAR(100),IN paginaWebIn VARCHAR(50)
)
BEGIN
    UPDATE Proveedores
    SET nitProveedor = nitProveedorIn,
        nombreProveedor = nombreProveedorIn,
        apellidosProveedor = apellidosProveedorIn,
        direccionProveedor = direccionProveedorIn,
        telefonoProveedor = telefonoProveedorIn,
        emailProveedor = emailProveedorIn,
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
call sp_agregarProducto("001","morral/ frutas",0.00,0.00,0.00,58,2,2);
call sp_agregarProducto("002","cereales",0.00,0.00,0.00,48,2,1);
call sp_agregarProducto("003","bombones",0.00,0.00,0.00,58,1,3);

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
in codigoTipoProductoIN int,in codigoProveedorIN int)
begin
	update Productos
    set
    descripcionProducto = descripcionProductoIN,
    codigoTipoProducto = codigoTipoProductoIN,
    codigoProveedor = codigoProveedorIN
    where
    codigoProducto = codigoProductoIN;
end $$
delimiter ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
						-- PROCEDIMIENTO actualizar PRECIOS EN Productos separado --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_actualizarPreciosDeProductos(in codigoProductoIN varchar(15),in precioUnitarioIN decimal(10,2), in precioDocenaIN decimal(10,2), 
in precioMayorIN decimal(10,2),in existenciaIN int)
begin
	update Productos 
	set 
		precioUnitario = precioUnitarioIN,
		precioDocena = precioDocenaIN,
		precioMayor = precioMayorIN,
		existencia = existenciaIN
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
FOREIGN KEY (codigoProducto) REFERENCES Productos(codigoProducto) on delete cascade,
FOREIGN KEY (numeroDocumento) REFERENCES Compras(numeroDocumento) on delete cascade
    
);
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO AGREGAR DetalleCompra --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_agregarDetalleCompra(IN codigoDetalleCompraIn INT,IN costoUnitarioIn decimal(10,2),IN cantidadIn INT,
IN codigoProductoIn varchar(15),IN numeroDocumentoIn int)
BEGIN
    INSERT INTO DetalleCompra(codigoDetalleCompra, costoUnitario, cantidad, codigoProducto,numeroDocumento)
    VALUES(codigoDetalleCompraIn, costoUnitarioIn, cantidadIn, codigoProductoIn,numeroDocumentoIn);
END$$
DELIMITER ;
CALL sp_agregarDetalleCompra(1, 2.25, 2, '001', 22);
CALL sp_agregarDetalleCompra(2, 2.25,5, '002', 11);
CALL sp_agregarDetalleCompra(3,2.25, 2, '003', 33);
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO LISTAR DetalleCompra --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_listarDetallesCompras()
BEGIN
    SELECT codigoDetalleCompra, costoUnitario, cantidad, codigoProducto,numeroDocumento 
    FROM DetalleCompra;
END$$
DELIMITER ;

CALL sp_listarDetallesCompras();
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO buscar DetalleCompra --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_buscarDetalleCompra(in codigoDetalleCompraIn int)
begin
	select codigoDetalleCompra, costoUnitario, cantidad, codigoProducto,numeroDocumento
    from DetalleCompra where codigoDetalleCompra=codigoDetalleCompraIn;
end $$
delimiter ; 
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO ACTUALIZAR DetalleCompra --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_actualizarDetalleCompra(IN codigoDetalleCompraIn INT,IN costoUnitarioIn decimal(10,2),IN cantidadIn INT,
IN codigoProductoIn varchar(15),IN numeroDocumentoIn int)
BEGIN
    UPDATE DetalleCompra
    SET costoUnitario = costoUnitarioIn,
        cantidad = cantidadIn,
        codigoProducto = codigoProductoIn,
        numeroDocumento = numeroDocumentoIn
    WHERE codigoDetalleCompra=codigoDetalleCompraIn;
END$$
DELIMITER ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO ELIMINAR DetalleCompra --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_eliminarDetalleCompra(IN codigoDetalleCompraIn INT)
BEGIN
    DELETE FROM DetalleCompra
    WHERE codigoDetalleCompra=codigoDetalleCompraIn;
END$$

DELIMITER ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
							-- PROCEDIMIENTO ELIMINAR DetalleCompra por producto --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_eliminarDetalleCompraConProducto(IN codigoProductoIn INT)
BEGIN
    DELETE FROM DetalleCompra
    WHERE codigoProducto=codigoProductoIn;
END$$

DELIMITER ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||

CREATE TABLE Empleados(

codigoEmpleado int,
nombresEmpleado varchar(50),
apellidosEmpleado varchar(50),
sueldo decimal(10,2),
direccion varchar(150),
turno varchar(15),
codigoCargoEmpleado int,
PRIMARY KEY PK_codigoEmpleado (codigoEmpleado),
FOREIGN KEY (codigoCargoEmpleado) REFERENCES CargoEmpleado(codigoCargoEmpleado) on delete cascade

);
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO AGREGAR Empleados --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_agregarEmpleado(IN codigoEmpleadoIn INT,IN nombresEmpleadoIn varchar(50),IN apellidosEmpleadoIn varchar(50),
IN sueldoIn decimal(10,2),IN direccionIn varchar(150), turnoIn varchar(15), IN codigoCargoEmpleadoIn int)
BEGIN
    INSERT INTO Empleados(codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo,direccion,turno,codigoCargoEmpleado)
    VALUES(codigoEmpleadoIn, nombresEmpleadoIn, apellidosEmpleadoIn, sueldoIn,direccionIn,turnoIn,codigoCargoEmpleadoIn);
END$$
DELIMITER ;
CALL sp_agregarEmpleado(1, 'Juan', 'Pérez', 500.00, 'Avenida Siempre Viva 742', 'Noche', 12);
CALL sp_agregarEmpleado(2, 'Anthony', 'Escobar', 300.00, 'Avenida Siempre Viva 742', 'Mañana', 16);
CALL sp_agregarEmpleado(3, 'Pedro', 'Simon', 350.00, 'Avenida Siempre Viva 742', 'Tarde', 15);
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO LISTAR Empleados --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_listarEmpleados()
BEGIN
    SELECT codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo,direccion,turno,codigoCargoEmpleado 
    FROM Empleados;
END$$
DELIMITER ;

CALL sp_listarEmpleados();
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO buscar Empleados --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_buscarEmpleado(in codigoEmpleadoIn int)
begin
	SELECT codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo,direccion,turno,codigoCargoEmpleado 
    FROM Empleados where codigoEmpleado=codigoEmpleadoIn;
end $$
delimiter ; 
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO ACTUALIZAR Empleados --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_actualizarEmpleado(IN codigoEmpleadoIn INT,IN nombresEmpleadoIn varchar(50),IN apellidosEmpleadoIn varchar(50),
IN sueldoIn decimal(10,2),IN direccionIn varchar(150), turnoIn varchar(15), IN codigoCargoEmpleadoIn int)
BEGIN
    UPDATE Empleados
    SET nombresEmpleado = nombresEmpleadoIn,
        apellidosEmpleado = apellidosEmpleadoIn,
        sueldo = sueldoIn,
        direccion = direccionIn,
        turno = turnoIn,
        codigoCargoEmpleado = codigoCargoEmpleadoIn
    WHERE codigoEmpleado=codigoEmpleadoIn;
END$$
DELIMITER ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO ELIMINAR Empleados --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
DELIMITER $$
CREATE PROCEDURE sp_eliminarEmpleados(IN codigoEmpleadoIn INT)
BEGIN
    DELETE FROM Empleados
    WHERE codigoEmpleado=codigoEmpleadoIn;
END$$

DELIMITER ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||


CREATE TABLE Factura(

numeroDeFactura int auto_increment,
estado varchar(50),
totalFactura decimal(10,2),
fechaFactura varchar(45),
codigoCliente int,
codigoEmpleado int,
PRIMARY KEY PK_numeroDeFactura (numeroDeFactura),
FOREIGN KEY (codigoCliente) REFERENCES Clientes(clienteID) on delete cascade,
FOREIGN KEY (codigoEmpleado) REFERENCES Empleados(codigoEmpleado) on delete cascade

);
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO  Agregar Factura --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_agregarFactura(in estadoIn varchar(50), in fechaFacturaIn varchar(45), in codigoClienteIn int, in codigoEmpleadoIn int)
begin
	insert into Factura (estado,fechaFactura,codigoCliente,codigoEmpleado)
    values(estadoIn, fechaFacturaIn, codigoClienteIn,codigoEmpleadoIn);
    
end $$
delimiter ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO listar Factura --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_listarFactura()
begin
	select
	Factura.numeroDeFactura,
    Factura.estado,
    Factura.totalFactura,
    Factura.fechaFactura,
    Factura.codigoCliente,
    Factura.codigoEmpleado
    from Factura ;
end $$
delimiter ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO buscar Factura --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_buscarFactura(in numeroDeFacturaIn int)
begin
	select 
    Factura.numeroDeFactura,
    Factura.estado,
    Factura.totalFactura,
    Factura.fechaFactura,
    Factura.codigoCliente,
    Factura.codigoEmpleado
    from Factura 
    where Factura.numeroDeFactura=numeroDeFacturaIn;
end $$
delimiter ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO eliminar Factura --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_eliminarFactura(in numeroDeFacturaIn int)
begin
	delete from Factura 
    where Factura.numeroDeFactura=numeroDeFacturaIn;
end $$
delimiter ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO Actualizar Factura --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_actualizarFactura(in numeroDeFacturaIn int,in estadoIn varchar(50),in totalFacturaIn decimal(10,2), in fechaFacturaIn varchar(45), in codigoClienteIn int, in codigoEmpleadoIn int)
begin
	update Factura 
	set 
		Factura.estado=estadoIn,
		Factura.totalFactura=totalFacturaIn,
		Factura.fechaFactura=fechaFacturaIn,
		Factura.codigoCliente=codigoClienteIn,
		Factura.codigoEmpleado=codigoEmpleadoIn
    where
		numeroDeFactura=numeroDeFacturaIn;
end $$
delimiter ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
						-- PROCEDIMIENTO Actualizar SOLO el total de factura --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_actualizarTotalDeFactura(in numeroDeFacturaIn int,in totalFacturaIn decimal(10,2))
begin
	update Factura 
	set 
		totalFactura=totalFacturaIn
    where
		numeroDeFactura=numeroDeFacturaIn;
end $$
delimiter ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
CREATE TABLE DetalleFactura(

codigoDetalleFactura int auto_increment,
precioUnitario decimal(10,2),
cantidad int,
numeroDeFactura int,
codigoProducto varchar(15),
PRIMARY KEY PK_codigoDetalleFactura (codigoDetalleFactura),
FOREIGN KEY (numeroDeFactura) REFERENCES Factura(numeroDeFactura) on delete cascade,
FOREIGN KEY (codigoProducto) REFERENCES Productos(codigoProducto) on delete cascade

);
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO Agregar DetalleFactura --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_agregarDetalleFactura( in cantidadIn int, in numeroDeFacturaIn int, in codigoProductoIn varchar(15))
begin
	insert into DetalleFactura (cantidad,numeroDeFactura,codigoProducto)
    values(cantidadIn,numeroDeFacturaIn,codigoProductoIn);
    
end $$
delimiter ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO Listar DetalleFactura --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_listarDetalleFactura()
begin
	select
	DetalleFactura.codigoDetalleFactura,
    DetalleFactura.precioUnitario,
    DetalleFactura.cantidad,
    DetalleFactura.numeroDeFactura,
    DetalleFactura.codigoProducto
    from DetalleFactura ;
end $$
delimiter ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO Buscar DetalleFactura --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_buscarDetalleFactura(in codigoDetalleFacturaIn int)
begin
	select 
    DetalleFactura.codigoDetalleFactura,
    DetalleFactura.precioUnitario,
    DetalleFactura.cantidad,
    DetalleFactura.numeroDeFactura,
    DetalleFactura.codigoProducto
    from DetalleFactura 
    where DetalleFactura.codigoDetalleFactura=codigoDetalleFacturaIn;
end $$
delimiter ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO Eliminar DetalleFactura --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_eliminarDetalleFactura(in codigoDetalleFacturaIn int)
begin
	delete from DetalleFactura 
    where DetalleFactura.codigoDetalleFactura=codigoDetalleFacturaIn;
end $$
delimiter ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- PROCEDIMIENTO actualizar DetalleFactura --
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
delimiter $$
create procedure sp_actualizarDetalleFactura(in codigoDetalleFacturaIn int, in precioUnitarioIn decimal(10,2), in cantidadIn int, in numeroDeFacturaIn int, in codigoProductoIn varchar(15))
begin
	update DetalleFactura 
	set 
		DetalleFactura.precioUnitario=precioUnitarioIn,
		DetalleFactura.cantidad=cantidadIn,
		DetalleFactura.numeroDeFactura=numeroDeFacturaIn,
		DetalleFactura.codigoProducto=codigoProductoIn
    where
		DetalleFactura.codigoDetalleFactura=codigoDetalleFacturaIn;
end $$
delimiter ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
											-- TRIGGERS FACTURA 
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- -----------------------------------------------------------------------------
-- insertar total de factura con insertar desde detalle factura
-- -----------------------------------------------------------------------------
delimiter $$
create trigger tr_insertarTotalDeFactura_after_Insert
after insert on DetalleFactura
for each row
	begin
    declare totalGet decimal(10,2);
    
    set totalGet=((select sum(precioUnitario*cantidad) from DetalleFactura where DetalleFactura.numeroDeFactura=new.numeroDeFactura ));
    
    call sp_actualizarTotalDeFactura(new.numeroDeFactura, totalGet);
                                    
	end$$
delimiter ;
-- -----------------------------------------------------------------------------
-- actualizar total de factura con update desde detalle factura
-- -----------------------------------------------------------------------------
delimiter $$
create trigger tr_actualizarTotalDeFactura_after_update
after update on DetalleFactura
for each row
	begin
    declare totalGet decimal(10,2);
    
    set totalGet=((select sum(new.precioUnitario*cantidad) from DetalleFactura where DetalleFactura.numeroDeFactura=new.numeroDeFactura ));
    
    call sp_actualizarTotalDeFactura(new.numeroDeFactura, totalGet);
                                    
	end$$
delimiter ;
-- -----------------------------------------------------------------------------
-- calculo del total de factura
-- -----------------------------------------------------------------------------
delimiter $$
create function fn_CalcularTotalDeFactura(numeroDeFacturaIn int) returns decimal(10,2)
deterministic
begin
    declare sumatoriaDeFactura decimal(10,2);
    
    set sumatoriaDeFactura = (select sum(precioUnitario*cantidad) from DetalleFactura 
					where numeroDeFactura=numeroDeFacturaIn) ;
    return sumatoriaDeFactura;
end$$
delimiter ;
-- -----------------------------------------------------------------------------
-- eliminar total de factura en cascada despues de detalle factura
-- -----------------------------------------------------------------------------
delimiter $$
create trigger tr_eliminarTotalFactura_after_delete
after delete on DetalleFactura
for each row
	begin
    declare totalDelete decimal(10,2);
    
    set totalDelete=fn_TotalFactura(old.numeroDeFactura);
    
    call sp_actualizarTotalDeFactura(old.numeroDeFactura, totalDelete);
                                    
	end$$
delimiter ;


-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
										-- TRIGGERS DETALLE FACTURA
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- -----------------------------------------------------------------------------
-- traer el precio unitario DetalleCompra A DetalleFactura
-- -----------------------------------------------------------------------------
delimiter $$
create function fn_getPrecioUnitarioDetalleCompra_DetalleFactura(codigoProductoIn varchar(15)) returns decimal(10,2)
deterministic
begin
	declare precioGet decimal(10,2);
	set precioGet= (select DetalleCompra.costoUnitario from DetalleCompra
    where DetalleCompra.codigoProducto=codigoProductoIn);
	return precioGet;
end$$
delimiter ;
-- -----------------------------------------------------------------------------
-- insertar precioUnitario Detalle factura
-- -----------------------------------------------------------------------------
delimiter $$
create trigger tr_InsertarPrecioUnidadDetalleFactura_Before_Insert
before insert on DetalleFactura
for each row
	begin
        set new.precioUnitario= (select precioUnitario from Productos
		where Productos.codigoProducto=new.codigoProducto);
	end$$
delimiter ;
-- -----------------------------------------------------------------------------
-- actualizar Precio unitario de DetalleFactura
-- -----------------------------------------------------------------------------
delimiter $$
create procedure sp_actualizarPrecioUnitarioDetalleFactura(in codigoProductoIn varchar(15), in precioUnitarioIn decimal(10,2))
begin
	update DetalleFactura 
	set 
		DetalleFactura.precioUnitario=precioUnitarioIn
    where
		DetalleFactura.codigoProducto=codigoProductoIn;
end $$
delimiter ;
-- -----------------------------------------------------------------------------
-- actualizar Precio unitarios en Detalle factura por producto
-- -----------------------------------------------------------------------------
delimiter $$
create trigger tr_actualizarPrecioUnitarioDetalleFactura_after_update
after update on Productos
for each row
	begin
		call sp_actualizarPrecioUnitarioDetalleFactura(new.codigoProducto,
        (select new.precioUnitario from Productos where Productos.codigoProducto=new.codigoProducto));
        
	end$$
delimiter ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
											-- TRIGGERS OTROS
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||

-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
										-- TRIGGERS PRODUCTOS
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- -----------------------------------------------------------------------------
-- insertar precio unitario en Productos desde detalle de factura
-- -----------------------------------------------------------------------------
delimiter $$
create trigger tr_insertarPrecioUnitarioProductos_after_Insert
after insert on DetalleCompra
for each row
	begin
    call sp_actualizarPreciosDeProductos(new.codigoProducto, 
									(fn_getPrecioUnitarioDetalleCompra_DetalleFactura(new.codigoProducto)+
									(fn_getPrecioUnitarioDetalleCompra_DetalleFactura(new.codigoProducto)*0.40)),
                                    -- ------------------------------------------------------------------------------ --
									(fn_getPrecioUnitarioDetalleCompra_DetalleFactura(new.codigoProducto)+
									(fn_getPrecioUnitarioDetalleCompra_DetalleFactura(new.codigoProducto)*0.35)),
                                    -- ------------------------------------------------------------------------------ --
                                    (fn_getPrecioUnitarioDetalleCompra_DetalleFactura(new.codigoProducto)+
									(fn_getPrecioUnitarioDetalleCompra_DetalleFactura(new.codigoProducto)*0.25)),new.cantidad);
	end$$
delimiter ;
-- -----------------------------------------------------------------------------
-- actualizar precio unitario en Productos desde detalle de factura
-- -----------------------------------------------------------------------------
delimiter $$
create trigger tr_actualizarPreciosProductos_after_update
after update on DetalleCompra
for each row
	begin
    call sp_actualizarPreciosDeProductos(new.codigoProducto, 
									(fn_getPrecioUnitarioDetalleCompra_DetalleFactura(new.codigoProducto)+
									(fn_getPrecioUnitarioDetalleCompra_DetalleFactura(new.codigoProducto)*0.40)),
                                    -- ------------------------------------------------------------------------------ --
									(fn_getPrecioUnitarioDetalleCompra_DetalleFactura(new.codigoProducto)+
									(fn_getPrecioUnitarioDetalleCompra_DetalleFactura(new.codigoProducto)*0.35)),
                                    -- ------------------------------------------------------------------------------ --
                                    (fn_getPrecioUnitarioDetalleCompra_DetalleFactura(new.codigoProducto)+
									(fn_getPrecioUnitarioDetalleCompra_DetalleFactura(new.codigoProducto)*0.25)),new.cantidad);
	end$$
delimiter ;
-- -----------------------------------------------------------------------------
-- eliminar todos los precios en Productos desde detalle compra
-- -----------------------------------------------------------------------------
delimiter $$
create trigger tr_eliminarPreciosDeProductos_after_delete
after delete on DetalleCompra
for each row
	begin
    call sp_actualizarPreciosDeProductos(old.codigoProducto, 0,0,0,0);
                                    
	end$$
delimiter ;
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
									-- TRIGGERS COMPRAS/DETALLE
-- ----|--|-|-----------------------------------|-|-|--|-|-------------------------------------------||
-- -----------------------------------------------------------------------------
-- insertar total en compra desde detalle compra insertado
-- -----------------------------------------------------------------------------
delimiter $$
create trigger tr_insertarTotalDeDetalleCompra_after_Insert
after insert on DetalleCompra
for each row
	begin
    declare totalGet decimal(10,2);
    
    set totalGet=((select sum(costoUnitario*cantidad) from DetalleCompra where DetalleCompra.numeroDocumento=new.numeroDocumento));
    
    call sp_actualizarTotalCompra(new.numeroDocumento, total);
                                    
	end$$
delimiter ;
-- -----------------------------------------------------------------------------
-- actualizar total compra compra desde detalle compra actualizado
-- -----------------------------------------------------------------------------
delimiter $$
create trigger tr_actualizarTotalCompra_after_update
after update on DetalleCompra
for each row
	begin
    declare totalGet decimal(10,2);
    
    set totalGet=((select sum(new.costoUnitario*new.cantidad) from DetalleCompra where DetalleCompra.numeroDocumento=new.numeroDocumento));
    
    call sp_actualizarTotalCompra(new.numeroDocumento, totalGet);
                                    
	end$$
delimiter ;
-- -----------------------------------------------------------------------------
-- Obtener/calcuar total de compra
-- -----------------------------------------------------------------------------
delimiter $$
create function fn_CalculoTotalDeCompra(numeroDocumentoIn int) returns decimal(10,2)
deterministic
begin
    declare sumatoriaDeCompra decimal(10,2);
    
    set sumatoriaDeCompra = (select sum(cantidad*costoUnitario) from DetalleCompra 
					where numeroDocumento=numeroDocumentoIn) ;
    return sumatoriaDeCompra;
end$$
delimiter ;
-- -----------------------------------------------------------------------------
-- eliminar el total compra desde detalle compra en cascada
-- -----------------------------------------------------------------------------
delimiter $$
create trigger tr_eliminarTotalCompra_after_delete
after delete on DetalleCompra
for each row
	begin
    declare totalDelete decimal(10,2);
    
    set totalDelete=fn_CalculoTotalDeCompra(old.numeroDocumento);
    
    call sp_actualizarTotalCompra(old.numeroDocumento, totalDelete);
                                    
	end$$
delimiter ;