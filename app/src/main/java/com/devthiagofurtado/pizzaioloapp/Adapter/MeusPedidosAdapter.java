package com.devthiagofurtado.pizzaioloapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devthiagofurtado.pizzaioloapp.Model.ResumoPedido;
import com.devthiagofurtado.pizzaioloapp.R;

import java.text.DecimalFormat;
import java.util.List;

public class MeusPedidosAdapter extends RecyclerView.Adapter<MeusPedidosAdapter.MyViewHolder> {

    List<ResumoPedido> resumoPedidoList;
    private DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

    public MeusPedidosAdapter(List<ResumoPedido> resumoPedidoList) {
        this.resumoPedidoList = resumoPedidoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.meuspedidos_adapter, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ResumoPedido resumoPedido = resumoPedidoList.get(position);

        holder.textMeusPedidosNumPedido.setText(Integer.toString(resumoPedido.getNumPedido()));
        holder.textMeusPedidosQtdProduto.setText(decimalFormat.format(resumoPedido.getQtdTotalPedido()));
        holder.textMeusPedidosSubTotal.setText(decimalFormat.format(resumoPedido.getSubTotalPedido()));
        holder.textMeusPedidosDesconto.setText(decimalFormat.format(resumoPedido.getValorDesconto()));
        holder.textMeusPedidosTotal.setText(decimalFormat.format(resumoPedido.getTotal()));


    }

    @Override
    public int getItemCount() {
        return resumoPedidoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textMeusPedidosNumPedido, textMeusPedidosQtdProduto, textMeusPedidosSubTotal, textMeusPedidosDesconto, textMeusPedidosTotal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textMeusPedidosNumPedido = itemView.findViewById(R.id.textMeusPedidosNumero);
            textMeusPedidosQtdProduto = itemView.findViewById(R.id.textMeusPedidosQuantidade);
            textMeusPedidosSubTotal = itemView.findViewById(R.id.textMeusPedidosSubtotal);
            textMeusPedidosDesconto = itemView.findViewById(R.id.textMeusPedidosDesconto);
            textMeusPedidosTotal = itemView.findViewById(R.id.textMeusPedidosTotal);

        }

    }
}
