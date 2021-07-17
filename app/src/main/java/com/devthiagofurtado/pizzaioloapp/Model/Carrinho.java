package com.devthiagofurtado.pizzaioloapp.Model;

public class Carrinho {
    private String nome;
    private Boolean tamanhoP;
    private Boolean tamanhoM;
    private Boolean tamanhoG;
    private Double qtdP;
    private Double qtdM;
    private Double qtdG;
    private Double subTotalP;
    private Double subTotalM;
    private Double subTotalG;

    public Carrinho() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getTamanhoP() {
        return tamanhoP;
    }

    public void setTamanhoP(Boolean tamanhoP) {
        this.tamanhoP = tamanhoP;
    }

    public Boolean getTamanhoM() {
        return tamanhoM;
    }

    public void setTamanhoM(Boolean tamanhoM) {
        this.tamanhoM = tamanhoM;
    }

    public Boolean getTamanhoG() {
        return tamanhoG;
    }

    public void setTamanhoG(Boolean tamanhoG) {
        this.tamanhoG = tamanhoG;
    }

    public Double getQtdP() {
        return qtdP;
    }

    public void setQtdP(Double qtdP) {
        this.qtdP = qtdP;
    }

    public Double getQtdM() {
        return qtdM;
    }

    public void setQtdM(Double qtdM) {
        this.qtdM = qtdM;
    }

    public Double getQtdG() {
        return qtdG;
    }

    public void setQtdG(Double qtdG) {
        this.qtdG = qtdG;
    }

    public Double getSubTotalP() {
        return subTotalP;
    }

    public void setSubTotalP(Double subTotalP) {
        this.subTotalP = subTotalP;
    }

    public Double getSubTotalM() {
        return subTotalM;
    }

    public void setSubTotalM(Double subTotalM) {
        this.subTotalM = subTotalM;
    }

    public Double getSubTotalG() {
        return subTotalG;
    }

    public void setSubTotalG(Double subTotalG) {
        this.subTotalG = subTotalG;
    }
}
