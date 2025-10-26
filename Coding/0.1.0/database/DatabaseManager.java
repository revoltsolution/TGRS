package org.api.trabalhodegraduacao.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/";

    public void criarBancoDeDados(String nomeDB){

        String sql = "CREATE DATABASE IF NOT EXISTS "+nomeDB+" CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";

        System.out.println("Tentando criar o banco de dados: " + nomeDB);
        try (Connection conn = DriverManager.getConnection(URL, ConnectionDB.getUSER(), ConnectionDB.getPASS());
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);

            // CORREÇÃO BÔNUS: Use a variável nomeDB na mensagem de sucesso
            System.out.println("Banco de dados '" + nomeDB + "' criado com sucesso (ou já existia).");

        } catch (SQLException e) {
            System.err.println("Erro ao tentar criar o banco de dados!");
            e.printStackTrace();
        }
    }
}