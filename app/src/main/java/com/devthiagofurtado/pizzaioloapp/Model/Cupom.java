package com.devthiagofurtado.pizzaioloapp.Model;

public class Cupom {
    String CD_CUPOM;
    int QTD_CUPOM;
    double VALOR_DESCONTO;

    public Cupom() {
    }

    public String getCD_CUPOM() {
        return CD_CUPOM;
    }

    public void setCD_CUPOM(String CD_CUPOM) {
        this.CD_CUPOM = CD_CUPOM;
    }

    public int getQTD_CUPOM() {
        return QTD_CUPOM;
    }

    public void setQTD_CUPOM(int QTD_CUPOM) {
        this.QTD_CUPOM = QTD_CUPOM;
    }

    public double getVALOR_DESCONTO() {
        return VALOR_DESCONTO;
    }

    public void setVALOR_CUPOM(double VALOR_DESCONTO) {
        this.VALOR_DESCONTO = VALOR_DESCONTO;
    }
}
