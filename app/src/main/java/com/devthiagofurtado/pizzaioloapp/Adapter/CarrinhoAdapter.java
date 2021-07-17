package com.devthiagofurtado.pizzaioloapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devthiagofurtado.pizzaioloapp.Model.Carrinho;
import com.devthiagofurtado.pizzaioloapp.Model.Pedido;
import com.devthiagofurtado.pizzaioloapp.R;

import java.text.DecimalFormat;
import java.util.List;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.MyViewHolder> {
    List<Pedido> pedidoLista;
    DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");


    public CarrinhoAdapter(List<Pedido> pedidoList) {
        this.pedidoLista = pedidoList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_carrinho, parent, false);
        return new CarrinhoAdapter.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pedido pedido = pedidoLista.get(position);

        holder.textViewSabor.setText(pedido.getNome());
        holder.textViewQtdPizza.setText(pedido.getQtd().toString());
        holder.textViewValorUnit.setText(decimalFormat.format(pedido.getValorUnitario()));
        holder.textViewSubTotal.setText(decimalFormat.format(pedido.getSubTotal()));
        holder.textViewTamanho.setText(pedido.getTamanho());



        /*Double total = pedido.getQtd()*pedido.getValorUnitario();
        pedido.setSubTotal(total);
        holder.textViewSubTotal.setText(decimalFormat.format(total));*/

    }

    @Override
    public int getItemCount() {
        return pedidoLista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSabor, textViewQtdPizza, textViewValorUnit, textViewSubTotal, textViewTamanho;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewSabor = itemView.findViewById(R.id.textAdapterNome);
            textViewQtdPizza = itemView.findViewById(R.id.textAdapterQtd);
            textViewValorUnit = itemView.findViewById(R.id.textAdapterVlrUnit);
            textViewSubTotal = itemView.findViewById(R.id.textAdapterSubTotal);
            textViewTamanho = itemView.findViewById(R.id.textAdapterTamanho);



        }
    }
}
