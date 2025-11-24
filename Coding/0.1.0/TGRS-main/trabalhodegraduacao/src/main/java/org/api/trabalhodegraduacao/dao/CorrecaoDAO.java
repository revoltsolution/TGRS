package org.api.trabalhodegraduacao.dao;

import org.api.trabalhodegraduacao.bancoDeDados.ConexaoDB;
import org.api.trabalhodegraduacao.entities.Correcao;
import org.api.trabalhodegraduacao.entities.Secao;
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

        String sql = "SELECT c.*, s.* FROM correcoes c " +
                "INNER JOIN Secao s ON c.Data_Secao = s.Data AND c.Email_Aluno = s.Email_Aluno " +
                "WHERE c.Email_Aluno = ? " +
                "ORDER BY c.data_correcoes ASC";

        try (Connection conn = ConexaoDB.getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emailAluno);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Correcao c = montarCorrecaoComSnapshot(rs);
                    c.setEmailOrientador(rs.getString("Email_Orientador"));
                    lista.add(c);
                }
            }
        }
        return lista;
    }

    public List<Correcao> buscarTodasCorrecoesDoAluno(String emailAluno) throws SQLException {
        List<Correcao> lista = new ArrayList<>();

        String sql = "SELECT c.*, s.ID_TG, s.Identificacao_Projeto, s.Data AS Data_Secao_Full " +
                "FROM correcoes c " +
                "INNER JOIN Secao s ON c.Data_Secao = s.Data AND c.Email_Aluno = s.Email_Aluno " +
                "WHERE c.Email_Aluno = ? " +
                "ORDER BY c.data_correcoes DESC";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emailAluno);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Correcao c = new Correcao();
                    c.setIdCorrecao(rs.getInt("ID_Correcao"));
                    c.setDataCorrecoes(rs.getDate("data_correcoes").toLocalDate());
                    c.setStatus(rs.getString("status"));
                    c.setConteudo(rs.getString("Conteudo"));
                    c.setEmailAluno(emailAluno);
                    c.setEmailOrientador(rs.getString("Email_Orientador"));

                    Timestamp ts = rs.getTimestamp("Data_Secao_Full");
                    if(ts != null) c.setDataSecao(ts.toLocalDateTime());

                    int idTg = rs.getInt("ID_TG");
                    String ident = rs.getString("Identificacao_Projeto");
                    String titulo = "TG " + idTg;
                    if (ident != null && !ident.isEmpty()) {
                        titulo += " - " + ident;
                    }
                    c.setTituloSecao(titulo);

                    lista.add(c);
                }
            }
        }
        return lista;
    }

    private Correcao montarCorrecaoComSnapshot(ResultSet rs) throws SQLException {
        Correcao c = new Correcao();
        c.setDataCorrecoes(rs.getDate("data_correcoes").toLocalDate());
        c.setStatus(rs.getString("status"));
        c.setConteudo(rs.getString("Conteudo"));

        Secao s = new Secao();
        s.setIdentificacaoProjeto(rs.getString("Identificacao_Projeto"));
        s.setEmpresaParceira(rs.getString("Empresa_Parceira"));
        s.setProblema(rs.getString("Problema"));
        s.setSolucao(rs.getString("Solucao"));
        s.setLinkRepositorio(rs.getString("Link_Repositorio"));
        s.setTecnologiasUtilizadas(rs.getString("Tecnologias_Utilizadas"));
        s.setContribuicoesPessoais(rs.getString("Contribuicoes_Pessoais"));
        s.setDescricaoSoft(rs.getString("Descricao_Soft"));
        s.setDescricaoHard(rs.getString("Descricao_Hard"));
        s.setHistoricoProfissional(rs.getString("Historico_Profissional"));
        s.setHistoricoAcademico(rs.getString("Historico_Academico"));
        s.setMotivacao(rs.getString("Motivacao"));

        s.setIdentificacaoOk(rs.getBoolean("is_identificacao_ok"));
        s.setEmpresaOk(rs.getBoolean("is_empresa_ok"));
        s.setProblemaOk(rs.getBoolean("is_problema_ok"));
        s.setSolucaoOk(rs.getBoolean("is_solucao_ok"));
        s.setLinkOk(rs.getBoolean("is_link_ok"));
        s.setTecnologiasOk(rs.getBoolean("is_tecnologias_ok"));
        s.setContribuicoesOk(rs.getBoolean("is_contribuicoes_ok"));
        s.setSoftskillsOk(rs.getBoolean("is_softskills_ok"));
        s.setHardskillsOk(rs.getBoolean("is_hardskills_ok"));
        s.setHistProfOk(rs.getBoolean("is_hist_prof_ok"));
        s.setHistAcadOk(rs.getBoolean("is_hist_acad_ok"));
        s.setMotivacaoOk(rs.getBoolean("is_motivacao_ok"));
        s.setAnoOk(rs.getBoolean("is_ano_ok"));
        s.setPeriodoOk(rs.getBoolean("is_periodo_ok"));
        s.setSemestreOk(rs.getBoolean("is_semestre_ok"));

        c.setDadosVersao(s);
        return c;
    }
}