package com.devthiagofurtado.pizzaioloapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.devthiagofurtado.pizzaioloapp.Config.ConfiguracaoFirebase;
import com.devthiagofurtado.pizzaioloapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainActivity extends IntroActivity {
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setButtonBackVisible(false);
        setButtonNextVisible(false);

        verificarUsuarioLogado();

        addSlide(new FragmentSlide.Builder()
                .background(R.color.startColorGradient)
                .fragment(R.layout.intro_01)
                .build()
        );
        addSlide(new FragmentSlide.Builder()
                .background(R.color.startColorGradient)
                .fragment(R.layout.intro_02)
                .build()
        );
        addSlide(new FragmentSlide.Builder()
                .background(R.color.startColorGradient)
                .fragment(R.layout.intro_cadastro)
                .canGoForward(false)
                .build()
        );


    }

    public void selecionarCadastrese(View view){
        startActivity(new Intent(MainActivity.this,CadastroActivity.class));
    }
    public void selecionarLogin(View view){
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }

    public void verificarUsuarioLogado() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this,PrincipalActivity.class));
        }
    }
}