package org.api.trabalhodegraduacao.dao;


import javafx.scene.input.DataFormat;
import org.api.trabalhodegraduacao.bancoDeDados.ConexaoDB;
import org.api.trabalhodegraduacao.entities.Usuario;

import java.sql.*;

public class UsuarioDAO {

    public Usuario buscarCredenciaisPorEmail(String email) throws SQLException {
        String sql = "SELECT Email, Senha, Funcao, Nome_Completo FROM usuario WHERE Email = ?";
        Usuario usuario = null;

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    String emailDB = rs.getString("Email");
                    String senhaDB = rs.getString("Senha");
                    String funcaoDB = rs.getString("Funcao");
                    String nomeDB = rs.getString("Nome_Completo");

                    usuario = new Usuario(funcaoDB, senhaDB, emailDB,nomeDB);
                }
            }
        }
        return usuario;
    }


    public Usuario exibirPerfil(String email) throws SQLException {

        // CORREÇÃO 1: Trocamos a coluna 'Email_Aluno' por 'Email'
        String sql = "SELECT Nome_Completo, Email, Curso, Data_Nasc, Linkedin, GitHub, Email_Orientador, Senha  FROM usuario WHERE Email = ?";
        Usuario usuario = null;

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    String nomeDB = rs.getString("Nome_Completo");

                    // CORREÇÃO 2: Lemos da coluna 'Email'
                    String emailDB = rs.getString("Email");

                    String cursoDB = rs.getString("Curso");
                    // java.sql.Date pode retornar NULL, o que é perfeito para nós
                    Date dataNascDB = rs.getDate("Data_Nasc");
                    String linkedinDB = rs.getString("Linkedin");
                    String gitHubDB = rs.getString("GitHub");
                    String emailOrientadorDB = rs.getString("Email_Orientador");
                    String senhaDB = rs.getString("Senha");

                    String nomeOrientador = chamarNomeOrientador(emailOrientadorDB);

                    // CORREÇÃO 3 (Lógica): Passamos o 'emailOrientadorDB' para o construtor,
                    // e não o 'emailDB' duas vezes.
                    usuario = new Usuario(nomeDB, emailDB, cursoDB, dataNascDB, linkedinDB, gitHubDB, emailOrientadorDB, senhaDB);

                    if (usuario != null) {
                        usuario.setNomeOrientador(nomeOrientador);
                    }
                }
            }
        }
        return usuario;
    }
    //REVISAR
    public String chamarNomeOrientador(String email) throws SQLException {
        String sql = "SELECT Nome_Completo  FROM usuario WHERE Email = ?";
        String usuario = null;

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    usuario = rs.getString("Nome_Completo");
                }
            }
        }
        return usuario;
    }

    public void atualizar(Usuario usuario) throws SQLException {

        String sql = "UPDATE Usuario SET Nome_Completo = ?, Senha = ?, Link_Linkedin = ?, Link_Git = ? WHERE Email = ?";
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = DB.getConnection();
            stmt = connection.prepareStatement(sql);

            // 1. Setar os NOVOS VALORES (SET)
            stmt.setString(1, usuario.getNomeCompleto());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getLinkedin());
            stmt.setString(4, usuario.getGitHub()); // Exemplo de um novo campo

            // 2. Usar o EMAIL para IDENTIFICAR A LINHA (WHERE)
            stmt.setString(5, usuario.getEmailCadastrado());

            stmt.executeUpdate();

        } finally {
            // ... Fechar recursos ...
        }
    }


}

