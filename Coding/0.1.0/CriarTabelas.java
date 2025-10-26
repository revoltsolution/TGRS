import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CriarTabelas {
    public static void main(String[] args) {

        String url = "";
        String usuario = "";
        String senha = "";

        String sqlUsuario = "CREATE TABLE usuario (" +
                "Nome_Completo VARCHAR(40)," +
                "Foto VARCHAR(255)," +
                "Data_Nascimento DATE," +
                "Email VARCHAR(100)," +
                "Email_Orientador VARCHAR(100) NULL," +
                "Link_Linkedin VARCHAR(255)," +
                "Link_Git VARCHAR(255)," +
                "Sexo CHAR(1)," +
                "Funcao VARCHAR(50)," +
                "Senha VARCHAR(50)," +
                "PRIMARY KEY (Email)," +
                "FOREIGN KEY (Email_Orientador) REFERENCES usuario (Email)" +
                ")";

        String sqlTG = "CREATE TABLE TG (" +
                "Curso VARCHAR(45)," +
                "Historico_Academico VARCHAR(100)," +
                "Historico_Profissional VARCHAR(100)," +
                "Motivacao VARCHAR(100)," +
                "Link_Repositorio VARCHAR(255)," +
                "Conhecimentos VARCHAR(100)," +
                "Horario_Agendado DATE," +
                "Problema VARCHAR(100)," +
                "Empresa_Parceira VARCHAR(50)," +
                "Ano INT," +
                "Periodo CHAR(1)," +
                "Semestre CHAR(1)," +
                "ID_TG INT NOT NULL," +
                "Email VARCHAR(255)," +
                "FOREIGN KEY (Email) REFERENCES usuario(Email)," +
                "PRIMARY KEY (ID_TG, Email)" +
                ")";

        String sqlSecoes = "CREATE TABLE Secoes (" +
                "Contribuicoes VARCHAR(100)," +
                "Tecnologias VARCHAR(150)," +
                "Solucao VARCHAR(100)," +
                "Data DATETIME," +
                "ID_Secoes INT NOT NULL," +
                "PRIMARY KEY (ID_Secoes)" +
                ")";

        String sqlCorrecoes = "CREATE TABLE correcoes (" +
                "data_correcoes DATE," +
                "status VARCHAR(45)," +
                "comentario VARCHAR(300)," +
                "ID_Secoes INT NOT NULL," +
                "FOREIGN KEY (ID_Secoes) REFERENCES Secoes(ID_Secoes)," +
                "PRIMARY KEY (data_correcoes, ID_Secoes)" +
                ")";

        String sqlSoftSkills = "CREATE TABLE Soft_Skills (" +
                "Descricao_Soft VARCHAR(150) NOT NULL," +
                "ID_Soft INT NOT NULL," +
                "PRIMARY KEY (ID_Soft)," +
                "Email VARCHAR(100)," +
                "FOREIGN KEY (email) REFERENCES usuario(Email)" +
                ")";

        String sqlHardSkills = "CREATE TABLE Hard_Skills (" +
                "Descricao_Hard VARCHAR(150) NOT NULL," +
                "ID_Hard INT NOT NULL," +
                "PRIMARY KEY (ID_Hard)," +
                "Email VARCHAR(100)," +
                "FOREIGN KEY (email) REFERENCES usuario(Email)" +
                ")";

        try (Connection conn = DriverManager.getConnection(url, usuario, senha);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlUsuario);
            stmt.execute(sqlTG);
            stmt.execute(sqlSecoes);
            stmt.execute(sqlCorrecoes);
            stmt.execute(sqlSoftSkills);
            stmt.execute(sqlHardSkills);


            System.out.println("Tabelas criadas com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao criar as tabelas! ");
        }
    }
}