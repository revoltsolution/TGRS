package org.api.trabalhodegraduacao.utils;

public class SessaoTG {
    private static SessaoTG instance;
    private int idTgAtual;
    private boolean modoApenasLeitura = false;

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

    public boolean isModoApenasLeitura() {
        return modoApenasLeitura;
    }

    public void setModoApenasLeitura(boolean modoApenasLeitura) {
        this.modoApenasLeitura = modoApenasLeitura;
    }
}