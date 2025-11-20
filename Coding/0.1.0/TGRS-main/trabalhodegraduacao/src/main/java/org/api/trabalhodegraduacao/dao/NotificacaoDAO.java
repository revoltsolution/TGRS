package org.api.trabalhodegraduacao.dao;

import org.api.trabalhodegraduacao.bancoDeDados.ConexaoDB;
import org.api.trabalhodegraduacao.entities.Notificacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificacaoDAO {

    public List<Notificacao> buscarUltimasNotificacoes(String emailAluno) throws SQLException {
        List<Notificacao> notificacoes = new ArrayList<>();

        String sql = "SELECT c.data_correcoes, c.status, s.ID_TG, s.Semestre " +
                "FROM correcoes c " +
                "INNER JOIN Secao s ON c.Data_Secao = s.Data AND c.Email_Aluno = s.Email_Aluno AND c.Email_Orientador = s.Email_Orientador " +
                "WHERE c.Email_Aluno = ? " +
                "ORDER BY c.data_correcoes DESC " +
                "LIMIT 5";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emailAluno);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String status = rs.getString("status");
                    int idTg = rs.getInt("ID_TG");

                    String msg = "Nova correção recebida para o TG " + idTg + ". Status: " + status;
                    if ("Aprovada".equalsIgnoreCase(status)) {
                        msg = "PARABÉNS! Sua seção do TG " + idTg + " foi APROVADA!";
                    }

                    Date dataSql = rs.getDate("data_correcoes");
                    if (dataSql != null) {
                        notificacoes.add(new Notificacao(msg, dataSql.toLocalDate()));
                    }
                }
            }
        }
        return notificacoes;
    }

    public List<Notificacao> buscarEnviosRecentesParaProfessor(String emailProfessor) throws SQLException {
        List<Notificacao> notificacoes = new ArrayList<>();

        String sql = "SELECT s.Data, u.Nome_Completo, s.ID_TG " +
                "FROM Secao s " +
                "INNER JOIN usuario u ON s.Email_Aluno = u.Email " +
                "WHERE s.Email_Orientador = ? " +
                "ORDER BY s.Data DESC " +
                "LIMIT 10";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emailProfessor);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String nomeAluno = rs.getString("Nome_Completo");
                    int idTg = rs.getInt("ID_TG");

                    String msg = "O aluno " + nomeAluno + " enviou uma atualização no TG " + idTg;

                    Timestamp dataTs = rs.getTimestamp("Data");
                    if (dataTs != null) {
                        notificacoes.add(new Notificacao(msg, dataTs.toLocalDateTime().toLocalDate()));
                    }
                }
            }
        }
        return notificacoes;
    }
}