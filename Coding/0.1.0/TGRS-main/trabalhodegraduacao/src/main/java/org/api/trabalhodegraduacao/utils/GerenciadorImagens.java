package org.api.trabalhodegraduacao.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class GerenciadorImagens {

    private static final String PASTA_IMAGENS = "imagens_perfil";
    private static final String FOTO_PADRAO_URL = "/org/api/trabalhodegraduacao/images/imgFotoPerfil.png";

    public static void configurarImagemPerfil(ImageView imageView, String caminhoFotoSalva) {
        try {
            Image imagem;
            if (caminhoFotoSalva != null && !caminhoFotoSalva.isEmpty()) {
                File file = new File(caminhoFotoSalva);
                if (file.exists()) {
                    imagem = new Image(file.toURI().toString());
                } else {
                    imagem = new Image(GerenciadorImagens.class.getResource(FOTO_PADRAO_URL).toExternalForm());
                }
            } else {
                imagem = new Image(GerenciadorImagens.class.getResource(FOTO_PADRAO_URL).toExternalForm());
            }

            imageView.setImage(imagem);

        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + e.getMessage());
        }
    }

    public static String selecionarESalvarNovaFoto(Stage stage, String nomeArquivoDestino) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione sua Foto de Perfil");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );

        File arquivoSelecionado = fileChooser.showOpenDialog(stage);

        if (arquivoSelecionado != null) {
            try {
                File pastaDestino = new File(PASTA_IMAGENS);
                if (!pastaDestino.exists()) {
                    pastaDestino.mkdirs();
                }

                String extensao = "";
                int i = arquivoSelecionado.getName().lastIndexOf('.');
                if (i > 0) {
                    extensao = arquivoSelecionado.getName().substring(i);
                }

                File arquivoDestino = new File(pastaDestino, nomeArquivoDestino + extensao);

                Files.copy(arquivoSelecionado.toPath(), arquivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                return arquivoDestino.getAbsolutePath();

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}