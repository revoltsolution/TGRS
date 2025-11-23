package org.api.trabalhodegraduacao.controller.usuario.professor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.bancoDeDados.GerenciadorDB;
import org.api.trabalhodegraduacao.dao.CursoDAO;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Curso;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.api.trabalhodegraduacao.utils.AlunoSelecionado;
import javafx.collections.transformation.FilteredList;

public class GestaoCursosController {

    private CursoDAO cursoDAO = new CursoDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private GerenciadorDB gerenciadorDB = new GerenciadorDB();
    private ObservableList<Usuario> listaMestreAlunos;

    @FXML private ComboBox<String> cbFiltroOrientador;

    @FXML private ImageView imgVwFotoPerfil;

    @FXML private ListView<Curso> listaCursos;
    @FXML private TextField txtNovoCursoCod, txtNovoCursoNome;

    @FXML private ComboBox<Usuario> cbProfessores;
    @FXML private ComboBox<Curso> cbCursosGestao;
    @FXML private CheckBox chkIsOrientador, chkIsGerente;
    @FXML private TableView<Usuario> tabelaProfessores;
    @FXML private TableColumn<Usuario, String> colProfNome, colProfFuncao, colProfCurso;

    @FXML private TableView<Usuario> tabelaMonitoramento;
    @FXML private TableColumn<Usuario, String> colAlunoNome, colAlunoOrientador;
    @FXML private TableColumn<Usuario, Double> colAlunoProgresso;

    @FXML private TextArea txtLogImportacao;

    @FXML
    public void initialize() {
        carregarFoto();
        carregarCursos();
        carregarProfessores();
        carregarMonitoramento();
        configurarSelecaoCurso();
    }

    private void carregarCursos() {
        List<Curso> cursos = cursoDAO.listarTodos();
        ObservableList<Curso> obsCursos = FXCollections.observableArrayList(cursos);
        listaCursos.setItems(obsCursos);
        cbCursosGestao.setItems(obsCursos);
    }

    @FXML
    void adicionarCurso(ActionEvent event) {
        try {
            String cod = txtNovoCursoCod.getText();
            String nome = txtNovoCursoNome.getText();
            if(!cod.isEmpty() && !nome.isEmpty()){
                cursoDAO.cadastrar(new Curso(cod, nome));
                carregarCursos();
                txtNovoCursoCod.clear();
                txtNovoCursoNome.clear();
                exibirAlerta("Sucesso", "Curso adicionado!");
            }
        } catch (SQLException e) {
            exibirAlerta("Erro", "Falha ao criar curso: " + e.getMessage());
        }
    }

    @FXML
    void removerCurso(ActionEvent event) {
        Curso selecionado = listaCursos.getSelectionModel().getSelectedItem();

        if (selecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Exclusão");
            alert.setHeaderText("Você está prestes a remover: " + selecionado.getNome());
            alert.setContentText("Tem certeza? Isso pode afetar professores e alunos vinculados a este curso.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    cursoDAO.excluir(selecionado.getCodigo());
                    carregarCursos();
                    txtNovoCursoCod.clear();
                    txtNovoCursoNome.clear();
                    exibirAlerta("Sucesso", "Curso removido com sucesso.");
                } catch (SQLException e) {
                    exibirAlerta("Erro Crítico", "Não foi possível remover este curso.\nMotivo: Existem alunos ou professores vinculados a ele no banco de dados.");
                }
            }
        } else {
            exibirAlerta("Atenção", "Selecione um curso na lista para remover.");
        }
    }

    private void carregarProfessores() {
        try {
            List<Usuario> profs = usuarioDAO.listarTodosProfessores();
            ObservableList<Usuario> obsProfs = FXCollections.observableArrayList(profs);

            tabelaProfessores.setItems(obsProfs);
            colProfNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
            colProfFuncao.setCellValueFactory(new PropertyValueFactory<>("funcao"));
            colProfCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));

            cbProfessores.setItems(obsProfs);

            Callback<ListView<Usuario>, ListCell<Usuario>> cellFactory = param -> new ListCell<>() {
                @Override protected void updateItem(Usuario item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null) setText(null); else setText(item.getNomeCompleto());
                }
            };
            cbProfessores.setCellFactory(cellFactory);
            cbProfessores.setButtonCell(cellFactory.call(null));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void salvarPermissao(ActionEvent event) {
        Usuario prof = cbProfessores.getValue();
        Curso curso = cbCursosGestao.getValue();
        boolean novoStatusGerente = chkIsGerente.isSelected();
        boolean novoStatusOrientador = chkIsOrientador.isSelected();

        if (prof == null) {
            exibirAlerta("Atenção", "Selecione um professor.");
            return;
        }
        if (novoStatusGerente && curso == null) {
            exibirAlerta("Atenção", "Para promover a Gerente, selecione o Curso que ele coordena.");
            return;
        }

        try {
            if (!novoStatusGerente) {
                UsuarioDAO.FuncaoProfessor papeisAtuais = usuarioDAO.buscarFuncaoProfessor(prof.getEmailCadastrado());

                if (papeisAtuais.gerenciador) {
                    int totalGerentes = usuarioDAO.contarTotalGerenciadores();
                    if (totalGerentes <= 1) {
                        exibirAlerta("Ação Bloqueada",
                                "Você não pode remover o status de Gerenciador deste professor.\n" +
                                        "Motivo: Ele é o ÚNICO gerenciador do sistema.\n" +
                                        "Cadastre outro gerenciador antes de remover este."
                        );
                        return;
                    }
                }
            }

            if (novoStatusGerente) {
                String emailDonoAtual = usuarioDAO.buscarEmailGerenteDoCurso(curso.getNome());

                if (emailDonoAtual != null && !emailDonoAtual.equals(prof.getEmailCadastrado())) {
                    Usuario dono = usuarioDAO.exibirPerfil(emailDonoAtual);
                    String nomeDono = (dono != null) ? dono.getNomeCompleto() : emailDonoAtual;

                    exibirAlerta("Conflito de Gestão",
                            "O curso '" + curso.getNome() + "' já é gerenciado por: " + nomeDono + ".\n\n" +
                                    "Regra: Apenas um professor pode gerenciar um curso por vez.\n" +
                                    "Remova a permissão do anterior antes de atribuir a este."
                    );
                    return;
                }
            }

            usuarioDAO.atualizarFuncaoProfessor(
                    prof.getEmailCadastrado(),
                    novoStatusOrientador,
                    novoStatusGerente,
                    novoStatusGerente ? curso.getNome() : null
            );

            carregarProfessores();
            exibirAlerta("Sucesso", "Permissões atualizadas com segurança.");

        } catch (SQLException e) {
            exibirAlerta("Erro", "Falha ao atualizar permissões: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void carregarMonitoramento() {
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        try {
            List<Usuario> alunos = usuarioDAO.buscarAlunosParaDashboard(sessao.getEmail());

            listaMestreAlunos = FXCollections.observableArrayList(alunos);

            tabelaMonitoramento.setItems(listaMestreAlunos);

            colAlunoNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
            colAlunoOrientador.setCellValueFactory(new PropertyValueFactory<>("nomeOrientador"));
            colAlunoProgresso.setCellValueFactory(new PropertyValueFactory<>("progresso"));

            colAlunoProgresso.setCellFactory(tc -> new TableCell<>() {
                private final ProgressBar pb = new ProgressBar();
                @Override protected void updateItem(Double progresso, boolean empty) {
                    super.updateItem(progresso, empty);
                    if (empty || progresso == null) { setGraphic(null); }
                    else { pb.setProgress(progresso); setGraphic(pb); }
                }
            });

            ObservableList<String> orientadores = FXCollections.observableArrayList();
            orientadores.add("Todos");

            for (Usuario u : alunos) {
                String nomeO = u.getNomeOrientador();
                if (nomeO != null && !orientadores.contains(nomeO)) {
                    orientadores.add(nomeO);
                }
            }
            cbFiltroOrientador.setItems(orientadores);

            cbFiltroOrientador.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal == null || newVal.equals("Todos")) {
                    tabelaMonitoramento.setItems(listaMestreAlunos);
                } else {
                    FilteredList<Usuario> filtrados = new FilteredList<>(listaMestreAlunos, p ->
                            p.getNomeOrientador() != null && p.getNomeOrientador().equals(newVal)
                    );
                    tabelaMonitoramento.setItems(filtrados);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void visualizarAluno(ActionEvent event) {
        Usuario alunoSelecionado = tabelaMonitoramento.getSelectionModel().getSelectedItem();

        if (alunoSelecionado == null) {
            exibirAlerta("Atenção", "Selecione um aluno na tabela primeiro.");
            return;
        }

        AlunoSelecionado.getInstance().setAluno(alunoSelecionado);
        org.api.trabalhodegraduacao.utils.SessaoTG.getInstance().setModoApenasLeitura(true);

        Application.carregarNovaCena(
                "/org/api/trabalhodegraduacao/view/usuario/professor/ListaTgsAluno.fxml",
                "Monitoramento: " + alunoSelecionado.getNomeCompleto(),
                event
        );
    }

    @FXML
    void importarCSV(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione o arquivo usuarios.csv");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        Stage stage = (Stage) txtLogImportacao.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            txtLogImportacao.setText("Lendo arquivo: " + file.getAbsolutePath() + "...\n");
            gerenciadorDB.popularUsuariosDeArquivoExterno(file);
            txtLogImportacao.appendText("Processamento finalizado. Verifique o console para erros.");
            carregarProfessores();
        }
    }

    private void carregarFoto() {
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        try {
            Usuario u = usuarioDAO.exibirPerfil(sessao.getEmail());
            if(u != null && imgVwFotoPerfil != null && u.getFotoPerfil() != null) {
                try {
                    imgVwFotoPerfil.setImage(new Image(u.getFotoPerfil()));
                } catch(Exception e){}
            }
        } catch (Exception e) {}
    }

    @FXML
    void voltarDashboard(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Dashboard", event);
    }

    private void exibirAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    private void configurarSelecaoCurso() {
        listaCursos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtNovoCursoCod.setText(newVal.getCodigo());
                txtNovoCursoNome.setText(newVal.getNome());

                System.out.println("Curso selecionado: " + newVal.getNome());
            }
        });
    }
}