package org.api.trabalhodegraduacao.tg;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class TGRegistro {


    private final StringProperty dataHora;
    private final StringProperty valorTG;

    public TGRegistro(String dataHora, String valorTG) {
        this.dataHora = new SimpleStringProperty(dataHora);
        this.valorTG = new SimpleStringProperty(valorTG);
    }

    
    public StringProperty dataHoraProperty() {
        return dataHora;
    }

    public StringProperty valorTGProperty() {
        return valorTG;
    }


    public String getDataHora() {
        return dataHora.get();
    }

    public String getValorTG() {
        return valorTG.get();
    }
}