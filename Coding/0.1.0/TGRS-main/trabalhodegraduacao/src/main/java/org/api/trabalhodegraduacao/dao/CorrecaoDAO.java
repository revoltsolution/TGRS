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
}