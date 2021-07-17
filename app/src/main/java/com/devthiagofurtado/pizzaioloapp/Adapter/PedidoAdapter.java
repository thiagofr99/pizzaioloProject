package com.devthiagofurtado.pizzaioloapp.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devthiagofurtado.pizzaioloapp.Model.Carrinho;
import com.devthiagofurtado.pizzaioloapp.Model.Produtos;
import com.devthiagofurtado.pizzaioloapp.R;
import com.devthiagofurtado.pizzaioloapp.activity.PedidoActivity;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.MyViewHolder> {
    List<Carrinho> carrinho;
    DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    List<Produtos> produtosList;
    Context context;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public PedidoAdapter(List<Produtos> produtosList, List<Carrinho> carrinhoList, Context context) {
        this.produtosList = produtosList;
        this.carrinho = carrinhoList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapeter_produtos, parent, false);
        return new MyViewHolder(itemLista);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Produtos produtos = produtosList.get(position);
        final Carrinho addCarrinho = carrinho.get(position);

        //StorageReference referenceImg = storageReference.child("imagens").child(produtos.getImgProduto());


        Uri uri = Uri.parse(produtos.getImgProduto());

        Glide.with(context)
                .load(uri)
                .into(holder.imagePizza);

        //holder.imagePizza.setImageResource(R.drawable.lombinho);

        holder.textViewSabor.setText(produtos.getNomePizza());
        holder.textViewDescricao.setText(produtos.getDescricaoSabor());
        holder.textViewValorUnP.setText(produtos.getValorP().toString());
        holder.textViewValorUnM.setText(produtos.getValorM().toString());
        holder.textViewValorUnG.setText(produtos.getValorG().toString());

        holder.buttonMaisP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double qtd = Double.parseDouble(holder.textViewQtdP.getText().toString());
                Double subtotal = Double.parseDouble(holder.textViewValorUnP.getText().toString());

                qtd = qtd + 0.5;
                subtotal = subtotal * qtd;
                holder.textViewQtdP.setText(Double.toString(qtd));
                addCarrinho.setQtdP(qtd);
                holder.textViewsubTotalP.setText(decimalFormat.format(subtotal));
                addCarrinho.setSubTotalP(subtotal);

                //Log.i("Textolan", addCarrinho.getNome()+" "+addCarrinho.getQtdP()+" "+addCarrinho.getSubTotalP());

            }
        });

        holder.buttonMaisM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double qtd = Double.parseDouble(holder.textViewQtdM.getText().toString());
                Double subtotal = Double.parseDouble(holder.textViewValorUnM.getText().toString());

                qtd = qtd + 0.5;
                subtotal = subtotal * qtd;
                holder.textViewQtdM.setText(Double.toString(qtd));
                addCarrinho.setQtdM(qtd);
                holder.textViewsubTotalM.setText(decimalFormat.format(subtotal));
                addCarrinho.setSubTotalM(subtotal);

            }
        });

        holder.buttonMaisG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double qtd = Double.parseDouble(holder.textViewQtdG.getText().toString());
                Double subtotal = Double.parseDouble(holder.textViewValorUnG.getText().toString());

                qtd = qtd + 0.5;
                subtotal = subtotal * qtd;
                holder.textViewQtdG.setText(Double.toString(qtd));
                addCarrinho.setQtdG(qtd);
                holder.textViewsubTotalG.setText(decimalFormat.format(subtotal));
                addCarrinho.setSubTotalG(subtotal);
            }
        });

        holder.buttonMenosP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double qtd = Double.parseDouble(holder.textViewQtdP.getText().toString());
                Double subtotal = Double.parseDouble(holder.textViewValorUnP.getText().toString());
                if (qtd == 0) {

                } else {
                    qtd = qtd - 0.5;

                    if (qtd == 0) {
                        holder.textViewsubTotalP.setText(Double.toString(0));
                        addCarrinho.setQtdP(0.0);
                    } else {
                        subtotal = subtotal * qtd;
                        holder.textViewsubTotalP.setText(decimalFormat.format(subtotal));
                        addCarrinho.setQtdP(qtd);
                        addCarrinho.setSubTotalP(subtotal);
                    }
                }
                holder.textViewQtdP.setText(Double.toString(qtd));
            }


        });

        holder.buttonMenosM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double qtd = Double.parseDouble(holder.textViewQtdM.getText().toString());
                Double subtotal = Double.parseDouble(holder.textViewValorUnM.getText().toString());
                if (qtd == 0) {

                } else {
                    qtd = qtd - 0.5;

                    if (qtd == 0) {
                        holder.textViewsubTotalM.setText(Double.toString(0));
                        addCarrinho.setQtdM(0.0);
                    } else {
                        subtotal = subtotal * qtd;
                        holder.textViewsubTotalM.setText(decimalFormat.format(subtotal));
                        addCarrinho.setQtdM(qtd);
                        addCarrinho.setSubTotalM(subtotal);
                    }
                }
                holder.textViewQtdM.setText(Double.toString(qtd));
            }
        });

        holder.buttonMenosG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double qtd = Double.parseDouble(holder.textViewQtdG.getText().toString());
                Double subtotal = Double.parseDouble(holder.textViewValorUnG.getText().toString());
                if (qtd == 0) {

                } else {
                    qtd = qtd - 0.5;

                    if (qtd == 0) {
                        holder.textViewsubTotalG.setText(Double.toString(0));
                        addCarrinho.setQtdG(0.0);
                    } else {
                        subtotal = subtotal * qtd;
                        holder.textViewsubTotalG.setText(decimalFormat.format(subtotal));
                        addCarrinho.setQtdG(qtd);
                        addCarrinho.setSubTotalG(subtotal);
                    }
                }
                holder.textViewQtdG.setText(Double.toString(qtd));
            }
        });

    }


    @Override
    public int getItemCount() {
        return produtosList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewSabor, textViewDescricao, textViewValorUnP, textViewValorUnM, textViewValorUnG, textViewQtdP, textViewQtdM, textViewQtdG, textViewsubTotalP, textViewsubTotalM, textViewsubTotalG;
        ImageView imagePizza;
        Button buttonMaisP, buttonMaisM, buttonMaisG, buttonMenosP, buttonMenosG, buttonMenosM;


        public MyViewHolder(View itemView) {
            super(itemView);


            textViewSabor = itemView.findViewById(R.id.textNomeProduto);
            textViewDescricao = itemView.findViewById(R.id.textDescricao);
            textViewValorUnP = itemView.findViewById(R.id.valorUntP);
            textViewValorUnM = itemView.findViewById(R.id.vlrUnitM);
            textViewValorUnG = itemView.findViewById(R.id.vlrUnitG);
            imagePizza = itemView.findViewById(R.id.imageViewPizza);
            buttonMaisP = itemView.findViewById(R.id.buttonAdicionarP);
            buttonMaisM = itemView.findViewById(R.id.buttonAdicionarM);
            buttonMaisG = itemView.findViewById(R.id.buttonAdicionarG);
            buttonMenosP = itemView.findViewById(R.id.buttonRetirarP);
            buttonMenosM = itemView.findViewById(R.id.buttonRetirarM);
            buttonMenosG = itemView.findViewById(R.id.buttonRetirarG);
            textViewQtdP = itemView.findViewById(R.id.textViewQtdP);
            textViewQtdM = itemView.findViewById(R.id.textViewQtdM);
            textViewQtdG = itemView.findViewById(R.id.textViewQtdG);
            textViewsubTotalP = itemView.findViewById(R.id.subTotalP);
            textViewsubTotalM = itemView.findViewById(R.id.subTotalM);
            textViewsubTotalG = itemView.findViewById(R.id.subTotalG);


        }

    }

}


