CREATE DATABASE IF NOT EXISTS mi_cacharrito
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE mi_cacharrito;

CREATE TABLE IF NOT EXISTS vehiculo (
    id_vehiculo BIGINT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(10) NOT NULL UNIQUE,
    tipo_vehiculo VARCHAR(30) NOT NULL,
    color VARCHAR(30) NOT NULL,
    valor_alquiler DECIMAL(12,2) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    numero_alquiler BIGINT UNIQUE NULL,
    fecha_devolucion DATE NULL
);

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    identificacion VARCHAR(30) NOT NULL UNIQUE,
    nombre_completo VARCHAR(100) NOT NULL,
    fecha_expedicion_licencia VARCHAR(30) NOT NULL,
    categoria VARCHAR(20) NOT NULL,
    vigencia VARCHAR(30) NOT NULL,
    correo_electronico VARCHAR(120) NOT NULL UNIQUE,
    numero_telefono VARCHAR(30) NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(30) NOT NULL
);

INSERT INTO vehiculo
(placa, tipo_vehiculo, color, valor_alquiler, estado, numero_alquiler, fecha_devolucion)
VALUES
('ABC123', 'Automovil', 'Rojo', 80000, 'alquilado', 1001, DATE_SUB(CURDATE(), INTERVAL 2 DAY)),
('DEF456', 'Motocicleta', 'Negro', 50000, 'alquilado', 1002, CURDATE()),
('GHI789', 'Camioneta', 'Blanco', 120000, 'disponible', NULL, NULL),
('JKL321', 'Automovil', 'Azul', 85000, 'disponible', NULL, NULL)
ON DUPLICATE KEY UPDATE placa = VALUES(placa);

INSERT INTO usuario
(identificacion, nombre_completo, fecha_expedicion_licencia, categoria, vigencia,
 correo_electronico, numero_telefono, password, rol)
VALUES
('1000000001', 'Administrador Demo', '2025-01-10', 'B1', '2027-01-10',
 'admin@micacharrito.com', '3000000000', 'admin123', 'ADMINISTRADOR'),
('1000000002', 'Usuario Demo', '2025-02-10', 'B1', '2027-02-10',
 'usuario@micacharrito.com', '3000000001', 'usuario123', 'USUARIO')
ON DUPLICATE KEY UPDATE identificacion = VALUES(identificacion);

-- Consultas rápidas para comprobar las 3 tareas:
-- SELECT * FROM vehiculo WHERE estado = 'alquilado';
-- SELECT * FROM vehiculo WHERE placa = 'ABC123';
-- SELECT * FROM vehiculo WHERE numero_alquiler = 1001;
