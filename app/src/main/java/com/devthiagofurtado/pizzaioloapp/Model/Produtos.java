package com.devthiagofurtado.pizzaioloapp.Model;

import com.google.firebase.database.Exclude;

public class Produtos {

    private String uidProduto;
    private String nomePizza;
    private String descricaoSabor;
    private String imgProduto;
    private Double valorP, valorM, valorG;
    private Boolean statusProduto;

    public Produtos() {
    }

    @Exclude
    public String getUidProduto() {
        return uidProduto;
    }

    @Exclude
    public void setUidProduto(String uidProduto) {
        this.uidProduto = uidProduto;
    }

    public String getNomePizza() {
        return nomePizza;
    }

    public void setNomePizza(String nomePizza) {
        this.nomePizza = nomePizza;
    }

    public String getDescricaoSabor() {
        return descricaoSabor;
    }

    public void setDescricaoSabor(String descricaoSabor) {
        this.descricaoSabor = descricaoSabor;
    }

    public String getImgProduto() {
        return imgProduto;
    }

    public void setImgProduto(String imgProduto) {
        this.imgProduto = imgProduto;
    }

    public Double getValorP() {
        return valorP;
    }

    public void setValorP(Double valorP) {
        this.valorP = valorP;
    }

    public Double getValorM() {
        return valorM;
    }

    public void setValorM(Double valorM) {
        this.valorM = valorM;
    }

    public Double getValorG() {
        return valorG;
    }

    public void setValorG(Double valorG) {
        this.valorG = valorG;
    }

    public Boolean getStatusProduto() {
        return statusProduto;
    }

    public void setStatusProduto(Boolean statusProduto) {
        this.statusProduto = statusProduto;
    }
}
