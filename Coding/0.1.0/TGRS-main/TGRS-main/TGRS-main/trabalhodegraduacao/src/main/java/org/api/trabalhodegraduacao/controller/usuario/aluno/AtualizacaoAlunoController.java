package org.api.trabalhodegraduacao.controller.usuario.aluno;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.api.trabalhodegraduacao.Application;

public class AtualizacaoAlunoController {

    public ImageView imgVwBackGround;
    public ImageView imgVwMoldura;
    public GridPane gridPaneBotoes;
    public Button bt_TG;
    public Button bt_Secao;
    public Button bt_Perfil;
    public Button bt_Sair;
    @FXML
    private AnchorPane contentPane;
    @FXML
    public void initialize() {
        Application.carregarConteudoPane("/org/api/trabalhodegraduacao/view/usuario/aluno/TGAluno.fxml", contentPane);
    }

    @FXML
    void btSair(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Autenticação", event);
    }
    @FXML
    public void btSecao() {
        Application.carregarConteudoPane("/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml", contentPane);
    }
    @FXML
    public void btTG() {
        Application.carregarConteudoPane("/org/api/trabalhodegraduacao/view/usuario/aluno/TGAluno.fxml", contentPane);
    }
    @FXML
    public void btPerfil() {
        Application.carregarConteudoPane("/org/api/trabalhodegraduacao/view/usuario/aluno/PerfilAluno.fxml", contentPane);
    }

    public void btChat() {
        Application.carregarConteudoPane("/org/api/trabalhodegraduacao/view/usuario/Chat.fxml", contentPane);
    }
}
