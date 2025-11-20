package org.api.trabalhodegraduacao.utils;

import org.api.trabalhodegraduacao.entities.Usuario;

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