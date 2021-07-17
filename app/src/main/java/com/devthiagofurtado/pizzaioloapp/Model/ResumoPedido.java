package com.devthiagofurtado.pizzaioloapp.Model;

public class ResumoPedido {
    private double qtdTotalPedido;
    private double subTotalPedido;
    private double valorDesconto;
    private double total;
    private int numPedido;

    public ResumoPedido() {
    }

    public double getQtdTotalPedido() {
        return qtdTotalPedido;
    }

    public void setQtdTotalPedido(double qtdTotalPedido) {
        this.qtdTotalPedido = qtdTotalPedido;
    }

    public double getSubTotalPedido() {
        return subTotalPedido;
    }

    public void setSubTotalPedido(double subTotalPedido) {
        this.subTotalPedido = subTotalPedido;
    }

    public double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }
}
