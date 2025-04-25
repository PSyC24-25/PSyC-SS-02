CREATE TABLE IF NOT EXISTS pelicula (
    cod_pelicula INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    duracion INT NOT NULL,
    genero VARCHAR(100),
    director VARCHAR(100),
    sinopsis VARCHAR(2000)
);

CREATE TABLE IF NOT EXISTS sala (
    cod_sala INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL,
    capacidad INT NOT NULL,
    columnas INT NOT NULL
);

CREATE TABLE IF NOT EXISTS emision (
    cod_emision INT AUTO_INCREMENT PRIMARY KEY,
    cod_pelicula INT,
    cod_sala INT,
    fecha TIMESTAMP NOT NULL,
    FOREIGN KEY (cod_pelicula) REFERENCES pelicula(cod_pelicula) ON DELETE CASCADE,
    FOREIGN KEY (cod_sala) REFERENCES sala(cod_sala) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS usuario (
    cod_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL,
    apellido VARCHAR(30) NOT NULL,
    correo VARCHAR(255) UNIQUE NOT NULL,
    num_telefono VARCHAR(15) UNIQUE NOT NULL,
    contrasenya VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS compra (
    cod_compra INT AUTO_INCREMENT PRIMARY KEY,
    cod_usuario INT,
    cod_emision INT,
    precio DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (cod_usuario) REFERENCES usuario(cod_usuario) ON DELETE CASCADE,
    FOREIGN KEY (cod_emision) REFERENCES emision(cod_emision) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS asiento (
    cod_compra INT,
    asiento VARCHAR(10),
    PRIMARY KEY (cod_compra, asiento),
    FOREIGN KEY (cod_compra) REFERENCES compra(cod_compra)
);