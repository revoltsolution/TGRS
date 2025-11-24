package org.api.trabalhodegraduacao.bancoDeDados;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.sql.ResultSet;

public class GerenciadorDB {

    public void criarBancoDeDados(String nomeBanco) {
        String sql = "CREATE DATABASE IF NOT EXISTS " + nomeBanco;
        try (Connection conn = ConexaoDB.getConexaoSemBanco();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Banco de dados '" + nomeBanco + "' verificado/criado.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar banco: " + e.getMessage());
        }
    }

    public void criarTodasAsTabelas() {
        try (Connection conn = ConexaoDB.getConexao();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS usuario (" +
                    "Email VARCHAR(100) PRIMARY KEY, " +
                    "Nome_Completo VARCHAR(150) NOT NULL, " +
                    "Senha VARCHAR(100) NOT NULL, " +
                    "Funcao VARCHAR(20) NOT NULL, " +
                    "Foto VARCHAR(255), " +
                    "Curso VARCHAR(100), " +
                    "Data_Nasc DATE, " +
                    "Linkedin VARCHAR(200), " +
                    "GitHub VARCHAR(200), " +
                    "Email_Orientador VARCHAR(100), " +
                    "FOREIGN KEY (Email_Orientador) REFERENCES usuario(Email)" +
                    ")");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS curso (" +
                    "Codigo VARCHAR(10) PRIMARY KEY, " +
                    "Nome VARCHAR(150) NOT NULL UNIQUE" +
                    ")");

            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM curso")) {
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.executeUpdate("INSERT INTO curso (Codigo, Nome) VALUES " +
                            "('ADS', 'Análise e Desenvolvimento de Sistemas'), " +
                            "('BD', 'Banco de Dados'), " +
                            "('DSM', 'Desenvolvimento de Software Multiplataforma'), " +
                            "('GPI', 'Gestão da Produção Industrial')");
                    System.out.println("Cursos padrão inseridos (Tabela estava vazia).");
                }
            }

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS professor (" +
                    "Email_Professor VARCHAR(100) PRIMARY KEY, " +
                    "Orientador BOOLEAN DEFAULT FALSE, " +
                    "Gerenciador BOOLEAN DEFAULT FALSE, " +
                    "Curso_Coordenacao VARCHAR(150), " +
                    "FOREIGN KEY (Email_Professor) REFERENCES usuario(Email)" +
                    ")");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Secao (" +
                    "ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "Identificacao_Projeto TEXT, Empresa_Parceira VARCHAR(200), Problema TEXT, Solucao TEXT, " +
                    "Link_Repositorio VARCHAR(255), Tecnologias_Utilizadas TEXT, Contribuicoes_Pessoais TEXT, " +
                    "Descricao_Soft TEXT, Descricao_Hard TEXT, Historico_Profissional TEXT, Historico_Academico TEXT, " +
                    "Motivacao TEXT, Ano INT, Periodo CHAR(1), Semestre CHAR(1), ID_TG INT, " +
                    "is_identificacao_ok BOOLEAN DEFAULT 0, is_empresa_ok BOOLEAN DEFAULT 0, is_problema_ok BOOLEAN DEFAULT 0, " +
                    "is_solucao_ok BOOLEAN DEFAULT 0, is_link_ok BOOLEAN DEFAULT 0, is_tecnologias_ok BOOLEAN DEFAULT 0, " +
                    "is_contribuicoes_ok BOOLEAN DEFAULT 0, is_softskills_ok BOOLEAN DEFAULT 0, is_hardskills_ok BOOLEAN DEFAULT 0, " +
                    "is_hist_prof_ok BOOLEAN DEFAULT 0, is_hist_acad_ok BOOLEAN DEFAULT 0, is_motivacao_ok BOOLEAN DEFAULT 0, " +
                    "is_ano_ok BOOLEAN DEFAULT 0, is_periodo_ok BOOLEAN DEFAULT 0, is_semestre_ok BOOLEAN DEFAULT 0, " +
                    "Data DATETIME, Email_Aluno VARCHAR(100), Email_Orientador VARCHAR(100), " +
                    "FOREIGN KEY (Email_Aluno) REFERENCES usuario(Email), " +
                    "FOREIGN KEY (Email_Orientador) REFERENCES usuario(Email)" +
                    ")");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS correcoes (" +
                    "ID_Correcao INT AUTO_INCREMENT PRIMARY KEY, " +
                    "data_correcoes DATE, status VARCHAR(50), Conteudo TEXT, " +
                    "Data_Secao DATETIME, Email_Aluno VARCHAR(100), Email_Orientador VARCHAR(100), " +
                    "FOREIGN KEY (Email_Aluno) REFERENCES usuario(Email)" +
                    ")");


            System.out.println("Tabelas verificadas.");

        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }

    public void popularUsuariosDoCSV(String nomeArquivo) {
        try (InputStream is = getClass().getResourceAsStream("/" + nomeArquivo)) {
            processarStreamCSV(is, "Resources: " + nomeArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void popularUsuariosDeArquivoExterno(File arquivo) {
        try (InputStream is = new FileInputStream(arquivo)) {
            processarStreamCSV(is, "Arquivo Externo: " + arquivo.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processarStreamCSV(InputStream is, String origem) {
        if (is == null) {
            System.err.println("Erro: InputStream nulo em " + origem);
            return;
        }

        String sqlUsuario = "INSERT IGNORE INTO usuario (Nome_Completo, Email, Senha, Funcao, Email_Orientador, Curso) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlProfessor = "INSERT IGNORE INTO professor (Email_Professor, Orientador, Gerenciador, Curso_Coordenacao) VALUES (?, ?, ?, ?)";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is));
             Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmtUser = conn.prepareStatement(sqlUsuario);
             PreparedStatement pstmtProf = conn.prepareStatement(sqlProfessor)) {

            System.out.println("Iniciando processamento de: " + origem);
            String linha;
            br.readLine();

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",", -1);

                for(int i = 0; i < dados.length; i++) {
                    dados[i] = dados[i].trim();
                    if (dados[i].equalsIgnoreCase("null")) dados[i] = "";
                }

                if (dados.length >= 4) {
                    String nome = dados[0];
                    String email = dados[1];
                    String senha = dados[2];
                    String funcao = dados[3];
                    String emailOrientador = (dados.length > 4 && !dados[4].isEmpty()) ? dados[4] : null;
                    String cursoAluno = (dados.length > 5 && !dados[5].isEmpty()) ? dados[5] : null;

                    pstmtUser.setString(1, nome);
                    pstmtUser.setString(2, email);
                    pstmtUser.setString(3, senha);
                    pstmtUser.setString(4, funcao);

                    if (emailOrientador != null) pstmtUser.setString(5, emailOrientador);
                    else pstmtUser.setNull(5, Types.VARCHAR);

                    if (cursoAluno != null) pstmtUser.setString(6, cursoAluno);
                    else pstmtUser.setNull(6, Types.VARCHAR);

                    try {
                        pstmtUser.executeUpdate();
                    } catch (SQLException e) {
                    }

                    if ("professor".equalsIgnoreCase(funcao) && dados.length > 6) {
                        boolean isOrientador = (!dados[6].isEmpty()) && Boolean.parseBoolean(dados[6]);
                        boolean isGerenciador = (dados.length > 7 && !dados[7].isEmpty()) && Boolean.parseBoolean(dados[7]);
                        String cursoCoord = (dados.length > 8 && !dados[8].isEmpty()) ? dados[8] : null;

                        pstmtProf.setString(1, email);
                        pstmtProf.setBoolean(2, isOrientador);
                        pstmtProf.setBoolean(3, isGerenciador);

                        if (cursoCoord != null) pstmtProf.setString(4, cursoCoord);
                        else pstmtProf.setNull(4, Types.VARCHAR);

                        try {
                            pstmtProf.executeUpdate();
                        } catch (SQLException e) {
                            System.err.println("Erro professor: " + e.getMessage());
                        }
                    }
                }
            }
            System.out.println("Fim do processamento.");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}