package org.api.trabalhodegraduacao.dao;

import org.api.trabalhodegraduacao.bancoDeDados.ConexaoDB;
import org.api.trabalhodegraduacao.entities.Correcao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.ResultSet; // <-- Para o Erro 2
import java.time.LocalDateTime; // <-- Para o Erro 1

public class CorrecaoDAO {

    /**
     * Salva uma nova correção (devolutiva) no banco de dados.
     */
    public void salvar(Correcao correcao) throws SQLException {

        String sql = "INSERT INTO correcoes (data_correcoes, status, Conteudo, Data_Secao, Email_Aluno, Email_Orientador) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, java.sql.Date.valueOf(correcao.getDataCorrecoes()));
            pstmt.setString(2, correcao.getStatus());
            pstmt.setString(3, correcao.getConteudo());

            // Chave Estrangeira Composta
            pstmt.setTimestamp(4, Timestamp.valueOf(correcao.getDataSecao()));
            pstmt.setString(5, correcao.getEmailAluno());
            pstmt.setString(6, correcao.getEmailOrientador());

            pstmt.executeUpdate();
        }
    }
    public Correcao buscarCorrecaoMaisRecente(LocalDateTime dataSecao, String emailAluno, String emailOrientador) throws SQLException {

        String sql = "SELECT * FROM correcoes " +
                "WHERE Data_Secao = ? AND Email_Aluno = ? AND Email_Orientador = ? " +
                "ORDER BY data_correcoes DESC " + // Pega a mais nova
                "LIMIT 1";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setTimestamp(1, Timestamp.valueOf(dataSecao));
            pstmt.setString(2, emailAluno);
            pstmt.setString(3, emailOrientador);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Correcao correcao = new Correcao();
                    correcao.setIdCorrecao(rs.getInt("ID_Correcao"));
                    correcao.setDataCorrecoes(rs.getDate("data_correcoes").toLocalDate());
                    correcao.setStatus(rs.getString("status"));
                    correcao.setConteudo(rs.getString("Conteudo"));
                    // (Não precisa preencher as chaves estrangeiras)
                    return correcao;
                }
            }
        }
        return null; // Retorna null se não encontrar nenhuma correção
    }
    public java.util.List<Correcao> buscarTodasCorrecoesDoAluno(String emailAluno) throws SQLException {
        java.util.List<Correcao> lista = new java.util.ArrayList<>();

        // Faz JOIN para pegar o ID_TG da seção corrigida
        String sql = "SELECT c.*, s.ID_TG " +
                "FROM correcoes c " +
                "INNER JOIN Secao s ON c.Data_Secao = s.Data AND c.Email_Aluno = s.Email_Aluno AND c.Email_Orientador = s.Email_Orientador " +
                "WHERE c.Email_Aluno = ? " +
                "ORDER BY c.data_correcoes DESC";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emailAluno);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Correcao correcao = new Correcao();
                    correcao.setIdCorrecao(rs.getInt("ID_Correcao"));
                    correcao.setDataCorrecoes(rs.getDate("data_correcoes").toLocalDate());
                    correcao.setStatus(rs.getString("status"));
                    correcao.setConteudo(rs.getString("Conteudo"));

                    // Preenche as chaves para podermos buscar a seção depois
                    if (rs.getTimestamp("Data_Secao") != null) {
                        correcao.setDataSecao(rs.getTimestamp("Data_Secao").toLocalDateTime());
                    }
                    correcao.setEmailAluno(rs.getString("Email_Aluno"));
                    correcao.setEmailOrientador(rs.getString("Email_Orientador"));

                    // Formata o Título baseado no ID_TG
                    int idTg = rs.getInt("ID_TG");
                    correcao.setTituloSecao(converterIdParaTexto(idTg));

                    lista.add(correcao);
                }
            }
        }
        return lista;
    }

    private String converterIdParaTexto(int idTg) {
        switch (idTg) {
            case 1: return "TG 1 - Seção 1";
            case 2: return "TG 1 - Seção 2";
            case 3: return "TG 2 - Seção 1";
            case 4: return "TG 2 - Seção 2";
            case 5: return "TG 2 - Seção 3";
            case 6: return "TG 2 - Seção 4";
            default: return "Seção " + idTg;
        }
    }
}