package org.api.trabalhodegraduacao.dao;

import org.api.trabalhodegraduacao.bancoDeDados.ConexaoDB;
import org.api.trabalhodegraduacao.entities.Correcao;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CorrecaoDAO {

    public void salvar(Correcao correcao) throws SQLException {
        String sql = "INSERT INTO correcoes (data_correcoes, status, Conteudo, Data_Secao, Email_Aluno, Email_Orientador) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoDB.getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf(correcao.getDataCorrecoes()));
            pstmt.setString(2, correcao.getStatus());
            pstmt.setString(3, correcao.getConteudo());
            pstmt.setTimestamp(4, Timestamp.valueOf(correcao.getDataSecao()));
            pstmt.setString(5, correcao.getEmailAluno());
            pstmt.setString(6, correcao.getEmailOrientador());
            pstmt.executeUpdate();
        }
    }

    public Correcao buscarCorrecaoMaisRecente(LocalDateTime dataSecao, String emailAluno, String emailOrientador) throws SQLException {
        String sql = "SELECT * FROM correcoes WHERE Data_Secao = ? AND Email_Aluno = ? AND Email_Orientador = ? ORDER BY ID_Correcao DESC LIMIT 1";
        try (Connection conn = ConexaoDB.getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(dataSecao));
            pstmt.setString(2, emailAluno);
            pstmt.setString(3, emailOrientador);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Correcao c = new Correcao();
                    c.setIdCorrecao(rs.getInt("ID_Correcao"));
                    c.setDataCorrecoes(rs.getDate("data_correcoes").toLocalDate());
                    c.setStatus(rs.getString("status"));
                    c.setConteudo(rs.getString("Conteudo"));
                    return c;
                }
            }
        }
        return null;
    }

    public List<Correcao> buscarHistoricoPorSecao(LocalDateTime dataSecao, String emailAluno) throws SQLException {
        List<Correcao> lista = new ArrayList<>();
        String sql = "SELECT * FROM correcoes WHERE Data_Secao = ? AND Email_Aluno = ? ORDER BY data_correcoes ASC";

        try (Connection conn = ConexaoDB.getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(dataSecao));
            pstmt.setString(2, emailAluno);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Correcao c = new Correcao();
                    c.setDataCorrecoes(rs.getDate("data_correcoes").toLocalDate());
                    c.setStatus(rs.getString("status"));
                    c.setConteudo(rs.getString("Conteudo"));
                    c.setEmailOrientador(rs.getString("Email_Orientador"));
                    lista.add(c);
                }
            }
        }
        return lista;
    }

    public List<Correcao> buscarTodasCorrecoesDoAluno(String emailAluno) throws SQLException {
        return new ArrayList<>();
    }
}