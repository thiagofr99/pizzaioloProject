package com.devthiagofurtado.pizzaioloapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devthiagofurtado.pizzaioloapp.Config.ConfiguracaoFirebase;
import com.devthiagofurtado.pizzaioloapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private EditText editTextLogin, editTextSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login");

        editTextLogin = findViewById(R.id.editeTextLogin);
        editTextSenha = findViewById(R.id.editTextSenha);




    }

   public void validarLogin(View view){
        if(!editTextLogin.getText().toString().isEmpty()){
            if(!editTextSenha.getText().toString().isEmpty()){
                autenticacao.signInWithEmailAndPassword(editTextLogin.getText().toString(),editTextSenha.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(LoginActivity.this,PrincipalActivity.class));
                            Toast.makeText(LoginActivity.this,"Login efetuado com sucesso",Toast.LENGTH_SHORT).show();
                        } else {

                            String excessao = "";
                            try{
                                throw task.getException();
                            }
                            catch (FirebaseAuthInvalidCredentialsException e){
                                excessao = "Email ou senha não correspondem a um usuário cadastrado.";
                            }
                            catch (FirebaseAuthInvalidUserException e){
                                excessao = "Email não possui cadastro.";
                            }
                            catch ( Exception e){
                                excessao="Erro a efetuar login: "+ e.getMessage();
                                e.printStackTrace();
                            }



                            Toast.makeText(LoginActivity.this,excessao,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                Toast.makeText(LoginActivity.this,"Preencha o campo Senha",Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(LoginActivity.this,"Preencha o campo Login",Toast.LENGTH_SHORT).show();
        }


   }

}