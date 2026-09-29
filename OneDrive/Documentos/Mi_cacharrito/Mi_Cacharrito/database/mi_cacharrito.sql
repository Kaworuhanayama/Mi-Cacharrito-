CREATE DATABASE IF NOT EXISTS mi_cacharrito CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE mi_cacharrito;

CREATE TABLE IF NOT EXISTS vehiculo (
 id_vehiculo BIGINT AUTO_INCREMENT PRIMARY KEY,
 placa VARCHAR(10) NOT NULL UNIQUE,
 tipo_vehiculo VARCHAR(30) NOT NULL,
 color VARCHAR(30) NOT NULL,
 valor_alquiler DOUBLE NOT NULL,
 estado VARCHAR(30) NOT NULL,
 numero_alquiler BIGINT UNIQUE,
 fecha_devolucion DATE
);

CREATE TABLE IF NOT EXISTS usuario (
 id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
 identificacion VARCHAR(30) NOT NULL UNIQUE,
 nombre_completo VARCHAR(100) NOT NULL,
 fecha_expedicion_licencia DATE NOT NULL,
 categoria VARCHAR(10) NOT NULL,
 vigencia DATE NOT NULL,
 correo_electronico VARCHAR(120) NOT NULL UNIQUE,
 numero_telefono VARCHAR(30) NOT NULL,
 password VARCHAR(255) NOT NULL,
 rol VARCHAR(30) NOT NULL
);

INSERT INTO vehiculo (placa,tipo_vehiculo,color,valor_alquiler,estado,numero_alquiler,fecha_devolucion) VALUES
('ABC123','automovil','Rojo',80000,'alquilado',1001,DATE_SUB(CURDATE(),INTERVAL 2 DAY)),
('XYZ789','camioneta','Blanco',120000,'alquilado',1002,CURDATE()),
('MNO456','campero','Negro',95000,'disponible',1003,NULL),
('PQR321','motocicleta','Azul',50000,'disponible',NULL,NULL)
ON DUPLICATE KEY UPDATE placa=VALUES(placa);

INSERT INTO usuario (identificacion,nombre_completo,fecha_expedicion_licencia,categoria,vigencia,correo_electronico,numero_telefono,password,rol) VALUES
('ADMIN001','Administrador Mi Cacharrito','2025-01-10','B1','2028-01-10','admin@micacharrito.com','3000000000','Andres02*','ADMINISTRADOR')
ON DUPLICATE KEY UPDATE identificacion=VALUES(identificacion);
