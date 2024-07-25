CREATE DATABASE IF NOT EXISTS `RuedaEnLaETITC`;
USE `RuedaEnLaETITC`;

CREATE TABLE `Estudiantes` (
    `ID` INT(255) NOT NULL,
    `Nombre` VARCHAR(100) NOT NULL,
    `Correo` VARCHAR(100) NOT NULL,
    `Password` VARCHAR(100),
    `Carrera` VARCHAR(50) NOT NULL,
    `Sede` VARCHAR(20) NOT NULL,
    `Estado` VARCHAR(20) NOT NULL,
    `Bicicleta` INT(20),
    PRIMARY KEY (`ID`)
);

CREATE TABLE `Administradores` (
    `ID` INT(255) NOT NULL,
    `Nombre` VARCHAR(100) NOT NULL,
    `Password` VARCHAR(100) NOT NULL,
    `Correo` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`ID`)
);

CREATE TABLE `Seguridad` (
    `ID` INT(255) NOT NULL,
    `Nombre` VARCHAR(100) NOT NULL,
    `Password` VARCHAR(100) NOT NULL,
    `Sede` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`ID`)
);

CREATE TABLE `BicicletaNormal` (
    `NumeroInventario` INT(20) NOT NULL,
    `Serial` INT(20) NOT NULL,
    `Tipo` VARCHAR(100) NOT NULL,
    `Estado` VARCHAR(100) NOT NULL,
    `EstudianteID` INT(20),
    `Sede` VARCHAR(100),
    `tipoFreno` VARCHAR(100) NOT NULL,
    `estadoFreno1` VARCHAR(100) NOT NULL,
    `estadoFreno2` VARCHAR(100) NOT NULL,
    `materialManubrio` VARCHAR(100) NOT NULL,
    `estadoManubrio` VARCHAR(100) NOT NULL,
    `materialMarco` VARCHAR(100) NOT NULL,
    `tamanoMarco` INT(20) NOT NULL,
    `estadoMarco` VARCHAR(100) NOT NULL,
    `materialPedal` VARCHAR(100) NOT NULL,
    `estadoPedal1` VARCHAR(100) NOT NULL,
    `estadoPedal2` VARCHAR(100) NOT NULL,
    `anchoLlanta` INT(20) NOT NULL,
    `diametroRin` INT(20) NOT NULL,
    `numRadios` INT(20) NOT NULL,
    `estadoRueda1` VARCHAR(100) NOT NULL,
    `estadoRueda2` VARCHAR(100) NOT NULL,
    `estadoSillin` VARCHAR(100) NOT NULL,
    `tipoMotor` VARCHAR(100) NOT NULL,
    `estadoMotor` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`NumeroInventario`, `Serial`)
);

CREATE TABLE `ParqueaderoSedes` (
    `ID` INT(1) NOT NULL,
    `Nombre` VARCHAR(100) NOT NULL,
    `CuposTotales` INT(255) NOT NULL,
    `CuposDisponibles` INT(255) NOT NULL,
    PRIMARY KEY (`Nombre`)
);

ALTER TABLE Estudiantes
ADD FOREIGN KEY (Bicicleta) REFERENCES BicicletaNormal(NumeroInventario);

ALTER TABLE BicicletaNormal
ADD FOREIGN KEY (EstudianteID) REFERENCES Estudiantes(ID),
ADD FOREIGN KEY (Sede) REFERENCES ParqueaderoSedes(Nombre);

INSERT INTO `Estudiantes` (ID, Nombre, Correo, Password, Carrera, Sede, Estado) VALUES (0, 'NULL', 'null@itc.edu.co', '1234', 'SISTEMAS', 'CENTRO', 'ACTIVO');
INSERT INTO `Estudiantes` (ID, Nombre, Correo, Password, Carrera, Sede, Estado) VALUES (1, 'TEST', 'test@itc.edu.co', '1234', 'SISTEMAS', 'CENTRO', 'ACTIVO');
INSERT INTO `Estudiantes` (ID, Nombre, Correo, Password, Carrera, Sede, Estado) VALUES (51804744, 'OSWALDO CUBILLIOS', 'oc@itc.edu.co', 'abcd', 'SISTEMAS', 'CENTRO', 'ACTIVO');
INSERT INTO `Estudiantes` (ID, Nombre, Correo, Password, Carrera, Sede, Estado) VALUES (31655237, 'ALFREDO BERNAL', 'ab@itc.edu.co', 'efgh', 'ELECTROMECÁNICA', 'CARVAJAL', 'ACTIVO');
INSERT INTO `Estudiantes` (ID, Nombre, Correo, Password, Carrera, Sede, Estado) VALUES (23607488, 'JOSE CASTRO', 'jc@itc.edu.co', 'ijkl', 'PINDUSTRIALES', 'TINTAL', 'ACTIVO');
INSERT INTO `Estudiantes` (ID, Nombre, Correo, Password, Carrera, Sede, Estado) VALUES (52055047, 'PILAR ORTIZ', 'po@itc.edu.co', 'mnop', 'MECATRÓNICA', 'CARVAJAL', 'ACTIVO');
INSERT INTO `Estudiantes` (ID, Nombre, Correo, Password, Carrera, Sede, Estado) VALUES (31474569, 'FRANCISCO LEGUIZAMON', 'fl@itc.edu.co', 'qrst', 'MECÁNICA', 'CENTRO', 'ACTIVO');

INSERT INTO `Administradores` (ID, Nombre, Password, Correo) VALUES (1, 'TEST', '1234', 'test@itc.edu.co');

INSERT INTO `Seguridad` (ID, Nombre, Password, Sede) VALUES (1, 'TEST', '1234', 'CENTRO');
INSERT INTO `Seguridad` (ID, Nombre, Password, Sede) VALUES (2, 'TEST', '1234', 'CARVAJAL');
INSERT INTO `Seguridad` (ID, Nombre, Password, Sede) VALUES (3, 'TEST', '1234', 'TINTAL');

INSERT INTO `ParqueaderoSedes` (ID, Nombre, CuposTotales, CuposDisponibles) VALUES (0, 'ESTUDIANTE', 0, 0);
INSERT INTO `ParqueaderoSedes` (ID, Nombre, CuposTotales, CuposDisponibles) VALUES (1, 'CENTRO', 200, 150);
INSERT INTO `ParqueaderoSedes` (ID, Nombre, CuposTotales, CuposDisponibles) VALUES (2, 'CARVAJAL', 100, 50);
INSERT INTO `ParqueaderoSedes` (ID, Nombre, CuposTotales, CuposDisponibles) VALUES (3, 'TINTAL', 150, 75);

INSERT INTO `BicicletaNormal` (NumeroInventario, Serial, Tipo, Estado, Sede, tipoFreno, estadoFreno1, estadoFreno2, materialManubrio, estadoManubrio, materialMarco, tamanoMarco, estadoMarco, materialPedal, estadoPedal1, estadoPedal2, anchoLlanta, diametroRin, numRadios, estadoRueda1, estadoRueda2, estadoSillin, tipoMotor, estadoMotor) VALUES (1, 11234, 'NORMAL','FUNCIONAL','CENTRO','ARO','FUNCIONAL','FUNCIONAL','ALUMINIO','FUNCIONAL','ALUMINIO',50,'FUNCIONAL','PLASTICO','FUNCIONAL','FUNCIONAL',2,23,32,'FUNCIONAL','FUNCIONAL','FUNCIONAL','TRACCIONHUMANA','FUNCIONAL');
INSERT INTO `BicicletaNormal` (NumeroInventario, Serial, Tipo, Estado, Sede, tipoFreno, estadoFreno1, estadoFreno2, materialManubrio, estadoManubrio, materialMarco, tamanoMarco, estadoMarco, materialPedal, estadoPedal1, estadoPedal2, anchoLlanta, diametroRin, numRadios, estadoRueda1, estadoRueda2, estadoSillin, tipoMotor, estadoMotor) VALUES (2, 24567, 'NORMAL','FUNCIONAL','CARVAJAL','ARO','FUNCIONAL','FUNCIONAL','ALUMINIO','FUNCIONAL','ALUMINIO',50,'FUNCIONAL','PLASTICO','FUNCIONAL','FUNCIONAL',2,23,32,'FUNCIONAL','FUNCIONAL','FUNCIONAL','TRACCIONHUMANA','FUNCIONAL');
INSERT INTO `BicicletaNormal` (NumeroInventario, Serial, Tipo, Estado, Sede, tipoFreno, estadoFreno1, estadoFreno2, materialManubrio, estadoManubrio, materialMarco, tamanoMarco, estadoMarco, materialPedal, estadoPedal1, estadoPedal2, anchoLlanta, diametroRin, numRadios, estadoRueda1, estadoRueda2, estadoSillin, tipoMotor, estadoMotor) VALUES (3, 31244, 'NORMAL','FUNCIONAL','TINTAL','ARO','FUNCIONAL','FUNCIONAL','ALUMINIO','FUNCIONAL','ALUMINIO',50,'FUNCIONAL','PLASTICO','FUNCIONAL','FUNCIONAL',2,23,32,'FUNCIONAL','FUNCIONAL','FUNCIONAL','TRACCIONHUMANA','FUNCIONAL');
INSERT INTO `BicicletaNormal` (NumeroInventario, Serial, Tipo, Estado, Sede, tipoFreno, estadoFreno1, estadoFreno2, materialManubrio, estadoManubrio, materialMarco, tamanoMarco, estadoMarco, materialPedal, estadoPedal1, estadoPedal2, anchoLlanta, diametroRin, numRadios, estadoRueda1, estadoRueda2, estadoSillin, tipoMotor, estadoMotor) VALUES (4, 45678, 'NORMAL','FUNCIONAL','TINTAL','ARO','FUNCIONAL','FUNCIONAL','ALUMINIO','FUNCIONAL','ALUMINIO',50,'FUNCIONAL','PLASTICO','FUNCIONAL','FUNCIONAL',2,23,32,'FUNCIONAL','FUNCIONAL','FUNCIONAL','TRACCIONHUMANA','FUNCIONAL');
INSERT INTO `BicicletaNormal` (NumeroInventario, Serial, Tipo, Estado, Sede, tipoFreno, estadoFreno1, estadoFreno2, materialManubrio, estadoManubrio, materialMarco, tamanoMarco, estadoMarco, materialPedal, estadoPedal1, estadoPedal2, anchoLlanta, diametroRin, numRadios, estadoRueda1, estadoRueda2, estadoSillin, tipoMotor, estadoMotor) VALUES (5, 58652, 'NORMAL','FUNCIONAL','CARVAJAL','ARO','FUNCIONAL','FUNCIONAL','ALUMINIO','FUNCIONAL','ALUMINIO',50,'FUNCIONAL','PLASTICO','FUNCIONAL','FUNCIONAL',2,23,32,'FUNCIONAL','FUNCIONAL','FUNCIONAL','TRACCIONHUMANA','FUNCIONAL');
INSERT INTO `BicicletaNormal` (NumeroInventario, Serial, Tipo, Estado, Sede, tipoFreno, estadoFreno1, estadoFreno2, materialManubrio, estadoManubrio, materialMarco, tamanoMarco, estadoMarco, materialPedal, estadoPedal1, estadoPedal2, anchoLlanta, diametroRin, numRadios, estadoRueda1, estadoRueda2, estadoSillin, tipoMotor, estadoMotor) VALUES (6, 65672, 'NORMAL','FUNCIONAL','CENTRO','ARO','FUNCIONAL','FUNCIONAL','ALUMINIO','FUNCIONAL','ALUMINIO',50,'FUNCIONAL','PLASTICO','FUNCIONAL','FUNCIONAL',2,23,32,'FUNCIONAL','FUNCIONAL','FUNCIONAL','TRACCIONHUMANA','FUNCIONAL');
INSERT INTO `BicicletaNormal` (NumeroInventario, Serial, Tipo, Estado, Sede, tipoFreno, estadoFreno1, estadoFreno2, materialManubrio, estadoManubrio, materialMarco, tamanoMarco, estadoMarco, materialPedal, estadoPedal1, estadoPedal2, anchoLlanta, diametroRin, numRadios, estadoRueda1, estadoRueda2, estadoSillin, tipoMotor, estadoMotor) VALUES (7, 77853, 'NORMAL','FUNCIONAL','CARVAJAL','ARO','FUNCIONAL','FUNCIONAL','ALUMINIO','FUNCIONAL','ALUMINIO',50,'FUNCIONAL','PLASTICO','FUNCIONAL','FUNCIONAL',2,23,32,'FUNCIONAL','FUNCIONAL','FUNCIONAL','TRACCIONHUMANA','FUNCIONAL');
INSERT INTO `BicicletaNormal` (NumeroInventario, Serial, Tipo, Estado, Sede, tipoFreno, estadoFreno1, estadoFreno2, materialManubrio, estadoManubrio, materialMarco, tamanoMarco, estadoMarco, materialPedal, estadoPedal1, estadoPedal2, anchoLlanta, diametroRin, numRadios, estadoRueda1, estadoRueda2, estadoSillin, tipoMotor, estadoMotor) VALUES (8, 83457, 'NORMAL','FUNCIONAL','CENTRO','ARO','FUNCIONAL','FUNCIONAL','ALUMINIO','FUNCIONAL','ALUMINIO',50,'FUNCIONAL','PLASTICO','FUNCIONAL','FUNCIONAL',2,23,32,'FUNCIONAL','FUNCIONAL','FUNCIONAL','TRACCIONHUMANA','FUNCIONAL');
INSERT INTO `BicicletaNormal` (NumeroInventario, Serial, Tipo, Estado, Sede, tipoFreno, estadoFreno1, estadoFreno2, materialManubrio, estadoManubrio, materialMarco, tamanoMarco, estadoMarco, materialPedal, estadoPedal1, estadoPedal2, anchoLlanta, diametroRin, numRadios, estadoRueda1, estadoRueda2, estadoSillin, tipoMotor, estadoMotor) VALUES (9, 98678, 'NORMAL','FUNCIONAL','CENTRO','ARO','FUNCIONAL','FUNCIONAL','ALUMINIO','FUNCIONAL','ALUMINIO',50,'FUNCIONAL','PLASTICO','FUNCIONAL','FUNCIONAL',2,23,32,'FUNCIONAL','FUNCIONAL','FUNCIONAL','TRACCIONHUMANA','FUNCIONAL');
INSERT INTO `BicicletaNormal` (NumeroInventario, Serial, Tipo, Estado, Sede, tipoFreno, estadoFreno1, estadoFreno2, materialManubrio, estadoManubrio, materialMarco, tamanoMarco, estadoMarco, materialPedal, estadoPedal1, estadoPedal2, anchoLlanta, diametroRin, numRadios, estadoRueda1, estadoRueda2, estadoSillin, tipoMotor, estadoMotor) VALUES (10, 10245, 'NORMAL','FUNCIONAL','CENTRO','ARO','FUNCIONAL','FUNCIONAL','ALUMINIO','FUNCIONAL','ALUMINIO',50,'FUNCIONAL','PLASTICO','FUNCIONAL','FUNCIONAL',2,23,32,'FUNCIONAL','FUNCIONAL','FUNCIONAL','TRACCIONHUMANA','FUNCIONAL');

USE RuedaEnLaETITC;
DELETE FROM Estudiantes WHERE id = 1020730942;