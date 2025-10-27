package org.api.trabalhodegraduacao.dao; // Escolha o pacote apropriado

import org.api.trabalhodegraduacao.entities.Usuario;

public class Sessao {


    private static Usuario usuarioLogado;


    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }


    public static void setUsuarioLogado(Usuario usuario) {
        Sessao.usuarioLogado = usuario;
    }


    public static void limparSessao() {
        Sessao.usuarioLogado = null;
    }
}