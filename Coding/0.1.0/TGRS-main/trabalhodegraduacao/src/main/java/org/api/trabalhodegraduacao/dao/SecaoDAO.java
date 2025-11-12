package org.api.trabalhodegraduacao.dao;

import org.api.trabalhodegraduacao.entities.Secao;
import org.api.trabalhodegraduacao.bancoDeDados.ConexaoDB;
import org.api.trabalhodegraduacao.entities.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SecaoDAO {

    private Connection connection;

    public SecaoDAO(Connection connection) {
        this.connection = connection;
    }

    public SecaoDAO() {
    }

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

            if (secao.getData() != null) {
                stmt.setTimestamp(16, new java.sql.Timestamp(secao.getData().getTime()));
            } else {
                stmt.setTimestamp(16, new java.sql.Timestamp(System.currentTimeMillis()));
            }

            stmt.setInt(17, secao.getIdTG());
            stmt.setString(18, secao.getEmailAluno());
            stmt.setString(19, secao.getEmailOrientador());

            stmt.executeUpdate();
        }
    }

    private Secao mapResultSetToSecao(ResultSet rs) throws SQLException {
        java.util.Date dataCompleta = rs.getTimestamp("data");

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
                dataCompleta,
                rs.getInt("id_tg"),
                rs.getString("email_aluno"),
                rs.getString("email_orientador")
        );
    }

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

            // CORREÇÃO NO ATUALIZAR TAMBÉM
            if (secao.getData() != null) {
                stmt.setTimestamp(16, new java.sql.Timestamp(secao.getData().getTime()));
            } else {
                stmt.setNull(16, Types.TIMESTAMP);
            }

            stmt.setInt(17, secao.getIdTG());
            stmt.setString(18, secao.getEmailAluno());
            stmt.setString(19, secao.getEmailOrientador());
            stmt.setInt(20, secao.getIdSecao());

            stmt.executeUpdate();
        }
    }

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

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM secao WHERE id_secao = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Usuario buscarUsuarioPorEmail(String email) throws SQLException {
        String sql = "SELECT Email, Nome_Completo, Funcao, Senha, Email_Orientador FROM usuario WHERE Email = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Usuario user = new Usuario();
                    user.setEmailCadastrado(rs.getString("Email"));
                    user.setNomeCompleto(rs.getString("Nome_Completo"));
                    user.setFuncao(rs.getString("Funcao"));
                    user.setSenha(rs.getString("Senha"));
                    user.setEmailOrientador(rs.getString("Email_Orientador"));
                    return user;
                }
            }
        }
        return null;
    }
}