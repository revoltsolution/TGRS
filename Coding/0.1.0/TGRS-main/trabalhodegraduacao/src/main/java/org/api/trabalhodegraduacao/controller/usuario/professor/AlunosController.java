package org.api.trabalhodegraduacao.controller.usuario.professor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.api.trabalhodegraduacao.Application;

public class AlunosController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bt_Sair;

    @FXML
    private Button bt_alunos_geral;

    @FXML
    private Button bt_devolutivas_geral;

    @FXML
    private Button bt_perfil_geral;

    @FXML
    private Button bt_tela_inicial;

    @FXML
    void initialize() {
        assert bt_Sair != null : "fx:id=\"bt_Sair\" was not injected: check your FXML file 'Alunos.fxml'.";
        assert bt_alunos_geral != null : "fx:id=\"bt_alunos_geral\" was not injected: check your FXML file 'Alunos.fxml'.";
        assert bt_devolutivas_geral != null : "fx:id=\"bt_devolutivas_geral\" was not injected: check your FXML file 'Alunos.fxml'.";
        assert bt_perfil_geral != null : "fx:id=\"bt_perfil_geral\" was not injected: check your FXML file 'Alunos.fxml'.";
        assert bt_tela_inicial != null : "fx:id=\"bt_tela_inical\" was not injected: check your FXML file 'Alunos.fxml'.";

    }
    public void sair(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event);
    }
    public void perfilProfessor (ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/PerfilProfessor.fxml", "Perfil Professor", event);
    }
    public void devolutivas(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Historico.fxml", "Historico de Devolutivas", event);
    }
    public void alunos(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Alunos.fxml", "TG Alunos", event);
    }
    public void telaInicial(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Tela Inicial", event);
    }

}
