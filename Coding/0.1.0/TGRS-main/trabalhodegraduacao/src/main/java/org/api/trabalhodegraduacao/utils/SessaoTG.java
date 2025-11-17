package org.api.trabalhodegraduacao.utils;

public class SessaoTG {
    private static SessaoTG instance;
    private int idTgAtual; // Armazena qual TG (1 a 6) o aluno quer acessar

    private SessaoTG() {}

    public static SessaoTG getInstance() {
        if (instance == null) {
            instance = new SessaoTG();
        }
        return instance;
    }

    public void setIdTgAtual(int id) {
        this.idTgAtual = id;
    }

    public int getIdTgAtual() {
        return idTgAtual;
    }
}