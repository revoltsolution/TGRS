package org.api.trabalhodegraduacao.controller.usuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Usuario;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BemVindoController implements Initializable {

    UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML public Button bt_Entrar;
    @FXML private TextField txt_Login;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SessaoUsuario.getInstance().limparSessao();
        System.out.println("Tela BemVindo carregada, sessão anterior limpa.");
    }

    @FXML
    void btEntrar(ActionEvent event) {
        String login = txt_Login.getText();

        Usuario usuarioEncontrado;

        if (login.isEmpty()) {
            exibirAlerta("Por favor, insira seu email.", Alert.AlertType.WARNING);
            return;
        }

        try {
            usuarioEncontrado = usuarioDAO.buscarCredenciaisPorEmail(login);

        } catch (SQLException e) {
            e.printStackTrace();
            exibirAlerta("Erro ao consultar o banco de dados.", Alert.AlertType.ERROR);
            return;
        }

        if (usuarioEncontrado == null) {
            exibirAlerta("Email não cadastrado ou não encontrado.", Alert.AlertType.ERROR);
            return;
        }

        System.out.println(usuarioEncontrado.getEmailCadastrado());

        SessaoUsuario.getInstance().iniciarSessao(
                usuarioEncontrado.getEmailCadastrado(),
                usuarioEncontrado.getNomeCompleto(),
                usuarioEncontrado.getFuncao(),
                usuarioEncontrado.getSenha()
        );

        System.out.println("Usuário salvo na Sessão: " + SessaoUsuario.getInstance().getEmail());

        String funcao = usuarioEncontrado.getFuncao();

        if ("aluno".equalsIgnoreCase(funcao) || "professor".equalsIgnoreCase(funcao)) {
            Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/Autenticacao.fxml", "Autenticação", event);
        } else {
            exibirAlerta("Função de usuário desconhecida: " + funcao, Alert.AlertType.ERROR);
        }
    }

    private void exibirAlerta(String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setTitle("Atenção");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}