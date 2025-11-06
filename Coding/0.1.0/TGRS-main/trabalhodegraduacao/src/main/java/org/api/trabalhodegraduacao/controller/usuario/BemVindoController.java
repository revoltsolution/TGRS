package org.api.trabalhodegraduacao.controller.usuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Usuario;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BemVindoController implements Initializable {

    UsuarioDAO usuario = new UsuarioDAO();


    @FXML public Button bt_Entrar;
    @FXML private TextField txt_Login;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SessaoUsuario.getInstance().limparSessao();
        System.out.println("Tela BemVindo carregada, sessão anterior limpa.");
    }

    @FXML
    void btEntrar(ActionEvent event) throws SQLException {
        String login = txt_Login.getText();
        Usuario usuarioEncontrado = usuario.buscarCredenciaisPorEmail(login);

        if (usuarioEncontrado == null) {
            System.out.println("Email não encontrado");
            return;
        }

        System.out.println(usuarioEncontrado.getEmailCadastrado());

        SessaoUsuario.getInstance().setUsuarioLogado(
                usuarioEncontrado.getEmailCadastrado(),
                usuarioEncontrado.getNomeCompleto(),
                usuarioEncontrado.getTipo(),
                usuarioEncontrado.getSenha()
        );

        System.out.println("Usuário salvo na Sessão: " + SessaoUsuario.getInstance().getEmail());

        String tipo = usuarioEncontrado.getTipo();

        if (tipo.equals("aluno") || tipo.equals("professor")) {
            Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/Autenticacao.fxml", "Autenticação", event);
        } else {
            System.out.println("Função desconhecida: " + tipo);
        }
    }
}