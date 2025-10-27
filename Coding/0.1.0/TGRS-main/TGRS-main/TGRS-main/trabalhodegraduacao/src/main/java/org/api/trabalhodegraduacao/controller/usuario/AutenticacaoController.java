package org.api.trabalhodegraduacao.controller.usuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Usuario;

import java.sql.SQLException;


public class AutenticacaoController {

    UsuarioDAO usuario = new UsuarioDAO();

    @FXML private TextField txt_Senha;

    @FXML
    void btEntrar(ActionEvent event) throws SQLException {
        String login = txt_Senha.getText();


        Usuario usuarioEncontrado = usuario.buscarCredenciaisPorsenha(login);

        String tipo;
        if (usuarioEncontrado == null) {
            System.out.println("não encontrado");
            return;
        } else {
            tipo = usuarioEncontrado.getTipo();
            if ("1".equals(tipo))

                Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Atualizações", event);
        }
        if ("2".equals(tipo)) {

            Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/AtualizacoesAluno.fxml", "Atualizações", event);
        } else {

        }
    }
@FXML
    void btVoltar(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-Vindo", event);
    }

    public void imvUsuario() {

    }
}