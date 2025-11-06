package org.api.trabalhodegraduacao.dao;



import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

//ENCERRAR O US DESSA CLASSE, UTILIZAR A CLASSE DENTRO DO PACKAGE "bancoDeDados"

    private static final String URL = "jdbc:mysql://localhost:3306/tgrs";
    private static final String USER = "root";
    private static final String PASS = "21122025";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void closeConnection() {
    }
}
