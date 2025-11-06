package org.api.trabalhodegraduacao.bancoDeDados;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    //UTILIZAR ESSA CLASSE

    //ALTERAR A SENHA E ALINHAR O BANCO DE DADOS PARA "TGRSDB"
    private static final String URL = "jdbc:mysql://localhost:3306/TGRSDB";
    private static final String USER = "root";
    private static final String PASS = "1234";


    public static String getUSER() {return USER;}
    public static String getPASS() {return PASS;}

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}