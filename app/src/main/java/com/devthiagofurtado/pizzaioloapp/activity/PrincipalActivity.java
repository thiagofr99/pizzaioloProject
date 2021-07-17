package com.devthiagofurtado.pizzaioloapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.devthiagofurtado.pizzaioloapp.Adapter.MeusPedidosAdapter;
import com.devthiagofurtado.pizzaioloapp.Config.ConfiguracaoFirebase;
import com.devthiagofurtado.pizzaioloapp.Model.Produtos;
import com.devthiagofurtado.pizzaioloapp.Model.ResumoPedido;
import com.devthiagofurtado.pizzaioloapp.Model.Usuario;
import com.devthiagofurtado.pizzaioloapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {
    private ValueEventListener valueEventListenerUsuario;
    private DatabaseReference usuarioEnd;
    private DatabaseReference pedidosReference;
    private List<ResumoPedido> listResumo = new ArrayList<>();
    private MeusPedidosAdapter meusPedidosAdapter;
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfiguracaoFirebase.getDatabaseReference();
    private TextView textViewNome, textViewTelefone, textViewEndereco, textViewBairro, textViewComplemento, textViewPedidos;
    private RecyclerView recyclerViewMeusPedidos;
    String uid = autenticacao.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pizzaiolo");

        setSupportActionBar(toolbar);

        textViewNome = findViewById(R.id.textViewNome);
        textViewTelefone = findViewById(R.id.textViewTelefone);
        textViewEndereco = findViewById(R.id.textViewEnderecoNum);
        textViewBairro = findViewById(R.id.textViewBairro);
        textViewComplemento = findViewById(R.id.textViewComplemento);
        textViewPedidos = findViewById(R.id.textViewMeusPedidos);
        recyclerViewMeusPedidos = findViewById(R.id.recyclerMeusPedido);


        //Configurar meusPedidosAdapter
        meusPedidosAdapter = new MeusPedidosAdapter(listResumo);

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewMeusPedidos.setLayoutManager(layoutManager);
        recyclerViewMeusPedidos.setHasFixedSize(true);
        recyclerViewMeusPedidos.setAdapter(meusPedidosAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSair:
                autenticacao.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void recuperarDadosUsuario() {
        usuarioEnd = reference.child(CadastroActivity.DATABASE_USUARIO).child(uid);

       valueEventListenerUsuario = usuarioEnd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                textViewNome.setText(usuario.getNome());
                textViewTelefone.setText(usuario.getTelefone());
                textViewEndereco.setText(usuario.getEndereco() + ", " + usuario.getNumero());
                textViewBairro.setText(usuario.getBairro());
                textViewComplemento.setText(usuario.getComplemento());
                textViewPedidos.setText("Meus pedidos: "+usuario.getQuantidadePedido());

                //Log.i("TESTE", usuario.getNome());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void carregarPedidos(){
        pedidosReference = reference.child(CarrinhoActivity.DATABASE_PEDIDO).child(uid).child(CarrinhoActivity.DATABASE_RESUMO);

        pedidosReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listResumo.clear();
                for(DataSnapshot dados : snapshot.getChildren()){
                    ResumoPedido resumoPedido = dados.getValue(ResumoPedido.class);
                    listResumo.add(resumoPedido);

                    Log.i("TEXTOLAN",dados.toString());
                    Log.i("TEXTOLAN", Integer.toString(resumoPedido.getNumPedido()));
                }

                meusPedidosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarDadosUsuario();
        carregarPedidos();
    }

    public void fabClick(View view){
        startActivity(new Intent(PrincipalActivity.this,PedidoActivity.class));
        usuarioEnd.removeEventListener(valueEventListenerUsuario);
        finish();
    }

    public void adicionarProdutos(View view){
        Produtos produtos = new Produtos();
        produtos.setNomePizza("Vegetariana");
        produtos.setDescricaoSabor("Mussarela, molho de tomate, azeitona, orégano, palmito e milho.");
        produtos.setImgProduto("https://firebasestorage.googleapis.com/v0/b/pizzaiolo-app.appspot.com/o/imagens%2FVegetariana.jpg?alt=media&token=377f32d7-2481-438f-8197-c0d8b1239a38");
        produtos.setStatusProduto(true);
        produtos.setValorG(40.99);
        produtos.setValorM(35.99);
        produtos.setValorP(29.99);
        reference.child("produto").push().setValue(produtos);
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuarioEnd.removeEventListener(valueEventListenerUsuario);
    }
}