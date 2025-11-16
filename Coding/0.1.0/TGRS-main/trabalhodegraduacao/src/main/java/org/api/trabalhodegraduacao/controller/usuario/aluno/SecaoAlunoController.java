package org.api.trabalhodegraduacao.controller.usuario.aluno;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.api.trabalhodegraduacao.Application;

public class SecaoAlunoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bt_Sair;

    @FXML
    void devolutivasGeral(ActionEvent event) {
        // 1. O caminho para o FXML
        String fxmlPath = "/org/api/trabalhodegraduacao/view/usuario/aluno/DevolutivasAluno.fxml";

        // 2. Um título para a janela
        String title = "TGRS - Devolutivas";

        // 3. Chame sua função com os TRÊS argumentos
        Application.carregarNovaCena(fxmlPath, title, event);
    }
    @FXML
    private Button bt_perfil_geral;

    @FXML
    private Button bt_secao_geral;

    @FXML
    private Button bt_tela_inicial;

    @FXML
    private Button bt_tg_geral;


    @FXML
    void initialize() {
        assert bt_Sair != null : "fx:id=\"bt_Sair\" was not injected: check your FXML file 'SecaoAluno.fxml'.";
        assert bt_perfil_geral != null : "fx:id=\"bt_perfil_geral\" was not injected: check your FXML file 'SecaoAluno.fxml'.";
        assert bt_secao_geral != null : "fx:id=\"bt_secao_geral\" was not injected: check your FXML file 'SecaoAluno.fxml'.";
        assert bt_tela_inicial != null : "fx:id=\"bt_tela_inicial\" was not injected: check your FXML file 'SecaoAluno.fxml'.";
        assert bt_tg_geral != null : "fx:id=\"bt_tg_geral\" was not injected: check your FXML file 'SecaoAluno.fxml'.";

    }
    public void sair(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event);
    }
    public void perfilAluno (ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/PerfilAluno.fxml", "Perfil Aluno", event);
    }
    public void secaoGeral(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml", "Secao Geral", event);
    }
    public void tgGeral(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/TGAluno.fxml", "TG Aluno", event);
    }
    public void telaInicial(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/AtualizacoesAluno.fxml", "Tela Inicial", event);
    }

}
