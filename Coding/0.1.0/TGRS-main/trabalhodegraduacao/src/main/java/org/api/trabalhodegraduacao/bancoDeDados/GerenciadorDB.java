package org.api.trabalhodegraduacao.bancoDeDados;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class GerenciadorDB {

    private static final String URL = "jdbc:mysql://localhost:3306/";

    public void criarBancoDeDados(String nomeDB){

        String sql = "CREATE DATABASE IF NOT EXISTS "+nomeDB;

        System.out.println("Tentando criar o banco de dados: " + nomeDB);
        try (Connection conn = DriverManager.getConnection(URL, ConexaoDB.getUSER(), ConexaoDB.getPASS());
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Banco de dados '" + nomeDB + "' criado com sucesso (ou já existia).");

        } catch (SQLException e) {
            System.err.println("Erro ao tentar criar o banco de dados!");
            e.printStackTrace();
        }
    }

    public void criarTodasAsTabelas() {

        String sqlUsuario = "CREATE TABLE IF NOT EXISTS usuario (" +
                "Nome_Completo VARCHAR(100)," +
                "Foto VARCHAR(255)," +
                "Data_Nascimento DATE," +
                "Email VARCHAR(100) PRIMARY KEY," +
                "Email_Orientador VARCHAR(100) NULL," +
                "Link_Linkedin VARCHAR(255)," +
                "Link_Git VARCHAR(255)," +
                "Sexo CHAR(1)," +
                "Funcao VARCHAR(50)," +
                "Senha VARCHAR(50)," +
                "FOREIGN KEY (Email_Orientador) REFERENCES usuario (Email)" +
                ")";

        String sqlTG = "CREATE TABLE IF NOT EXISTS TG (" +
                "ID_TG INT NOT NULL," +
                "Email VARCHAR(100) NOT NULL," +
                "Curso VARCHAR(45)," +
                "Historico_Academico TEXT," +
                "Historico_Profissional TEXT," +
                "Motivacao TEXT," +
                "Link_Repositorio VARCHAR(255)," +
                "Conhecimentos TEXT," +
                "Horario_Agendado DATE," +
                "Problema TEXT," +
                "Empresa_Parceira VARCHAR(50)," +
                "Ano INT," +
                "Periodo CHAR(1)," +
                "Semestre CHAR(1)," +
                "FOREIGN KEY (Email) REFERENCES usuario(Email)," +
                "PRIMARY KEY (ID_TG, Email)" +
                ")";

        String sqlSecoes = "CREATE TABLE IF NOT EXISTS Secoes (" +
                "ID_Secoes INT NOT NULL AUTO_INCREMENT," +
                "Contribuicoes TEXT," +
                "Tecnologias TEXT," +
                "Solucao TEXT," +
                "Data DATETIME," +
                "ID_TG INT NOT NULL," +
                "Email_Aluno VARCHAR(100) NOT NULL," +
                "PRIMARY KEY (ID_Secoes)," +
                "FOREIGN KEY (ID_TG, Email_Aluno) REFERENCES TG(ID_TG, Email)" +
                ")";

        String sqlSoftSkills = "CREATE TABLE IF NOT EXISTS Soft_Skills (" +
                "ID_Soft INT NOT NULL AUTO_INCREMENT," +
                "Descricao_Soft VARCHAR(150) NOT NULL," +
                "Email VARCHAR(100) NOT NULL," +
                "PRIMARY KEY (ID_Soft)," +
                "FOREIGN KEY (email) REFERENCES usuario(Email)" +
                ")";

        String sqlHardSkills = "CREATE TABLE IF NOT EXISTS Hard_Skills (" +
                "ID_Hard INT NOT NULL AUTO_INCREMENT," +
                "Descricao_Hard VARCHAR(150) NOT NULL," +
                "Email VARCHAR(100) NOT NULL," +
                "PRIMARY KEY (ID_Hard)," +
                "FOREIGN KEY (email) REFERENCES usuario(Email)" +
                ")";


        String sqlCorrecoes = "CREATE TABLE IF NOT EXISTS correcoes (" +
                "ID_Correcao INT NOT NULL AUTO_INCREMENT," +
                "data_correcoes DATE," +
                "status VARCHAR(45)," +
                "comentario TEXT," +
                "ID_Secoes INT NOT NULL," +
                "PRIMARY KEY (ID_Correcao)," +
                "FOREIGN KEY (ID_Secoes) REFERENCES Secoes(ID_Secoes)" +
                ")";

        try (Connection conn = ConexaoDB.getConexao();
             Statement stmt = conn.createStatement()) {

            System.out.println("Conectado ao banco RSTGDB. Criando tabelas...");

            stmt.execute(sqlUsuario);
            System.out.println("Tabela 'usuario' OK.");
            this.InserirUsuarios(conn);

            stmt.execute(sqlTG);
            System.out.println("Tabela 'TG' OK.");

            stmt.execute(sqlSecoes);
            System.out.println("Tabela 'Secoes' OK.");

            stmt.execute(sqlSoftSkills);
            System.out.println("Tabela 'Soft_Skills' OK.");

            stmt.execute(sqlHardSkills);
            System.out.println("Tabela 'Hard_Skills' OK.");

            stmt.execute(sqlCorrecoes);
            System.out.println("Tabela 'correcoes' OK.");

            System.out.println("Todas as tabelas foram criadas com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao criar as tabelas!");
            e.printStackTrace();
        }
    }

    private void InserirUsuarios(Connection conn) {

        System.out.println("Inserindo usuários na tabela usando Arquivo.csv, caos já existam, os mesmos serão ignorados).");

        String sql = "INSERT IGNORE INTO usuario (Nome_Completo, Email, Senha, Funcao) VALUES (?, ?, ?, ?)";

        String arquivoCSV = "usuarios.csv";
        int i = 0;

        try (
                InputStream is = getClass().getClassLoader().getResourceAsStream(arquivoCSV);

                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(isr);
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String nome = parts[0].trim();
                    String email = parts[1].trim();
                    String senha = parts[2].trim();
                    String funcao = parts[3].trim();

                    pstmt.setString(1, nome);
                    pstmt.setString(2, email);
                    pstmt.setString(3, senha);
                    pstmt.setString(4, funcao);

                    int affectedRows = pstmt.executeUpdate();
                    if (affectedRows > 0) {
                        i++;
                    }
                }
            }

            System.out.println( i + " novos usuários foram inseridos.");

        } catch (SQLException e) {
            System.err.println("Erro de BANCO DE DADOS ao inserir usuarios a tabela!");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro de ARQUIVO ao ler o " + arquivoCSV);
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Erro: Arquivo '" + arquivoCSV + "' não encontrado na pasta resources!");
            e.printStackTrace();
        }
    }
}