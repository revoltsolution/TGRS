package org.api.trabalhodegraduacao.dao;

import org.api.trabalhodegraduacao.bancoDeDados.ConexaoDB;
import org.api.trabalhodegraduacao.entities.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public Usuario buscarCredenciaisPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE Email = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Usuario user = new Usuario(
                            rs.getString("Nome_Completo"),
                            rs.getString("Email"),
                            rs.getString("Curso"),
                            rs.getDate("Data_Nasc"),
                            rs.getString("Linkedin"),
                            rs.getString("GitHub"),
                            rs.getString("Email_Orientador"),
                            rs.getString("Senha")
                    );
                    user.setFuncao(rs.getString("Funcao")); // Preenche a função para o login funcionar
                    user.setFotoPerfil(rs.getString("Foto"));
                    return user;
                }
            }
        }
        return null;
    }

    public Usuario exibirPerfil(String email) throws SQLException {
        String sql = "SELECT u.*, o.Nome_Completo as Nome_Orientador FROM usuario u " +
                "LEFT JOIN usuario o ON u.Email_Orientador = o.Email WHERE u.Email = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String curso = null;
                    try { curso = rs.getString("Curso"); } catch (SQLException e) {}

                    Usuario user = new Usuario(
                            rs.getString("Nome_Completo"),
                            rs.getString("Email"),
                            curso,
                            rs.getDate("Data_Nasc"),
                            rs.getString("Linkedin"),
                            rs.getString("GitHub"),
                            rs.getString("Email_Orientador"),
                            rs.getString("Senha")
                    );

                    try { user.setFotoPerfil(rs.getString("Foto")); } catch (Exception e) {}
                    try { user.setFuncao(rs.getString("Funcao")); } catch (Exception e) {}
                    try { user.setNomeOrientador(rs.getString("Nome_Orientador")); } catch (Exception e) {}

                    return user;
                }
            }
        }
        return null;
    }

    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET Curso=?, Data_Nasc=?, Linkedin=?, GitHub=?, Senha=? WHERE Email=?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getCurso());
            pstmt.setDate(2, usuario.getDataNascimento() != null ? Date.valueOf(usuario.getDataNascimento()) : null);
            pstmt.setString(3, usuario.getLinkedin());
            pstmt.setString(4, usuario.getGitHub());
            pstmt.setString(5, usuario.getSenha());
            pstmt.setString(6, usuario.getEmailCadastrado());
            pstmt.executeUpdate();
        }
    }

    public List<Usuario> listarTodosProfessores() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT u.Nome_Completo, u.Email, u.Funcao, " +
                "p.Orientador, p.Gerenciador, p.Curso_Coordenacao " +
                "FROM usuario u " +
                "LEFT JOIN professor p ON u.Email = p.Email_Professor " +
                "WHERE u.Funcao = 'professor'";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Usuario prof = new Usuario();
                prof.setNomeCompleto(rs.getString("Nome_Completo"));
                prof.setEmailCadastrado(rs.getString("Email"));

                boolean isOrientador = rs.getBoolean("Orientador");
                boolean isGerenciador = rs.getBoolean("Gerenciador");

                String papel = "Apenas Professor";
                if (isGerenciador && isOrientador) papel = "Gerente e Orientador";
                else if (isGerenciador) papel = "Gerente de Curso";
                else if (isOrientador) papel = "Orientador";

                prof.setFuncao(papel);
                prof.setCurso(rs.getString("Curso_Coordenacao"));

                lista.add(prof);
            }
        }
        return lista;
    }

    public void atualizarFuncaoProfessor(String email, boolean isOrientador, boolean isGerenciador, String curso) throws SQLException {
        String sql = "INSERT INTO professor (Email_Professor, Orientador, Gerenciador, Curso_Coordenacao) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE Orientador=?, Gerenciador=?, Curso_Coordenacao=?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setBoolean(2, isOrientador);
            pstmt.setBoolean(3, isGerenciador);
            pstmt.setString(4, isGerenciador ? curso : null);

            pstmt.setBoolean(5, isOrientador);
            pstmt.setBoolean(6, isGerenciador);
            pstmt.setString(7, isGerenciador ? curso : null);

            pstmt.executeUpdate();
        }
    }

    public static class FuncaoProfessor {
        public boolean orientador = false;
        public boolean gerenciador = false;
        public String cursoCoordenacao = null;
    }

    public FuncaoProfessor buscarFuncaoProfessor(String email) {
        FuncaoProfessor papeis = new FuncaoProfessor();
        String sql = "SELECT * FROM professor WHERE Email_Professor = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    papeis.orientador = rs.getBoolean("Orientador");
                    papeis.gerenciador = rs.getBoolean("Gerenciador");
                    papeis.cursoCoordenacao = rs.getString("Curso_Coordenacao");
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return papeis;
    }

    public String buscarEmailGerenteDoCurso(String nomeCurso) throws SQLException {
        String sql = "SELECT Email_Professor FROM professor WHERE Curso_Coordenacao = ? AND Gerenciador = TRUE";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomeCurso);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Email_Professor");
                }
            }
        }
        return null;
    }

    public int contarTotalGerenciadores() throws SQLException {
        String sql = "SELECT COUNT(*) FROM professor WHERE Gerenciador = TRUE";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public List<Usuario> buscarAlunosParaDashboard(String emailProfessor) throws SQLException {
        FuncaoProfessor papeis = buscarFuncaoProfessor(emailProfessor);
        List<Usuario> alunos = new ArrayList<>();

        if (!papeis.orientador && !papeis.gerenciador) {
            return buscarAlunosPorOrientador(emailProfessor);
        }

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT u.*, o.Nome_Completo AS Nome_Orientador, ")
                .append(" (SELECT ((is_identificacao_ok + is_empresa_ok + is_problema_ok + is_solucao_ok + is_link_ok + ")
                .append(" is_tecnologias_ok + is_contribuicoes_ok + is_softskills_ok + is_hardskills_ok + ")
                .append(" is_hist_prof_ok + is_hist_acad_ok + is_motivacao_ok + is_ano_ok + is_periodo_ok + is_semestre_ok) / 15.0) ")
                .append(" FROM Secao s WHERE s.Email_Aluno = u.Email ORDER BY s.Data DESC LIMIT 1) AS progresso_decimal, ")
                .append(" (SELECT s.ID_TG FROM Secao s WHERE s.Email_Aluno = u.Email ORDER BY s.Data DESC LIMIT 1) AS ultimo_id_tg ")
                .append("FROM usuario u ")
                .append("LEFT JOIN usuario o ON u.Email_Orientador = o.Email ")
                .append("WHERE u.Funcao = 'aluno' AND ( ");

        boolean added = false;
        if (papeis.orientador) {
            sql.append(" u.Email_Orientador = ? ");
            added = true;
        }
        if (papeis.gerenciador && papeis.cursoCoordenacao != null) {
            if (added) sql.append(" OR ");
            sql.append(" u.Curso = ? ");
            added = true;
        }
        if (!added) sql.append(" 1=0 ");
        sql.append(" )");

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (papeis.orientador) pstmt.setString(idx++, emailProfessor);
            if (papeis.gerenciador && papeis.cursoCoordenacao != null) pstmt.setString(idx++, papeis.cursoCoordenacao);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) alunos.add(construirUsuario(rs));
            }
        }
        return alunos;
    }

    public List<Usuario> buscarAlunosPorOrientador(String emailOrientador) throws SQLException {
        List<Usuario> alunos = new ArrayList<>();
        String sql = "SELECT u.*, o.Nome_Completo AS Nome_Orientador, " +
                " (SELECT ((is_identificacao_ok + is_empresa_ok + is_problema_ok + is_solucao_ok + is_link_ok + " +
                " is_tecnologias_ok + is_contribuicoes_ok + is_softskills_ok + is_hardskills_ok + " +
                " is_hist_prof_ok + is_hist_acad_ok + is_motivacao_ok + is_ano_ok + is_periodo_ok + is_semestre_ok) / 15.0) " +
                " FROM Secao s WHERE s.Email_Aluno = u.Email ORDER BY s.Data DESC LIMIT 1) AS progresso_decimal " +
                "FROM usuario u " +
                "LEFT JOIN usuario o ON u.Email_Orientador = o.Email " +
                "WHERE u.Email_Orientador = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emailOrientador);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) alunos.add(construirUsuario(rs));
            }
        }
        return alunos;
    }

    private Usuario construirUsuario(ResultSet rs) throws SQLException {
        Usuario user = new Usuario();
        user.setNomeCompleto(rs.getString("Nome_Completo"));
        user.setEmailCadastrado(rs.getString("Email"));
        user.setCurso(rs.getString("Curso"));
        user.setFuncao(rs.getString("Funcao"));
        user.setEmailOrientador(rs.getString("Email_Orientador"));
        user.setNomeOrientador(rs.getString("Nome_Orientador"));
        try {
            user.setProgresso(rs.getDouble("progresso_decimal"));
        } catch(Exception e){}
        try {
            user.setFotoPerfil(rs.getString("Foto"));
        } catch(Exception e){}

        return user;
    }
}