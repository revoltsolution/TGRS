package org.api.trabalhodegraduacao.controller.usuario.professor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.AlunoSelecionado;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;

import java.sql.SQLException;
import java.util.List;

public class AlunosController {

    @FXML private Button bt_Sair;
    @FXML private Button bt_alunos_geral;
    @FXML private Button bt_perfil_geral;
    @FXML private Button bt_tela_inicial;

    @FXML private TableView<Usuario> tabelaAlunos;
    @FXML private TableColumn<Usuario, String> colNome;
    @FXML private TableColumn<Usuario, Double> colProgresso;

    @FXML private TableColumn<Usuario, String> colTG;
    @FXML private TableColumn<Usuario, String> colSecao;

    private UsuarioDAO usuarioDAO;

    @FXML
    void initialize() {
        this.usuarioDAO = new UsuarioDAO();

        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));

        colTG.setCellValueFactory(new PropertyValueFactory<>("displayTG"));

        colSecao.setCellValueFactory(new PropertyValueFactory<>("displaySecao"));

        colProgresso.setCellValueFactory(new PropertyValueFactory<>("progresso"));
        colProgresso.setCellFactory(column -> {
            return new TableCell<Usuario, Double>() {
                private final ProgressBar pb = new ProgressBar();
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        pb.setProgress(item);
                        pb.getStyleClass().add("progress-bar-custom");
                        pb.setMaxWidth(Double.MAX_VALUE);
                        setGraphic(pb);
                    }
                }
            };
        });

        carregarAlunosDaTabela();

        tabelaAlunos.setOnMouseClicked(this::handleRowClick);
    }

    private void handleRowClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Usuario alunoSelecionado = tabelaAlunos.getSelectionModel().getSelectedItem();

            if (alunoSelecionado != null) {
                AlunoSelecionado.getInstance().setAluno(alunoSelecionado);

                System.out.println("Abrindo perfil do aluno: " + alunoSelecionado.getNomeCompleto());
                Application.carregarNovaCena(
                        "/org/api/trabalhodegraduacao/view/usuario/professor/InteracaoProfessorAluno.fxml",
                        "Perfil de " + alunoSelecionado.getNomeCompleto(),
                        new ActionEvent(event.getSource(), event.getTarget())
                );
            }
        }
    }

    private void carregarAlunosDaTabela() {
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        if (!sessao.isLogado()) {
            System.err.println("Professor não está logado!");
            return;
        }

        try {
            String emailProfessor = sessao.getEmail();
            List<Usuario> alunos = usuarioDAO.buscarAlunosPorOrientador(emailProfessor);
            ObservableList<Usuario> listaObservavel = FXCollections.observableArrayList(alunos);
            tabelaAlunos.setItems(listaObservavel);

        } catch (SQLException e) {
            System.err.println("Erro de SQL ao buscar alunos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML void alunos(ActionEvent event) { System.out.println("Já está na tela de Alunos."); }
    @FXML void perfilProfessor(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/PerfilProfessor.fxml", "Perfil", event); }
    @FXML void sair(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML void telaInicial(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Tela Inicial", event); }
}