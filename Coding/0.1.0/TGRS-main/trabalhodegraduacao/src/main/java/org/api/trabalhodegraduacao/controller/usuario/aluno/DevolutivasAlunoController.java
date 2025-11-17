package org.api.trabalhodegraduacao.controller.usuario.aluno;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image; // Importe
import javafx.scene.image.ImageView; // Importe
import javafx.geometry.Rectangle2D; // Importe
import javafx.scene.shape.Circle; // Importe
import java.io.File; // Importe

import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.CorrecaoDAO;
import org.api.trabalhodegraduacao.dao.SecaoDAO;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Correcao;
import org.api.trabalhodegraduacao.entities.Secao;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;
import org.api.trabalhodegraduacao.utils.SessaoVisualizacao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DevolutivasAlunoController {

    @FXML private Button bt_Sair, bt_devolutivas_geral, bt_perfil_geral, bt_secao_geral, bt_tela_inicial, bt_tg_geral;

    @FXML private ImageView imgVwFotoPerfil; // Adicionado

    @FXML private TableView<Correcao> devolutivasTableView;
    @FXML private TableColumn<Correcao, LocalDate> colunaData;
    @FXML private TableColumn<Correcao, String> colunaTitulo;
    @FXML private TableColumn<Correcao, String> colunaDevolutivas;

    private CorrecaoDAO correcaoDAO;
    private UsuarioDAO usuarioDAO;
    private SecaoDAO secaoDAO;
    private Usuario usuarioLogado; // Para guardar o usuário carregado

    @FXML
    public void initialize() {
        this.correcaoDAO = new CorrecaoDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.secaoDAO = new SecaoDAO();

        configurarTabela();
        carregarDadosTabela();

        // --- CARREGA A FOTO DE PERFIL ---
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        if (sessao.isLogado()) {
            try {
                this.usuarioLogado = usuarioDAO.exibirPerfil(sessao.getEmail());
                if (this.usuarioLogado != null) {
                    carregarFotoPerfil();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Foto Padrão
            Image imagemPadrao = new Image(getClass().getResourceAsStream("/org/api/trabalhodegraduacao/images/imgFotoPerfil.png"));
            configurarImagemRedonda(imgVwFotoPerfil, imagemPadrao);
        }
        // --------------------------------

        devolutivasTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                abrirVersaoHistorica();
            }
        });
    }

    /**
     * Carrega a foto de perfil do usuário logado e a exibe com recorte redondo.
     */
    private void carregarFotoPerfil() {
        if (imgVwFotoPerfil == null) return;

        Image imagem = null;
        String caminhoFoto = (usuarioLogado != null) ? usuarioLogado.getFotoPerfil() : null;

        if (caminhoFoto != null && !caminhoFoto.isEmpty()) {
            try {
                if (caminhoFoto.startsWith("file:") || caminhoFoto.startsWith("http")) {
                    imagem = new Image(caminhoFoto);
                } else {
                    File arquivo = new File(caminhoFoto);
                    if (arquivo.exists()) {
                        imagem = new Image(arquivo.toURI().toString());
                    }
                }
            } catch (Exception e) {
                System.err.println("Erro ao carregar foto: " + e.getMessage());
            }
        }

        if (imagem == null || imagem.isError()) {
            imagem = new Image(getClass().getResourceAsStream("/org/api/trabalhodegraduacao/images/imgFotoPerfil.png"));
        }

        configurarImagemRedonda(imgVwFotoPerfil, imagem);
    }

    private void configurarImagemRedonda(ImageView imageView, Image imagem) {
        if (imagem == null || imageView == null) return;

        imageView.setImage(imagem);
        double w = imagem.getWidth();
        double h = imagem.getHeight();
        if (w <= 0 || h <= 0) return;

        double tamanhoQuadrado = Math.min(w, h);
        double x = (w - tamanhoQuadrado) / 2;
        double y = (h - tamanhoQuadrado) / 2;

        imageView.setViewport(new Rectangle2D(x, y, tamanhoQuadrado, tamanhoQuadrado));
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        double raio = imageView.getFitWidth() / 2;
        Circle clip = new Circle(raio, raio, raio);
        imageView.setClip(clip);
    }

    private void configurarTabela() {
        colunaData.setCellValueFactory(new PropertyValueFactory<>("dataCorrecoes"));
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("tituloSecao"));
        colunaDevolutivas.setCellValueFactory(new PropertyValueFactory<>("conteudo"));
        colunaDevolutivas.setCellFactory(tc -> new TableCell<>() {
            private final TextArea textArea = new TextArea();
            {
                textArea.setWrapText(true);
                textArea.setEditable(false);
                textArea.setStyle("-fx-control-inner-background: #50799c; -fx-text-fill: white; -fx-background-color: transparent;");
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    textArea.setText(item);
                    textArea.setPrefHeight(80);
                    setGraphic(textArea);
                }
            }
        });
    }

    private void carregarDadosTabela() {
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        if (sessao.isLogado()) {
            try {
                List<Correcao> listaCorrecoes = correcaoDAO.buscarTodasCorrecoesDoAluno(sessao.getEmail());
                ObservableList<Correcao> dados = FXCollections.observableArrayList(listaCorrecoes);
                devolutivasTableView.setItems(dados);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erro ao carregar devolutivas.");
            }
        }
    }

    private void abrirVersaoHistorica() {
        Correcao correcaoSelecionada = devolutivasTableView.getSelectionModel().getSelectedItem();
        if (correcaoSelecionada == null) return;

        try {
            Secao secaoAntiga = secaoDAO.buscarSecaoPorDataExata(
                    correcaoSelecionada.getDataSecao(),
                    correcaoSelecionada.getEmailAluno(),
                    correcaoSelecionada.getEmailOrientador()
            );

            if (secaoAntiga != null) {
                SessaoVisualizacao.getInstance().setDados(secaoAntiga, correcaoSelecionada);
                System.out.println("Abrindo histórico: " + correcaoSelecionada.getTituloSecao());

                ActionEvent eventoFalso = new ActionEvent(devolutivasTableView, null);

                Application.carregarNovaCena(
                        "/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml",
                        "Histórico: " + correcaoSelecionada.getTituloSecao(),
                        eventoFalso
                );
            } else {
                System.err.println("Erro: A versão histórica da seção não foi encontrada no banco.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- Métodos de Navegação ---
    @FXML void sair(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML void perfilAluno (ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/PerfilAluno.fxml", "Perfil Aluno", event); }
    @FXML void secaoGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml", "Seção", event); }
    @FXML void tgGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/TGAluno.fxml", "TG Aluno", event); }
    @FXML void telaInicial(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/AtualizacoesAluno.fxml", "Tela Inicial", event); }
    @FXML void devolutivasGeral(ActionEvent event) { System.out.println("Já está na tela de Devolutivas."); }
}