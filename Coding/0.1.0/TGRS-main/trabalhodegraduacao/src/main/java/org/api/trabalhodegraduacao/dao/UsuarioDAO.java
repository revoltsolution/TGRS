package org.api.trabalhodegraduacao.dao;

import org.api.trabalhodegraduacao.bancoDeDados.ConexaoDB;
import org.api.trabalhodegraduacao.entities.Usuario;

import java.sql.*;
import java.util.ArrayList; // Importa o ArrayList
import java.util.List; // Importa o List

public class UsuarioDAO {

    public Usuario buscarCredenciaisPorEmail(String email) throws SQLException {
        String sql = "SELECT u.*, o.Nome_Completo AS Nome_Orientador FROM usuario u " +
                "LEFT JOIN usuario o ON u.Email_Orientador = o.Email " +
                "WHERE u.Email = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return construirUsuario(rs);
                }
            }
        }
        return null;
    }

    public Usuario exibirPerfil(String email) throws SQLException {
        String sql = "SELECT u.*, o.Nome_Completo AS Nome_Orientador FROM usuario u " +
                "LEFT JOIN usuario o ON u.Email_Orientador = o.Email " +
                "WHERE u.Email = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return construirUsuario(rs);
                }
            }
        }
        return null;
    }

    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET Curso = ?, Data_Nasc = ?, Senha = ?, Foto = ?, Linkedin = ?, GitHub = ? " +
                "WHERE Email = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getCurso());

            if (usuario.getDataNascimento() != null) {
                pstmt.setDate(2, java.sql.Date.valueOf(usuario.getDataNascimento()));
            } else {
                pstmt.setNull(2, java.sql.Types.DATE);
            }

            pstmt.setString(3, usuario.getSenha());
            pstmt.setString(4, usuario.getFotoPerfil());

            pstmt.setString(5, usuario.getLinkedin());
            pstmt.setString(6, usuario.getGitHub());

            pstmt.setString(7, usuario.getEmailCadastrado());

            pstmt.executeUpdate();
        }
    }

    /**
     * MÉTODO ATUALIZADO
     * Agora inclui o cálculo de progresso.
     */
    private Usuario construirUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();

        usuario.setFotoPerfil(rs.getString("Foto"));
        usuario.setNomeCompleto(rs.getString("Nome_Completo"));
        usuario.setEmailCadastrado(rs.getString("Email"));
        usuario.setCurso(rs.getString("Curso"));

        Date dataSql = rs.getDate("Data_Nasc");
        if (dataSql != null) {
            usuario.setDataNascimento(dataSql.toLocalDate());
        }

        usuario.setEmailOrientador(rs.getString("Email_Orientador"));
        usuario.setLinkedin(rs.getString("Linkedin"));
        usuario.setGitHub(rs.getString("GitHub"));
        usuario.setFuncao(rs.getString("Funcao"));
        usuario.setSenha(rs.getString("Senha"));
        usuario.setNomeOrientador(rs.getString("Nome_Orientador"));

        // --- MUDANÇA AQUI ---
        // Lê a coluna 'progresso_decimal' que é calculada na query
        // O "try-catch" evita erros se a coluna 'progresso_decimal' não existir (ex: no login)
        try {
            usuario.setProgresso(rs.getDouble("progresso_decimal"));
        } catch (SQLException e) {
            // Ignora o erro se a coluna não for encontrada (ex: no exibirPerfil)
            usuario.setProgresso(0.0);
        }
        // --- FIM DA MUDANÇA ---

        return usuario;
    }

    /**
     * MÉTODO ATUALIZADO
     * Agora calcula o progresso da seção mais recente do aluno.
     */
    public java.util.List<Usuario> buscarAlunosPorOrientador(String emailOrientador) throws SQLException {

        List<Usuario> alunos = new ArrayList<>();

        // Esta query agora usa um Sub-SELECT para calcular o progresso (de 0.0 a 1.0)
        // da seção mais recente de cada aluno.
        String sql = "SELECT " +
                "    u.*, " +
                "    o.Nome_Completo AS Nome_Orientador, " +
                "    ( " +
                "        SELECT ( " +
                "            (is_identificacao_ok + is_empresa_ok + is_problema_ok + is_solucao_ok + is_link_ok + " +
                "             is_tecnologias_ok + is_contribuicoes_ok + is_softskills_ok + is_hardskills_ok + " +
                "             is_hist_prof_ok + is_hist_acad_ok + is_motivacao_ok + is_ano_ok + is_periodo_ok + is_semestre_ok) / 15.0 " +
                "        ) " +
                "        FROM Secao s " +
                "        WHERE s.Email_Aluno = u.Email AND s.Email_Orientador = u.Email_Orientador " +
                "        ORDER BY s.Data DESC " +
                "        LIMIT 1 " +
                "    ) AS progresso_decimal " + // Nome da coluna calculada
                "FROM " +
                "    usuario u " +
                "LEFT JOIN " +
                "    usuario o ON u.Email_Orientador = o.Email " +
                "WHERE " +
                "    u.Funcao = 'aluno' AND u.Email_Orientador = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emailOrientador);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    // O construirUsuario agora vai ler o campo 'progresso_decimal'
                    alunos.add(construirUsuario(rs));
                }
            }
        }
        return alunos;
    }
}