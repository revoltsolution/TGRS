-- Cria o banco e seleciona ele
CREATE DATABASE TGRS
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

USE TGRS;

-- Tabela Usuário
CREATE TABLE usuario (
  CPF CHAR(11) NOT NULL,
  Nome_Completo VARCHAR(40),
  Data_Nascimento DATE,
  Email_Pessoal VARCHAR(100),
  Email_FATEC VARCHAR(100),
  Sexo CHAR(1),
  Tipo VARCHAR(20),
  Orientador VARCHAR(50),
  Senha VARCHAR(50),
  PRIMARY KEY (CPF)
) ENGINE=InnoDB;

-- Tabela TG
CREATE TABLE TG (
  Link_Linkedin VARCHAR(255) NOT NULL,
  Foto VARCHAR(255),
  Curso VARCHAR(45),
  Historico_Academico VARCHAR(100),
  Historico_Profissional VARCHAR(100),
  Motivacao VARCHAR(100),
  Link_Git VARCHAR(255),
  Conhecimentos VARCHAR(100),
  PRIMARY KEY (Link_Linkedin)
) ENGINE=InnoDB;

-- Tabela Seções
CREATE TABLE Secoes (
  ID_Secoes INT NOT NULL AUTO_INCREMENT,
  Soft_Skills VARCHAR(100),
  Hard_Skills VARCHAR(100),
  Contribuicoes VARCHAR(100),
  Link_Repositorio VARCHAR(255),
  Solucao VARCHAR(100),
  Problema VARCHAR(100),
  Empresa_Parceira VARCHAR(50),
  Ano INT,
  Semestre_curso CHAR(1),
  Semestre_Ano CHAR(1),
  TG_Link_Linkedin VARCHAR(255),
  PRIMARY KEY (ID_Secoes),
  CONSTRAINT fk_secoes_TG FOREIGN KEY (TG_Link_Linkedin)
    REFERENCES TG (Link_Linkedin)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Tabela Correções
CREATE TABLE correcoes (
  ID_Correcoes INT NOT NULL AUTO_INCREMENT,
  data DATE,
  status VARCHAR(45),
  comentario VARCHAR(300),
  ID_Secoes INT,
  PRIMARY KEY (ID_Correcoes),
  CONSTRAINT fk_correcoes_secoes FOREIGN KEY (ID_Secoes)
    REFERENCES Secoes (ID_Secoes)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Tabela de relacionamento entre usuário e TG
CREATE TABLE usuario_has_TG (
  usuario_CPF CHAR(11) NOT NULL,
  TG_Link_Linkedin VARCHAR(255) NOT NULL,
  PRIMARY KEY (usuario_CPF, TG_Link_Linkedin),
  CONSTRAINT fk_usuario_has_TG_usuario FOREIGN KEY (usuario_CPF)
    REFERENCES usuario (CPF)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_usuario_has_TG_TG FOREIGN KEY (TG_Link_Linkedin)
    REFERENCES TG (Link_Linkedin)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB;
