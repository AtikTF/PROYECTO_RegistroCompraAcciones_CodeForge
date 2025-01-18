-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS AccionesDB;
USE AccionesDB;

-- Tabla de usuarios
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL
);

-- Tabla de compras
CREATE TABLE compras (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL, 
    nombre_accion VARCHAR(50) NOT NULL,
    fecha_compra DATE NOT NULL,
    cantidad INT NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    valor_actual DECIMAL(10, 2) NOT NULL,
    ganancia_perdida DECIMAL(10,2) NOT NULL,
    ganancia_perdida_porcentaje DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Insertar un usuario en la tabla usuarios
INSERT INTO usuarios (nombre_usuario, contraseña) 
VALUES ('atik', 'atik123');

-- Insertar una compra
INSERT INTO compras (id_usuario, nombre_accion, fecha_compra, cantidad, valor)
VALUES (1, 'AAPL', '2024-11-18', 10, 150.75);

Select * from compras
