-- Cria o banco e seleciona ele
CREATE DATABASE TGRS
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

USE TGRS;

-- Tabela Usuário
CREATE TABLE usuario (
  Nome_Completo VARCHAR(40),
  Foto VARCHAR(255),
  Data_Nascimento DATE,
  Email VARCHAR(100),
  Link_Linkedin VARCHAR(255),
  Link_Git VARCHAR(255),
  Sexo CHAR(1),
  Funcao varchar(50),
  Senha VARCHAR(50),
  PRIMARY KEY (Email)
) ENGINE=InnoDB;


-- Tabela TG
CREATE TABLE TG (
  Curso VARCHAR(45),
  Historico_Academico VARCHAR(100),
  Historico_Profissional VARCHAR(100),
  Motivacao VARCHAR(100),
  Link_Repositorio VARCHAR(255),
  Conhecimentos VARCHAR(100),
  Horario_Agendado DATE,
  Problema VARCHAR(100),
  Empresa_Parceira VARCHAR(50),
  Ano INT,
  Periodo CHAR(1),
  Semestre CHAR(1),
  ID_TG INT NOT NULL,
  Email VARCHAR(255),
  foreign key(Email) REFERENCES  usuario(Email),
  PRIMARY KEY (ID_TG, Email)
) ENGINE=InnoDB;

-- Tabela Seções
CREATE TABLE Secoes (
  Contribuicoes VARCHAR(100),
  Tecnologias VARCHAR(150),
  Solucao VARCHAR(100),
  Data DATETIME,
  ID_Secoes INT NOT NULL,
  PRIMARY KEY(ID_Secoes)
) ENGINE=InnoDB;

-- Tabela Correções
CREATE TABLE correcoes (
  data_correcoes DATE,
  status VARCHAR(45),
  comentario VARCHAR(300),
  ID_Secoes INT NOT NULL,
  foreign key(ID_Secoes) REFERENCES Secoes(ID_Secoes),
  PRIMARY KEY (data_correcoes, ID_Secoes)
) ENGINE=InnoDB;

CREATE TABLE Hard_Skills (
Descricao_Hard VARCHAR(150) NOT NULL,
ID_Hard INT NOT NULL,
  PRIMARY KEY (ID_Hard),
  Email VARCHAR(100),
  FOREIGN KEY (email) REFERENCES usuario(Email)
) ENGINE=InnoDB;

CREATE TABLE Soft_Skills (
Descricao_Soft VARCHAR(150) NOT NULL,
ID_Soft INT NOT NULL,
  PRIMARY KEY (ID_Soft),
  Email VARCHAR(100),
  FOREIGN KEY (email) REFERENCES usuario(Email)
) ENGINE=InnoDB;

-- Tabela com chaves primárias e estrangeiras.
CREATE TABLE usuario_has_TG (
  Email_Usuario VARCHAR(100) NOT NULL,
  ID_TG INT NOT NULL,
  Email_TG VARCHAR(255) NOT NULL,
  
  PRIMARY KEY (Email_Usuario, ID_TG, Email_TG),
  
  CONSTRAINT fk_usuario_has_TG_usuario 
    FOREIGN KEY (Email_Usuario)
    REFERENCES usuario (Email)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    
  CONSTRAINT fk_usuario_has_TG_TG 
    FOREIGN KEY (ID_TG, Email_TG)
    REFERENCES TG (ID_TG, Email)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB;












