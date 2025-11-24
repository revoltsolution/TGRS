package org.api.trabalhodegraduacao.bancoDeDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    private static final String URL_BASE = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "TGRSDB";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final String USER = "root";
    private static final String PASS = "1234";

    public static Connection getConexao() throws SQLException {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(
                    URL_BASE + DB_NAME + "?useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false",
                    USER,
                    PASS
            );
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não encontrado. Verifique o pom.xml: " + e.getMessage());
        }
    }

    public static Connection getConexaoSemBanco() throws SQLException {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(
                    URL_BASE + "?allowPublicKeyRetrieval=true&useSSL=false",
                    USER,
                    PASS
            );
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não encontrado: " + e.getMessage());
        }
    }
}