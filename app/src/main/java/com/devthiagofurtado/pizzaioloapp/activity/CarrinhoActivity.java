package com.devthiagofurtado.pizzaioloapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devthiagofurtado.pizzaioloapp.Adapter.CarrinhoAdapter;
import com.devthiagofurtado.pizzaioloapp.Adapter.PedidoAdapter;
import com.devthiagofurtado.pizzaioloapp.Config.ConfiguracaoFirebase;
import com.devthiagofurtado.pizzaioloapp.Model.Carrinho;
import com.devthiagofurtado.pizzaioloapp.Model.Cupom;
import com.devthiagofurtado.pizzaioloapp.Model.Pedido;
import com.devthiagofurtado.pizzaioloapp.Model.ResumoPedido;
import com.devthiagofurtado.pizzaioloapp.Model.Usuario;
import com.devthiagofurtado.pizzaioloapp.R;
import com.devthiagofurtado.pizzaioloapp.Util.NumAleatorio;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarrinhoActivity extends AppCompatActivity {
    private DatabaseReference reference = ConfiguracaoFirebase.getDatabaseReference();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference usuarioEnd;
    private DatabaseReference consultaNumPedido;
    private DatabaseReference consultaPedido;
    private DatabaseReference consultaCupom;
    private DatabaseReference finalizarPedido;
    private ValueEventListener valueEventListenerUsuario, valueEventListenerPedidos;
    private CarrinhoAdapter carrinhoAdapter;
    private RecyclerView recyclerView;
    private DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    private ArrayList<NumAleatorio> listNumAleatorio = new ArrayList<>();
    private ArrayList<Pedido> listPedido = new ArrayList<>();
    private TextView textViewNomeCarrinho, textViewTelefoneCarrinho, textViewEnderecoCarrinho, textViewBairroCarrinho, textViewComplementoCarrinho, textViewFinalQtd, textViewFinalTotal, textViewDesconto, textViewTotalFim;
    private EditText editTextDesconto;
    private Cupom cupom;
    private Usuario usuario;
    static String DATABASE_PEDIDO = "pedidos";
    static String DATABASE_NUMPEDIDO = "numpedidos";
    static String DATABASE_RESUMO = "resumo";
    static String DATABASE_DETALHADO = "detalhado";
    private ResumoPedido resumoPedido = new ResumoPedido();
    String uid = autenticacao.getCurrentUser().getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        getSupportActionBar().setTitle("Carrinho");

        textViewNomeCarrinho = findViewById(R.id.carrinhoTextViewNome);
        textViewTelefoneCarrinho = findViewById(R.id.carrinhoTextViewTelefone);
        textViewEnderecoCarrinho = findViewById(R.id.carrinhoTextViewEndereco);
        textViewBairroCarrinho = findViewById(R.id.carrinhoTextViewBairro);
        textViewComplementoCarrinho = findViewById(R.id.carrinhoTextViewComplemento);
        textViewFinalTotal = findViewById(R.id.textFinalTotal);
        textViewFinalQtd = findViewById(R.id.textFinalQtd);
        textViewDesconto = findViewById(R.id.textViewDesconto);
        editTextDesconto = findViewById(R.id.editTextDeconto);
        textViewTotalFim = findViewById(R.id.textCarrinhoTotalFim);
        recyclerView = findViewById(R.id.recyclerCarrinho);


        //Configurar pedido adapter
        carrinhoAdapter = new CarrinhoAdapter(listPedido);

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(carrinhoAdapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double qtd = extras.getDouble("qtd");
            double total = extras.getDouble("total");
            textViewFinalQtd.setText(decimalFormat.format(qtd));
            textViewFinalTotal.setText(decimalFormat.format(total));
            resumoPedido.setQtdTotalPedido(qtd);
            resumoPedido.setSubTotalPedido(total);

        }


        double total = extras.getDouble("total");

        resumoPedido.setValorDesconto(0.0);
        resumoPedido.setTotal(total);
        textViewTotalFim.setText(decimalFormat.format(total));


    }

    public void recuperarUsuario() {

        usuarioEnd = reference.child(CadastroActivity.DATABASE_USUARIO).child(uid);

        valueEventListenerUsuario = usuarioEnd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(Usuario.class);
                textViewNomeCarrinho.setText(usuario.getNome());
                textViewTelefoneCarrinho.setText(usuario.getTelefone());
                textViewEnderecoCarrinho.setText(usuario.getEndereco() + ", " + usuario.getNumero());
                textViewBairroCarrinho.setText(usuario.getBairro());
                textViewComplementoCarrinho.setText(usuario.getComplemento());


                //Log.i("TESTE", usuario.getNome());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

    }

    public void carregarPedidos() {

        consultaPedido = reference.child(PedidoActivity.DATABASE_TEMP).child(uid);
        valueEventListenerPedidos = consultaPedido.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPedido.clear();
                for (DataSnapshot dados : snapshot.getChildren()) {
                    Pedido pedidosDados = dados.getValue(Pedido.class);
                    listPedido.add(pedidosDados);

                Log.i("TEXTOLAN",dados.toString());
                }

                carrinhoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void carregarNumPedidos() {
        consultaNumPedido = reference.child(DATABASE_PEDIDO).child(uid).child(DATABASE_NUMPEDIDO);

        consultaNumPedido.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listNumAleatorio.clear();
                for (DataSnapshot dados : snapshot.getChildren()) {
                    NumAleatorio aleatorio = dados.getValue(NumAleatorio.class);
                    listNumAleatorio.add(aleatorio);
                    Log.i("TEXTOLAN",dados.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        recuperarUsuario();
        carregarPedidos();
        carregarNumPedidos();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuarioEnd.removeEventListener(valueEventListenerUsuario);
        consultaPedido.removeEventListener(valueEventListenerPedidos);
        reference.child(PedidoActivity.DATABASE_TEMP).child(uid).removeValue();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        usuarioEnd.removeEventListener(valueEventListenerUsuario);
        consultaPedido.removeEventListener(valueEventListenerPedidos);
        reference.child(PedidoActivity.DATABASE_TEMP).child(uid).removeValue();

    }

    public void verificarDesconto(final View view) {

        if (!editTextDesconto.getText().toString().isEmpty()) {
            final String cupomCodigo = editTextDesconto.getText().toString();
            consultaCupom = reference.child(CadastroActivity.DATABASE_CUPOM).child("1cupom").child(uid);
            consultaCupom.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    cupom = snapshot.getValue(Cupom.class);
                    if (cupom.getCD_CUPOM().equals(cupomCodigo)) {
                        if(cupom.getQTD_CUPOM()!=0){
                            textViewDesconto.setText(decimalFormat.format(cupom.getVALOR_DESCONTO()));
                            Bundle extras = getIntent().getExtras();

                            double total = extras.getDouble("total");
                            double desconto = cupom.getVALOR_DESCONTO();

                            resumoPedido.setValorDesconto(desconto);

                            double totalFinal = total - desconto;
                            textViewTotalFim.setText(decimalFormat.format(totalFinal));

                            resumoPedido.setTotal(totalFinal);

                            Log.i("TEXTOLAN", snapshot.getValue().toString());
                        } else {
                            Toast.makeText(CarrinhoActivity.this, "Cupom já utilizado", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(CarrinhoActivity.this, "Cupom Inválido", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        } else {

        }
    }

    public void finalizarPedido(View view) {
        finalizarPedido = reference.child(DATABASE_PEDIDO).child(uid);
        consultaCupom = reference.child(CadastroActivity.DATABASE_CUPOM).child("1cupom").child(uid);


        if (resumoPedido.getValorDesconto() != 0) {
            int qtdCupom = cupom.getQTD_CUPOM();
            int saldoCupom = qtdCupom - 1;
            cupom.setQTD_CUPOM(saldoCupom);

            consultaCupom.setValue(cupom);
        }


        Random random = new Random();

        if (usuario.getQuantidadePedido() == 0) {
            int numPedido = random.nextInt(9999);
            NumAleatorio numSorteado = new NumAleatorio();
            numSorteado.setNumSorteado(numPedido);
            resumoPedido.setNumPedido(numPedido);

            finalizarPedido.child(DATABASE_RESUMO).push().setValue(resumoPedido);
            for (int i = 0; i < listPedido.size(); i++) {
                Pedido pedido = listPedido.get(i);
                finalizarPedido.child(DATABASE_DETALHADO).child(Integer.toString(numPedido)).push().setValue(pedido);


            }

            int qtdPedidos = usuario.getQuantidadePedido();
            int saldoPedidos = qtdPedidos + 1;
            usuario.setQuantidadePedido(saldoPedidos);
            usuarioEnd.setValue(usuario);

            finalizarPedido.child(DATABASE_NUMPEDIDO).push().setValue(numSorteado);
            finish();
            startActivity(new Intent(CarrinhoActivity.this, PrincipalActivity.class));


        } else {
            for (int i = 0; i < listNumAleatorio.size(); i++) {

                NumAleatorio numeroSorteado = listNumAleatorio.get(i);
                int numPedido = random.nextInt(9999);
                if (numPedido == numeroSorteado.getNumSorteado()) {

                } else {
                    resumoPedido.setNumPedido(numPedido);
                    numeroSorteado.setNumSorteado(numPedido);
                    finalizarPedido.child(DATABASE_RESUMO).push().setValue(resumoPedido);
                    for (int x = 0; x < listPedido.size(); x++) {
                        Pedido pedido = listPedido.get(x);
                        finalizarPedido.child(DATABASE_DETALHADO).child(Integer.toString(numPedido)).push().setValue(pedido);

                    }


                    int qtdPedidos = usuario.getQuantidadePedido();
                    int saldoPedidos = qtdPedidos + 1;
                    usuario.setQuantidadePedido(saldoPedidos);
                    usuarioEnd.setValue(usuario);
                    finalizarPedido.child(DATABASE_NUMPEDIDO).push().setValue(numeroSorteado);

                    finish();
                    startActivity(new Intent(CarrinhoActivity.this, PrincipalActivity.class));
                    break;
                }
            }
        }


    }

}