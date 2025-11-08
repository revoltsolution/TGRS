package org.api.trabalhodegraduacao.dao;

import org.api.trabalhodegraduacao.bancoDeDados.ConexaoDB;
import org.api.trabalhodegraduacao.entities.Usuario;

import java.sql.*;
import java.time.LocalDate;

public class UsuarioDAO {

    /**
     * Busca um usuário e suas credenciais APENAS pelo e-mail.
     * Usado por BemVindoController.
     */
    public Usuario buscarCredenciaisPorEmail(String email) throws SQLException {
        // Query com LEFT JOIN para buscar o nome do orientador
        String sql = "SELECT u.*, o.Nome_Completo AS Nome_Orientador FROM usuario u " +
                "LEFT JOIN usuario o ON u.Email_Orientador = o.Email " +
                "WHERE u.Email = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Chama o método auxiliar para construir o objeto Usuario
                    return construirUsuario(rs);
                }
            }
        }
        return null; // Retorna null se não encontrar o e-mail
    }

    /**
     * Busca o perfil completo de um usuário.
     * Usado por PerfilAlunoController.
     */
    public Usuario exibirPerfil(String email) throws SQLException {
        // Esta query é idêntica à de 'buscarCredenciaisPorEmail'
        String sql = "SELECT u.*, o.Nome_Completo AS Nome_Orientador FROM usuario u " +
                "LEFT JOIN usuario o ON u.Email_Orientador = o.Email " +
                "WHERE u.Email = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Reutiliza o mesmo construtor de objeto
                    return construirUsuario(rs);
                }
            }
        }
        return null; // Retorna null se não encontrar o usuário
    }

    /**
     * Atualiza o perfil do aluno no banco de dados.
     * Usado por PerfilAlunoController.
     */
    public void atualizar(Usuario usuario) throws SQLException {
        // Atualiza apenas os campos que o perfil pode editar
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

            // Define LinkedIn e GitHub (eles serão null se vierem do Professor)
            pstmt.setString(5, usuario.getLinkedin());
            pstmt.setString(6, usuario.getGitHub());

            // Cláusula WHERE
            pstmt.setString(7, usuario.getEmailCadastrado());

            pstmt.executeUpdate();
        }
    }

    /**
     * Método auxiliar privado para criar um objeto Usuario a partir de um ResultSet.
     * Evita duplicação de código.
     */
    private Usuario construirUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();

        usuario.setFotoPerfil(rs.getString("Foto")); // Corrigido
        usuario.setNomeCompleto(rs.getString("Nome_Completo"));
        usuario.setEmailCadastrado(rs.getString("Email")); // Corrigido
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

        return usuario;
    }
}