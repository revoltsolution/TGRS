package org.api.trabalhodegraduacao.utils;

import org.api.trabalhodegraduacao.entities.Usuario;

/**
 * Singleton para "passar" o aluno selecionado pelo professor
 * da tela de Alunos para a tela de Interacao.
 */
public class AlunoSelecionado {

    private static AlunoSelecionado instance;
    private Usuario aluno;

    private AlunoSelecionado() {}

    public static AlunoSelecionado getInstance() {
        if (instance == null) {
            instance = new AlunoSelecionado();
        }
        return instance;
    }

    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
    }

    public Usuario getAluno() {
        return aluno;
    }

    public void limparSelecao() {
        this.aluno = null;
    }
}