package org.api.trabalhodegraduacao.utils;

import org.api.trabalhodegraduacao.entities.Secao;
import org.api.trabalhodegraduacao.entities.Correcao;

public class SessaoVisualizacao {
    private static SessaoVisualizacao instance;

    private Secao secaoHistorica; // A seção antiga para visualizar
    private Correcao correcaoHistorica; // A correção daquela época

    private SessaoVisualizacao() {}

    public static SessaoVisualizacao getInstance() {
        if (instance == null) instance = new SessaoVisualizacao();
        return instance;
    }

    public void setDados(Secao secao, Correcao correcao) {
        this.secaoHistorica = secao;
        this.correcaoHistorica = correcao;
    }

    public Secao getSecaoHistorica() { return secaoHistorica; }
    public Correcao getCorrecaoHistorica() { return correcaoHistorica; }

    public void limpar() {
        this.secaoHistorica = null;
        this.correcaoHistorica = null;
    }
}