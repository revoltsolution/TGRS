package org.api.trabalhodegraduacao.dao;

import org.api.trabalhodegraduacao.bancoDeDados.ConexaoDB;
import org.api.trabalhodegraduacao.entities.Secao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SecaoDAO {

    public Secao buscarSecaoMaisRecente(String emailAluno, String emailOrientador) throws SQLException {
        String sql = "SELECT * FROM Secao " +
                "WHERE Email_Aluno = ? AND Email_Orientador = ? " +
                "ORDER BY Data DESC " +
                "LIMIT 1";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emailAluno);
            pstmt.setString(2, emailOrientador);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return construirSecao(rs);
                }
            }
        }
        return null;
    }
   public Secao buscarUltimaVersaoPorIdTg(String emailAluno, String emailOrientador, int idTg) throws SQLException {
        String sql = "SELECT * FROM Secao " +
                "WHERE Email_Aluno = ? AND Email_Orientador = ? AND ID_TG = ? " +
                "ORDER BY Data DESC " +
                "LIMIT 1";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emailAluno);
            pstmt.setString(2, emailOrientador);
            pstmt.setInt(3, idTg);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return construirSecao(rs);
                }
            }
        }
        return null;
    }

    public void inserirSecao(Secao secao) throws SQLException {

        String sqlInsert = "INSERT INTO Secao (Identificacao_Projeto, Empresa_Parceira, Problema, Solucao, " +
                "Link_Repositorio, Tecnologias_Utilizadas, Contribuicoes_Pessoais, Descricao_Soft, " +
                "Descricao_Hard, Historico_Profissional, Historico_Academico, Motivacao, Ano, Periodo, " +
                "Semestre, ID_TG, " +
                "is_identificacao_ok, is_empresa_ok, is_problema_ok, is_solucao_ok, is_link_ok, is_tecnologias_ok, " +
                "is_contribuicoes_ok, is_softskills_ok, is_hardskills_ok, is_hist_prof_ok, is_hist_acad_ok, " +
                "is_motivacao_ok, is_ano_ok, is_periodo_ok, is_semestre_ok, " +
                "Data, Email_Aluno, Email_Orientador) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?,?)";

        try (Connection conn = ConexaoDB.getConexao()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {

                preencherStatement(pstmt, secao);

                pstmt.setBoolean(17, secao.isIdentificacaoOk());
                pstmt.setBoolean(18, secao.isEmpresaOk());
                pstmt.setBoolean(19, secao.isProblemaOk());
                pstmt.setBoolean(20, secao.isSolucaoOk());
                pstmt.setBoolean(21, secao.isLinkOk());
                pstmt.setBoolean(22, secao.isTecnologiasOk());
                pstmt.setBoolean(23, secao.isContribuicoesOk());
                pstmt.setBoolean(24, secao.isSoftskillsOk());
                pstmt.setBoolean(25, secao.isHardskillsOk());
                pstmt.setBoolean(26, secao.isHistProfOk());
                pstmt.setBoolean(27, secao.isHistAcadOk());
                pstmt.setBoolean(28, secao.isMotivacaoOk());
                pstmt.setBoolean(29, secao.isAnoOk());
                pstmt.setBoolean(30, secao.isPeriodoOk());
                pstmt.setBoolean(31, secao.isSemestreOk());

                pstmt.setTimestamp(32, Timestamp.valueOf(secao.getData()));
                pstmt.setString(33, secao.getEmailAluno());
                pstmt.setString(34, secao.getEmailOrientador());

                pstmt.executeUpdate();
                System.out.println("Nova seção inserida com sucesso.");
            }
        }
    }

    public void atualizarStatusSecao(Secao secao) throws SQLException {
        String sqlUpdate = "UPDATE Secao SET " +
                "is_identificacao_ok = ?, is_empresa_ok = ?, is_problema_ok = ?, is_solucao_ok = ?, is_link_ok = ?, " +
                "is_tecnologias_ok = ?, is_contribuicoes_ok = ?, is_softskills_ok = ?, is_hardskills_ok = ?, " +
                "is_hist_prof_ok = ?, is_hist_acad_ok = ?, is_motivacao_ok = ?, is_ano_ok = ?, is_periodo_ok = ?, is_semestre_ok = ? " +
                "WHERE Data = ? AND Email_Aluno = ? AND Email_Orientador = ?";

        try (Connection conn = ConexaoDB.getConexao()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
                pstmt.setBoolean(1, secao.isIdentificacaoOk());
                pstmt.setBoolean(2, secao.isEmpresaOk());
                pstmt.setBoolean(3, secao.isProblemaOk());
                pstmt.setBoolean(4, secao.isSolucaoOk());
                pstmt.setBoolean(5, secao.isLinkOk());
                pstmt.setBoolean(6, secao.isTecnologiasOk());
                pstmt.setBoolean(7, secao.isContribuicoesOk());
                pstmt.setBoolean(8, secao.isSoftskillsOk());
                pstmt.setBoolean(9, secao.isHardskillsOk());
                pstmt.setBoolean(10, secao.isHistProfOk());
                pstmt.setBoolean(11, secao.isHistAcadOk());
                pstmt.setBoolean(12, secao.isMotivacaoOk());
                pstmt.setBoolean(13, secao.isAnoOk());
                pstmt.setBoolean(14, secao.isPeriodoOk());
                pstmt.setBoolean(15, secao.isSemestreOk());

                pstmt.setTimestamp(16, Timestamp.valueOf(secao.getData()));
                pstmt.setString(17, secao.getEmailAluno());
                pstmt.setString(18, secao.getEmailOrientador());

                pstmt.executeUpdate();
                System.out.println("Status da seção atualizado com sucesso.");
            }
        }
    }

    private void preencherStatement(PreparedStatement pstmt, Secao secao) throws SQLException {
        pstmt.setString(1, secao.getIdentificacaoProjeto());
        pstmt.setString(2, secao.getEmpresaParceira());
        pstmt.setString(3, secao.getProblema());
        pstmt.setString(4, secao.getSolucao());
        pstmt.setString(5, secao.getLinkRepositorio());
        pstmt.setString(6, secao.getTecnologiasUtilizadas());
        pstmt.setString(7, secao.getContribuicoesPessoais());
        pstmt.setString(8, secao.getDescricaoSoft());
        pstmt.setString(9, secao.getDescricaoHard());
        pstmt.setString(10, secao.getHistoricoProfissional());
        pstmt.setString(11, secao.getHistoricoAcademico());
        pstmt.setString(12, secao.getMotivacao());
        pstmt.setInt(13, secao.getAno());
        pstmt.setString(14, String.valueOf(secao.getPeriodo()));
        pstmt.setString(15, String.valueOf(secao.getSemestre()));
        pstmt.setInt(16, secao.getIdTG());
    }

    private Secao construirSecao(ResultSet rs) throws SQLException {
        Secao secao = new Secao();

        Timestamp dataSql = rs.getTimestamp("Data");
        if (dataSql != null) secao.setData(dataSql.toLocalDateTime());
        secao.setEmailAluno(rs.getString("Email_Aluno"));
        secao.setEmailOrientador(rs.getString("Email_Orientador"));

        secao.setIdentificacaoProjeto(rs.getString("Identificacao_Projeto"));
        secao.setEmpresaParceira(rs.getString("Empresa_Parceira"));
        secao.setProblema(rs.getString("Problema"));
        secao.setSolucao(rs.getString("Solucao"));
        secao.setLinkRepositorio(rs.getString("Link_Repositorio"));
        secao.setTecnologiasUtilizadas(rs.getString("Tecnologias_Utilizadas"));
        secao.setContribuicoesPessoais(rs.getString("Contribuicoes_Pessoais"));
        secao.setDescricaoSoft(rs.getString("Descricao_Soft"));
        secao.setDescricaoHard(rs.getString("Descricao_Hard"));
        secao.setHistoricoProfissional(rs.getString("Historico_Profissional"));
        secao.setHistoricoAcademico(rs.getString("Historico_Academico"));
        secao.setMotivacao(rs.getString("Motivacao"));
        secao.setAno(rs.getInt("Ano"));
        String p = rs.getString("Periodo"); if (p != null && !p.isEmpty()) secao.setPeriodo(p.charAt(0));
        String s = rs.getString("Semestre"); if (s != null && !s.isEmpty()) secao.setSemestre(s.charAt(0));
        secao.setIdTG(rs.getInt("ID_TG"));

        secao.setIdentificacaoOk(rs.getBoolean("is_identificacao_ok"));
        secao.setEmpresaOk(rs.getBoolean("is_empresa_ok"));
        secao.setProblemaOk(rs.getBoolean("is_problema_ok"));
        secao.setSolucaoOk(rs.getBoolean("is_solucao_ok"));
        secao.setLinkOk(rs.getBoolean("is_link_ok"));
        secao.setTecnologiasOk(rs.getBoolean("is_tecnologias_ok"));
        secao.setContribuicoesOk(rs.getBoolean("is_contribuicoes_ok"));
        secao.setSoftskillsOk(rs.getBoolean("is_softskills_ok"));
        secao.setHardskillsOk(rs.getBoolean("is_hardskills_ok"));
        secao.setHistProfOk(rs.getBoolean("is_hist_prof_ok"));
        secao.setHistAcadOk(rs.getBoolean("is_hist_acad_ok"));
        secao.setMotivacaoOk(rs.getBoolean("is_motivacao_ok"));
        secao.setAnoOk(rs.getBoolean("is_ano_ok"));
        secao.setPeriodoOk(rs.getBoolean("is_periodo_ok"));
        secao.setSemestreOk(rs.getBoolean("is_semestre_ok"));

        return secao;
    }

    public double buscarProgressoSecao(int idTg, String emailAluno, String emailOrientador) throws SQLException {
        String sql = "SELECT ( " +
                "    (is_identificacao_ok + is_empresa_ok + is_problema_ok + is_solucao_ok + is_link_ok + " +
                "     is_tecnologias_ok + is_contribuicoes_ok + is_softskills_ok + is_hardskills_ok + " +
                "     is_hist_prof_ok + is_hist_acad_ok + is_motivacao_ok + is_ano_ok + is_periodo_ok + is_semestre_ok) / 15.0 " +
                ") AS progresso_decimal " +
                "FROM Secao " +
                "WHERE ID_TG = ? AND Email_Aluno = ? AND Email_Orientador = ? " +
                "ORDER BY Data DESC " +
                "LIMIT 1";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idTg);
            pstmt.setString(2, emailAluno);
            pstmt.setString(3, emailOrientador);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("progresso_decimal");
                }
            }
        }
        return 0.0;
    }
    public Secao buscarSecaoPorDataExata(LocalDateTime data, String emailAluno, String emailOrientador) throws SQLException {
        String sql = "SELECT * FROM Secao WHERE Data = ? AND Email_Aluno = ? AND Email_Orientador = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setTimestamp(1, Timestamp.valueOf(data));
            pstmt.setString(2, emailAluno);
            pstmt.setString(3, emailOrientador);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return construirSecao(rs);
                }
            }
        }
        return null;
    }
}