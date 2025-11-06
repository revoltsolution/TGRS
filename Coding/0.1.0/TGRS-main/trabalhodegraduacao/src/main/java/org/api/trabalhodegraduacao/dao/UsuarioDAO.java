package org.api.trabalhodegraduacao.dao;


import org.api.trabalhodegraduacao.bancoDeDados.ConexaoDB;
import org.api.trabalhodegraduacao.entities.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

