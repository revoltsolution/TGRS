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
                "Foto VARCHAR(255)," +
                "Nome_Completo VARCHAR(100)," +
                "Email VARCHAR(100) PRIMARY KEY NOT NULL," +
                "Curso VARCHAR(45) ," +
                "Data_Nasc DATE," +
                "Email_Orientador VARCHAR(100)," +
                "Linkedin VARCHAR(255)," +
                "GitHub VARCHAR(255)," +
                "Funcao VARCHAR(50)," +
                "Senha VARCHAR(50)," +
                "FOREIGN KEY (Email_Orientador) REFERENCES usuario (Email)" +
                ")";

        String sqlTG = "CREATE TABLE IF NOT EXISTS TG (" +
                "ID_TG INT NOT NULL," +
                "Email VARCHAR(100) NOT NULL," +
                "Conteudo_TG TEXT," +
                "FOREIGN KEY (Email) REFERENCES usuario(Email)," +
                "PRIMARY KEY (ID_TG, Email)" +
                ")";

        // ALTERADO: Removido ID_Secao e definida PK composta (Data, Email_Aluno, Email_Orientador)
        String sqlSecao = "CREATE TABLE IF NOT EXISTS Secao (" +
                "Identificacao_Projeto TEXT," +
                "Empresa_Parceira TEXT," +
                "Problema TEXT," +
                "Solucao TEXT," +
                "Link_Repositorio VARCHAR(255)," +
                "Tecnologias_Utilizadas TEXT," +
                "Contribuicoes_Pessoais TEXT," +
                "Descricao_Soft TEXT," +
                "Descricao_Hard TEXT," +
                "Historico_Profissional TEXT," +
                "Historico_Academico TEXT," +
                "Motivacao TEXT," +
                "Ano INT," +
                "Periodo CHAR(1)," +
                "Semestre CHAR(1)," +

                // --- INÍCIO DAS NOVAS COLUNAS DE STATUS ---
                "is_identificacao_ok TINYINT(1) DEFAULT 0," +
                "is_empresa_ok TINYINT(1) DEFAULT 0," +
                "is_problema_ok TINYINT(1) DEFAULT 0," +
                "is_solucao_ok TINYINT(1) DEFAULT 0," +
                "is_link_ok TINYINT(1) DEFAULT 0," +
                "is_tecnologias_ok TINYINT(1) DEFAULT 0," +
                "is_contribuicoes_ok TINYINT(1) DEFAULT 0," +
                "is_softskills_ok TINYINT(1) DEFAULT 0," +
                "is_hardskills_ok TINYINT(1) DEFAULT 0," +
                "is_hist_prof_ok TINYINT(1) DEFAULT 0," +
                "is_hist_acad_ok TINYINT(1) DEFAULT 0," +
                "is_motivacao_ok TINYINT(1) DEFAULT 0," +
                "is_ano_ok TINYINT(1) DEFAULT 0," +
                "is_periodo_ok TINYINT(1) DEFAULT 0," +
                "is_semestre_ok TINYINT(1) DEFAULT 0," +
                // --- FIM DAS NOVAS COLUNAS ---

                "Data DATETIME NOT NULL," +
                "ID_TG INT NOT NULL," +
                "Email_Aluno VARCHAR(100) NOT NULL," +
                "Email_Orientador VARCHAR(100) NOT NULL," +
                "PRIMARY KEY (Data, Email_Aluno, Email_Orientador)," +
                "FOREIGN KEY (Email_Aluno) REFERENCES usuario(Email)," +
                "FOREIGN KEY (Email_Orientador) REFERENCES usuario(Email)" +
                ")";

        // ALTERADO: Tabela correcoes agora referencia a chave composta de Secao
        String sqlCorrecoes = "CREATE TABLE IF NOT EXISTS correcoes (" +
                "ID_Correcao INT NOT NULL AUTO_INCREMENT," +
                "data_correcoes DATE," +
                "status VARCHAR(45)," +
                "Conteudo TEXT," +
                "Data_Secao DATETIME NOT NULL," +
                "Email_Aluno VARCHAR(100) NOT NULL," +
                "Email_Orientador VARCHAR(100) NOT NULL," +
                "PRIMARY KEY (ID_Correcao)," +
                "FOREIGN KEY (Data_Secao, Email_Aluno, Email_Orientador) REFERENCES Secao(Data, Email_Aluno, Email_Orientador)" +
                ")";

        try (Connection conn = ConexaoDB.getConexao();
             Statement stmt = conn.createStatement()) {

            System.out.println("Conectado ao banco TGRSDB. Criando tabelas...");

            stmt.execute(sqlUsuario);
            System.out.println("Tabela 'usuario' OK.");
            this.InserirUsuarios(conn);

            stmt.execute(sqlTG);
            System.out.println("Tabela 'TG' OK.");

            stmt.execute(sqlSecao);
            System.out.println("Tabela 'Secao' OK.");

            stmt.execute(sqlCorrecoes);
            System.out.println("Tabela 'correcoes' OK.");

            System.out.println("Todas as tabelas foram criadas com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao criar as tabelas!");
            e.printStackTrace();
        }
    }

    private void InserirUsuarios(Connection conn) {

        System.out.println("Inserindo usuários na tabela usando Arquivo.csv, caso já existam, os mesmos serão ignorados.");

        String sql = "INSERT IGNORE INTO usuario (Nome_Completo, Email, Senha, Funcao, Email_Orientador) VALUES (?, ?, ?, ?, ?)";

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

                if (parts.length >= 5) {
                    String nome = parts[0].trim();
                    String email = parts[1].trim();
                    String senha = parts[2].trim();
                    String funcao = parts[3].trim();
                    String emailOrientador = parts[4].trim();

                    pstmt.setString(1, nome);
                    pstmt.setString(2, email);
                    pstmt.setString(3, senha);
                    pstmt.setString(4, funcao);

                    if (emailOrientador.equalsIgnoreCase("null")) {
                        pstmt.setNull(5, java.sql.Types.VARCHAR);
                    } else {
                        pstmt.setString(5, emailOrientador);
                    }

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