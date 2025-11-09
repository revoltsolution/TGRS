package org.api.trabalhodegraduacao.dao;

import org.api.trabalhodegraduacao.entities.Secao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SecaoDAO {

    private Connection connection;

    public SecaoDAO(Connection connection) {
        this.connection = connection;
    }

    // ✅ Inserir nova seção
    public void inserir(Secao secao) throws SQLException {
        String sql = "INSERT INTO secao (identificacao_projeto, empresa_parceira, problema, solucao, link_repositorio, " +
                "tecnologias_utilizadas, contribuicoes_pessoais, descricao_soft, descricao_hard, historico_profissional, " +
                "historico_academico, motivacao, ano, periodo, semestre, data, id_tg, email_aluno, email_orientador) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, secao.getIdentificacaoProjeto());
            stmt.setString(2, secao.getEmpresaParceira());
            stmt.setString(3, secao.getProblema());
            stmt.setString(4, secao.getSolucao());
            stmt.setString(5, secao.getLinkRepositorio());
            stmt.setString(6, secao.getTecnologiasUtilizadas());
            stmt.setString(7, secao.getContribuicoesPessoais());
            stmt.setString(8, secao.getDescricaoSoft());
            stmt.setString(9, secao.getDescricaoHard());
            stmt.setString(10, secao.getHistoricoProfissional());
            stmt.setString(11, secao.getHistoricoAcademico());
            stmt.setString(12, secao.getMotivacao());
            stmt.setInt(13, secao.getAno());
            stmt.setString(14, String.valueOf(secao.getPeriodo()));
            stmt.setString(15, String.valueOf(secao.getSemestre()));
            stmt.setDate(16, secao.getData());
            stmt.setInt(17, secao.getIdTG());
            stmt.setString(18, secao.getEmailAluno());
            stmt.setString(19, secao.getEmailOrientador());

            stmt.executeUpdate();
        }
    }

    // ✅ Atualizar seção existente
    public void atualizar(Secao secao) throws SQLException {
        String sql = "UPDATE secao SET identificacao_projeto=?, empresa_parceira=?, problema=?, solucao=?, link_repositorio=?, " +
                "tecnologias_utilizadas=?, contribuicoes_pessoais=?, descricao_soft=?, descricao_hard=?, historico_profissional=?, " +
                "historico_academico=?, motivacao=?, ano=?, periodo=?, semestre=?, data=?, id_tg=?, email_aluno=?, email_orientador=? " +
                "WHERE id_secao=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, secao.getIdentificacaoProjeto());
            stmt.setString(2, secao.getEmpresaParceira());
            stmt.setString(3, secao.getProblema());
            stmt.setString(4, secao.getSolucao());
            stmt.setString(5, secao.getLinkRepositorio());
            stmt.setString(6, secao.getTecnologiasUtilizadas());
            stmt.setString(7, secao.getContribuicoesPessoais());
            stmt.setString(8, secao.getDescricaoSoft());
            stmt.setString(9, secao.getDescricaoHard());
            stmt.setString(10, secao.getHistoricoProfissional());
            stmt.setString(11, secao.getHistoricoAcademico());
            stmt.setString(12, secao.getMotivacao());
            stmt.setInt(13, secao.getAno());
            stmt.setString(14, String.valueOf(secao.getPeriodo()));
            stmt.setString(15, String.valueOf(secao.getSemestre()));
            stmt.setDate(16, secao.getData());
            stmt.setInt(17, secao.getIdTG());
            stmt.setString(18, secao.getEmailAluno());
            stmt.setString(19, secao.getEmailOrientador());
            stmt.setInt(20, secao.getIdSecao());

            stmt.executeUpdate();
        }
    }

    // ✅ Buscar uma seção pelo ID
    public Secao buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM secao WHERE id_secao = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToSecao(rs);
            }
        }
        return null;
    }

    // ✅ Listar todas as seções
    public List<Secao> listarTodas() throws SQLException {
        List<Secao> secoes = new ArrayList<>();
        String sql = "SELECT * FROM secao";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                secoes.add(mapResultSetToSecao(rs));
            }
        }
        return secoes;
    }

    // ✅ Excluir seção
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM secao WHERE id_secao = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // 🔁 Método auxiliar para mapear os dados do ResultSet para o objeto Secao
    private Secao mapResultSetToSecao(ResultSet rs) throws SQLException {
        return new Secao(
                rs.getInt("id_secao"),
                rs.getString("identificacao_projeto"),
                rs.getString("empresa_parceira"),
                rs.getString("problema"),
                rs.getString("solucao"),
                rs.getString("link_repositorio"),
                rs.getString("tecnologias_utilizadas"),
                rs.getString("contribuicoes_pessoais"),
                rs.getString("descricao_soft"),
                rs.getString("descricao_hard"),
                rs.getString("historico_profissional"),
                rs.getString("historico_academico"),
                rs.getString("motivacao"),
                rs.getInt("ano"),
                rs.getString("periodo").charAt(0),
                rs.getString("semestre").charAt(0),
                rs.getDate("data"),
                rs.getInt("id_tg"),
                rs.getString("email_aluno"),
                rs.getString("email_orientador")
        );
    }
}
