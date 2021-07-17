package com.devthiagofurtado.pizzaioloapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.print.PrinterId;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.devthiagofurtado.pizzaioloapp.Adapter.PedidoAdapter;
import com.devthiagofurtado.pizzaioloapp.Config.ConfiguracaoFirebase;
import com.devthiagofurtado.pizzaioloapp.Model.Carrinho;
import com.devthiagofurtado.pizzaioloapp.Model.Pedido;
import com.devthiagofurtado.pizzaioloapp.Model.Produtos;
import com.devthiagofurtado.pizzaioloapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PedidoActivity extends AppCompatActivity {
    private DatabaseReference reference = ConfiguracaoFirebase.getDatabaseReference();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference consultaProduto;
    private List<Produtos> produtosList = new ArrayList<>();
    private TextView textQuantidade, textTotal;
    private List<Carrinho> carrinho = new ArrayList<>();
    private PedidoAdapter pedidoAdapter;
    private RecyclerView recyclerView;
    private Pedido pedido;
    private DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    static String DATABASE_TEMP = "TEMP_CARRINHO";
    String uid = autenticacao.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        textTotal = findViewById(R.id.textViewTotalPedido);
        textQuantidade = findViewById(R.id.textViewQuantidade);
        recyclerView = findViewById(R.id.recycleProdutos);


        //Configurar pedido adapter
        pedidoAdapter = new PedidoAdapter(produtosList, carrinho, this);

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(pedidoAdapter);


    }

    public void carregarLista() {
        consultaProduto = reference.child("produto");

        consultaProduto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                produtosList.clear();
                carrinho.clear();
                for (DataSnapshot dados : snapshot.getChildren()) {
                    Produtos produtos = dados.getValue(Produtos.class);
                    produtosList.add(produtos);
                    Carrinho addCarrinho = new Carrinho();
                    addCarrinho.setNome(produtos.getNomePizza());
                    addCarrinho.setQtdP(0.0);
                    addCarrinho.setQtdM(0.0);
                    addCarrinho.setQtdG(0.0);
                    addCarrinho.setSubTotalP(0.0);
                    addCarrinho.setSubTotalM(0.0);
                    addCarrinho.setSubTotalG(0.0);
                    carrinho.add(addCarrinho);

                    Log.i("TEXTOLAN", dados.getValue().toString());
                    Log.i("TEXTOLAN", produtosList.get(0).getNomePizza());
                    Log.i("TEXTOLAN", carrinho.get(0).getNome());
                }

                pedidoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void fabButton(View view) {

        Double qtdx = 0.0, qtdy = 0.0, qtdz = 0.0, totalQtd;
        Double subtotalx = 0.0, subtotaly = 0.0, subtotalz = 0.0, subtotalfim;


        for (int i = 0; i < carrinho.size(); i++) {
            qtdx += carrinho.get(i).getQtdP();
            qtdy += carrinho.get(i).getQtdM();
            qtdz += carrinho.get(i).getQtdG();

            subtotalx += carrinho.get(i).getSubTotalP();
            subtotaly += carrinho.get(i).getSubTotalM();
            subtotalz += carrinho.get(i).getSubTotalG();

            Log.i("TextoLan", carrinho.get(i).getNome() + " " + carrinho.get(i).getQtdP() + " " + carrinho.get(i).getQtdM() + " " + carrinho.get(i).getQtdG());
        }

        totalQtd = qtdx + qtdy + qtdz;
        subtotalfim = subtotalx + subtotaly + subtotalz;

        if (verificarQtdP(qtdx, qtdy, qtdz)) {

            for (int i = 0; i < carrinho.size(); i++) {
                if (carrinho.get(i).getQtdP() != 0.0) {
                    Pedido pedidoAdd = new Pedido();
                    pedidoAdd.setNome(produtosList.get(i).getNomePizza());
                    pedidoAdd.setQtd(carrinho.get(i).getQtdP());
                    pedidoAdd.setValorUnitario(produtosList.get(i).getValorP());
                    pedidoAdd.setSubTotal(carrinho.get(i).getSubTotalP());
                    pedidoAdd.setTamanho("P");

                    reference.child(DATABASE_TEMP).child(uid).push().setValue(pedidoAdd);

                    Log.i("textolan", carrinho.get(i).getQtdP().toString());
                    Log.i("textolan", produtosList.get(i).getNomePizza());
                }
                if (carrinho.get(i).getQtdM() != 0.0) {
                    Pedido pedidoAdd = new Pedido();
                    pedidoAdd.setNome(produtosList.get(i).getNomePizza());
                    pedidoAdd.setQtd(carrinho.get(i).getQtdM());
                    pedidoAdd.setValorUnitario(produtosList.get(i).getValorM());
                    pedidoAdd.setSubTotal(carrinho.get(i).getSubTotalM());
                    pedidoAdd.setTamanho("M");

                    reference.child(DATABASE_TEMP).child(uid).push().setValue(pedidoAdd);

                    Log.i("textolan", carrinho.get(i).getQtdM().toString());
                    Log.i("textolan", produtosList.get(i).getNomePizza());
                }
                if (carrinho.get(i).getQtdG() != 0.0) {
                    Pedido pedidoAdd = new Pedido();
                    pedidoAdd.setNome(produtosList.get(i).getNomePizza());
                    pedidoAdd.setQtd(carrinho.get(i).getQtdG());
                    pedidoAdd.setValorUnitario(produtosList.get(i).getValorG());
                    pedidoAdd.setSubTotal(carrinho.get(i).getSubTotalG());
                    pedidoAdd.setTamanho("G");

                    reference.child(DATABASE_TEMP).child(uid).push().setValue(pedidoAdd);

                    Log.i("textolan", carrinho.get(i).getQtdG().toString());
                    Log.i("textolan", produtosList.get(i).getNomePizza());
                }
            }
            textQuantidade.setText("Pedido quantidade: " + totalQtd.toString());
            textTotal.setText("Pedido quantidade: " + decimalFormat.format(subtotalfim));

            Intent i = new Intent(PedidoActivity.this, CarrinhoActivity.class);
            i.putExtra("qtd",totalQtd);
            i.putExtra("total",subtotalfim);
            startActivity(i);
            finish();
        }

        textQuantidade.setText("Pedido quantidade: " + totalQtd.toString());
        textTotal.setText("Pedido quantidade: " + decimalFormat.format(subtotalfim));

    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarLista();
        reference.child(PedidoActivity.DATABASE_TEMP).child(uid).removeValue();


    }

    public boolean verificarQtdP(Double qtd1, Double qtd2, Double qtd3) {
        if (qtd1.intValue() == qtd1 && qtd2.intValue() == qtd2 && qtd3.intValue() == qtd3) {
            if (qtd1.intValue() == 0 && qtd2.intValue() == 0 && qtd3.intValue() == 0) {
                Toast.makeText(PedidoActivity.this, "Pedido zerado selecione um produto.", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }

        } else {
            Toast.makeText(PedidoActivity.this, "Quantidade fracionada, selecione a outra metade.", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}
