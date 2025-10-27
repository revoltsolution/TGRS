package org.api.trabalhodegraduacao.controller.usuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.Sessao;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Usuario;

import java.sql.SQLException;

public class BemVindoController {

    UsuarioDAO usuario = new UsuarioDAO();



    @FXML public Button bt_Entrar;
    @FXML private TextField txt_Login;

    @FXML
    void btEntrar(ActionEvent event) throws SQLException {
        String login = txt_Login.getText();
        Usuario usuarioEncontrado = usuario.buscarCredenciaisPorEmail(login);

        if (usuarioEncontrado == null)  {
            System.out.println("não encontrado");
            return;
        }

        System.out.println(usuarioEncontrado.getEmailCadastrado());

        Sessao.setUsuarioLogado(usuarioEncontrado);

        System.out.println("Usuário logado e salvo na Sessão: " + Sessao.getUsuarioLogado().getEmailCadastrado());


        String tipo = usuarioEncontrado.getTipo();

        if (tipo.equals("1") || tipo.equals("2")) {
            Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/Autenticacao.fxml", "Bem-vindo", event);
        } else {
            System.out.println("Função desconhecida: " + tipo);
        }
    }


}