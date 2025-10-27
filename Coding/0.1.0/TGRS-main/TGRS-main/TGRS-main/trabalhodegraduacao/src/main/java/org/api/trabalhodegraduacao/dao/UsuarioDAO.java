package org.api.trabalhodegraduacao.dao;



import org.api.trabalhodegraduacao.entities.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {







    public Usuario buscarCredenciaisPorEmail(String email) throws SQLException {
        // 1. SQL Otimizado: Puxa APENAS as 3 colunas necessárias.
        String sql = "SELECT email, senha, funcao FROM usuario WHERE email = ?";
        Usuario usuario = null;

        try (Connection conn = DB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {

                // Se encontramos uma linha...
                if (rs.next()) {
                    // 2. Extrai os 3 valores
                    String emailDB = rs.getString("email");
                    String senhaDB = rs.getString("senha");
                    String funcaoDB = rs.getString("funcao");

                    usuario = new Usuario(funcaoDB, senhaDB, emailDB);
                }
            }
        }


        return usuario;
    }

    public Usuario buscarCredenciaisPorsenha(String senha) throws SQLException {

        String sql = "SELECT email, senha, funcao FROM usuario WHERE senha = ?";
        Usuario usuario = null;

        try (Connection conn = DB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, senha);

            try (ResultSet rs = pstmt.executeQuery()) {


                if (rs.next()) {

                    String emailDB = rs.getString("email");
                    String senhaDB = rs.getString("senha");
                    String funcaoDB = rs.getString("funcao");


                    usuario = new Usuario(funcaoDB, senhaDB, emailDB);
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

