package org.api.trabalhodegraduacao.dao;

import org.api.trabalhodegraduacao.bancoDeDados.ConexaoDB;
import org.api.trabalhodegraduacao.entities.Curso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public void cadastrar(Curso curso) throws SQLException {
        String sql = "INSERT INTO curso (Codigo, Nome) VALUES (?, ?)";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, curso.getCodigo().toUpperCase());
            pstmt.setString(2, curso.getNome());

            pstmt.executeUpdate();
        }
    }

    public List<Curso> listarTodos() {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM curso ORDER BY Nome";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Curso c = new Curso();
                c.setCodigo(rs.getString("Codigo"));
                c.setNome(rs.getString("Nome"));
                cursos.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }

    public void excluir(String codigo) throws SQLException {
        String sql = "DELETE FROM curso WHERE Codigo = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigo);
            pstmt.executeUpdate();
        }
    }
}