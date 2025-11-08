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
                "Data_Nascimento DATE," +
                "Email_Orientador VARCHAR(100)," +
                "Link_Linkedin VARCHAR(255)," +
                "Link_Git VARCHAR(255)," +
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

        // --- INÍCIO DAS CORREÇÕES ---

        String sqlSecoes = "CREATE TABLE IF NOT EXISTS Secoes (" +
                "ID_Secao INT NOT NULL AUTO_INCREMENT," + // <-- ADICIONADO: Uma PK simples é muito melhor
                "Identificacao_Projeto TEXT," +
                "Empresa_Parceira TEXT," +
                "Problema TEXT," +
                "Solucao TEXT," +
                "Link_Repositorio VARCHAR(255)," +
                "Tecnologias_Utilizadas TEXT," +
                "Contribuicoes_Pessoais TEXT," +
                "Descricao_Soft TEXT," + // <-- CORRIGIDO: Faltava vírgula
                "Descricao_Hard TEXT," + // <-- CORRIGIDO: Faltava vírgula
                "Historico_Profissional TEXT," +
                "Historico_Academico TEXT," +
                "Motivacao TEXT," +
                "Ano INT," +
                "Periodo CHAR(1)," +
                "Semestre CHAR(1)," +
                "Data DATETIME NOT NULL," +
                "ID_TG INT NOT NULL," +
                "Email_Aluno VARCHAR(100) NOT NULL," +
                "Email_Orientador VARCHAR(100) NOT NULL," + // <-- CORRIGIDO: Renomeado de Email_Professor
                "PRIMARY KEY (ID_Secao)," + // <-- CORRIGIDO: Usando a nova PK
                "UNIQUE KEY UQ_Secao_Unica (Data, Email_Aluno, Email_Orientador)," + // <-- ADICIONADO: Mantém sua regra de negócio original
                "FOREIGN KEY (Email_Aluno) REFERENCES usuario(Email)," + // <-- CORRIGIDO: FK separada
                "FOREIGN KEY (Email_Orientador) REFERENCES usuario(Email)" + // <-- CORRIGIDO: FK separada
                ")";


        String sqlCorrecoes = "CREATE TABLE IF NOT EXISTS correcoes (" +
                "ID_Correcao INT NOT NULL AUTO_INCREMENT," +
                "data_correcoes DATE," +
                "status VARCHAR(45)," +
                "Conteudo TEXT," +
                "ID_Secao INT NOT NULL," + // <-- CORRIGIDO: Renomeado de ID_Secoes para clareza
                "PRIMARY KEY (ID_Correcao)," +
                "FOREIGN KEY (ID_Secao) REFERENCES Secoes(ID_Secao)" + // <-- CORRIGIDO: Referencia a nova PK da tabela Secoes
                ")";

        // --- FIM DAS CORREÇÕES ---


        try (Connection conn = ConexaoDB.getConexao();
             Statement stmt = conn.createStatement()) {

            System.out.println("Conectado ao banco RSTGDB. Criando tabelas...");

            stmt.execute(sqlUsuario);
            System.out.println("Tabela 'usuario' OK.");
            this.InserirUsuarios(conn); // Assumindo que ConexaoDB.getConexao() já está conectado ao DB correto

            stmt.execute(sqlTG);
            System.out.println("Tabela 'TG' OK.");

            stmt.execute(sqlSecoes);
            System.out.println("Tabela 'Secoes' OK.");

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

        // --- MUDANÇA 1: Query atualizada para 5 colunas ---
        String sql = "INSERT IGNORE INTO usuario (Nome_Completo, Email, Senha, Funcao, Email_Orientador) VALUES (?, ?, ?, ?, ?)";

        String arquivoCSV = "usuarios.csv";
        int i = 0;

        try (
                InputStream is = getClass().getClassLoader().getResourceAsStream(arquivoCSV);
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(isr);
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            reader.readLine(); // Pula o cabeçalho
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",");

                // --- MUDANÇA 2: Verificando 5 colunas ---
                if (parts.length >= 5) {
                    String nome = parts[0].trim();
                    String email = parts[1].trim();
                    String senha = parts[2].trim();
                    String funcao = parts[3].trim();
                    String emailOrientador = parts[4].trim(); // <-- MUDANÇA: Lendo a 5ª coluna

                    pstmt.setString(1, nome);
                    pstmt.setString(2, email);
                    pstmt.setString(3, senha);
                    pstmt.setString(4, funcao);

                    // --- MUDANÇA 3: Lógica para tratar "null" do CSV ---
                    if (emailOrientador.equalsIgnoreCase("null")) {
                        pstmt.setNull(5, java.sql.Types.VARCHAR); // Insere NULL de verdade
                    } else {
                        pstmt.setString(5, emailOrientador); // Insere o email do orientador
                    }
                    // --------------------------------------------------

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