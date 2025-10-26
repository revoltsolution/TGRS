import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class CriarTabelas {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/TGRS";
        String usuario = "TGRS";
        String senha = "tgrs123";

        Connection conn = null;

        try {
            // Abre conexão
            conn = DriverManager.getConnection(url, usuario, senha);
            Statement stmt = conn.createStatement();

            // Criação das tabelas
            String sqlUsuario = "CREATE TABLE IF NOT EXISTS usuario (" +
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

            String sqlTG = "CREATE TABLE IF NOT EXISTS TG (" +
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

            String sqlSecoes = "CREATE TABLE IF NOT EXISTS Secoes (" +
                    "Contribuicoes VARCHAR(100)," +
                    "Tecnologias VARCHAR(150)," +
                    "Solucao VARCHAR(100)," +
                    "Data DATETIME," +
                    "ID_Secoes INT NOT NULL," +
                    "PRIMARY KEY (ID_Secoes)" +
                    ")";

            String sqlCorrecoes = "CREATE TABLE IF NOT EXISTS correcoes (" +
                    "data_correcoes DATE," +
                    "status VARCHAR(45)," +
                    "comentario VARCHAR(300)," +
                    "ID_Secoes INT NOT NULL," +
                    "FOREIGN KEY (ID_Secoes) REFERENCES Secoes(ID_Secoes)," +
                    "PRIMARY KEY (data_correcoes, ID_Secoes)" +
                    ")";

            String sqlSoftSkills = "CREATE TABLE IF NOT EXISTS Soft_Skills (" +
                    "Descricao_Soft VARCHAR(150) NOT NULL," +
                    "ID_Soft INT NOT NULL," +
                    "PRIMARY KEY (ID_Soft)," +
                    "Email VARCHAR(100)," +
                    "FOREIGN KEY (email) REFERENCES usuario(Email)" +
                    ")";

            String sqlHardSkills = "CREATE TABLE IF NOT EXISTS Hard_Skills (" +
                    "Descricao_Hard VARCHAR(150) NOT NULL," +
                    "ID_Hard INT NOT NULL," +
                    "PRIMARY KEY (ID_Hard)," +
                    "Email VARCHAR(100)," +
                    "FOREIGN KEY (email) REFERENCES usuario(Email)" +
                    ")";

            stmt.execute(sqlUsuario);
            stmt.execute(sqlTG);
            stmt.execute(sqlSecoes);
            stmt.execute(sqlCorrecoes);
            stmt.execute(sqlSoftSkills);
            stmt.execute(sqlHardSkills);

            System.out.println("Tabelas criadas com sucesso!");

            String sqlInsert = "INSERT IGNORE INTO usuario(" +
                    "Nome_Completo, Email, Funcao, Senha)" +
                    "VALUES (?, ?, ?, ?)";

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, "João Gabriel Fernandes de Sousa Silva");
                stmtInsert.setString(2, "joao.silva853@fatec.sp.gov.br");
                stmtInsert.setString(3, "aluno");
                stmtInsert.setString(4, "#@1234");
                stmtInsert.executeUpdate();
                System.out.println("Aluno inserido com sucesso!");
            }

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, "Samir Kassen Moraes Mariano de Siqueira");
                stmtInsert.setString(2, "samir.kassen@fatec.sp.gov.br");
                stmtInsert.setString(3, "aluno");
                stmtInsert.setString(4, "#@1234");
                stmtInsert.executeUpdate();
                System.out.println("Aluno inserido com sucesso!");
            }

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, "Erick Vinicius de Faria");
                stmtInsert.setString(2, "erick.faria01@fatec.sp.gov.br");
                stmtInsert.setString(3, "aluno");
                stmtInsert.setString(4, "#@1234");
                stmtInsert.executeUpdate();
                System.out.println("Aluno inserido com sucesso!");
            }

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, "Pedro Henrique Pereira Quirino");
                stmtInsert.setString(2, "pedro.quirino@fatec.sp.gov.br");
                stmtInsert.setString(3, "aluno");
                stmtInsert.setString(4, "#@1234");
                stmtInsert.executeUpdate();
                System.out.println("Aluno inserido com sucesso!");
            }

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, "Luan Santos");
                stmtInsert.setString(2, "luan.santos@fatec.sp.gov.br");
                stmtInsert.setString(3, "aluno");
                stmtInsert.setString(4, "#@1234");
                stmtInsert.executeUpdate();
                System.out.println("Aluno inserido com sucesso!");
            }

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, "Fernando Santos");
                stmtInsert.setString(2, "fernando.santos@fatec.sp.gov.br");
                stmtInsert.setString(3, "aluno");
                stmtInsert.setString(4, "#@1234");
                stmtInsert.executeUpdate();
                System.out.println("Aluno inserido com sucesso!");
            }

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, "Emanuel Mineda Carneiro");
                stmtInsert.setString(2, "emanuel.mineda@fatec.sp.gov.br");
                stmtInsert.setString(3, "professor");
                stmtInsert.setString(4, "#@1234");
                stmtInsert.executeUpdate();
                System.out.println("Aluno inserido com sucesso!");
            }

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, "Gerson da Penha Neto");
                stmtInsert.setString(2, "gerson.penha@fatec.sp.gov.br");
                stmtInsert.setString(3, "professor");
                stmtInsert.setString(4, "#@1234");
                stmtInsert.executeUpdate();
                System.out.println("Professor inserido com sucesso!");
            }

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, "Claudio Etelvino de Lima");
                stmtInsert.setString(2, "claudio.elima@fatec.sp.gov.br");
                stmtInsert.setString(3, "professor");
                stmtInsert.setString(4, "#@1234");
                stmtInsert.executeUpdate();
                System.out.println("Professor inserido com sucesso!");
            }

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, "Fernando Masanori Ashikaga");
                stmtInsert.setString(2, "fmasanori@fatec.sp.gov.br");
                stmtInsert.setString(3, "professor");
                stmtInsert.setString(4, "#@1234");
                stmtInsert.executeUpdate();
                System.out.println("Professor inserido com sucesso!");
            }

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, "Giuliano Araújo Bertoti");
                stmtInsert.setString(2, "giuliano.bertoti@fatec.sp.gov.br");
                stmtInsert.setString(3, "professor");
                stmtInsert.setString(4, "#@1234");
                stmtInsert.executeUpdate();
                System.out.println("Professor inserido com sucesso!");
            }

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, "Jean Carlos Lourenço Costa");
                stmtInsert.setString(2, "jean.costa4@fatec.sp.gov.br");
                stmtInsert.setString(3, "professor");
                stmtInsert.setString(4, "#@1234");
                stmtInsert.executeUpdate();
                System.out.println("Professor inserido com sucesso!");
            }

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, "Reinaldo Gen Ichiro Arakaki");
                stmtInsert.setString(2, "reinaldo.arakaki@fatec.sp.gov.br");
                stmtInsert.setString(3, "professor");
                stmtInsert.setString(4, "#@1234");
                stmtInsert.executeUpdate();
                System.out.println("Professor inserido com sucesso!");
            }

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, "José Walmir Gonçalves Duque");
                stmtInsert.setString(2, "walmir.duque@fatec.sp.gov.br");
                stmtInsert.setString(3, "professor");
                stmtInsert.setString(4, "#@1234");
                stmtInsert.executeUpdate();
                System.out.println("Professor inserido com sucesso!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}