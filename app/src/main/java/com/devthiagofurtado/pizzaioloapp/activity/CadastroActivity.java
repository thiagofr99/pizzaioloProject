package com.devthiagofurtado.pizzaioloapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.devthiagofurtado.pizzaioloapp.Config.ConfiguracaoFirebase;
import com.devthiagofurtado.pizzaioloapp.Model.Usuario;
import com.devthiagofurtado.pizzaioloapp.R;
import com.devthiagofurtado.pizzaioloapp.Util.MaskEditUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;

public class CadastroActivity extends AppCompatActivity {
    private EditText textInputNome, textInputSenha, textInputTelefone, textInputEndereco, textInputNumero, textInputComplemento, textInputBairro, textInputEmail;
    private Usuario usuario;
    private FirebaseAuth autenticar = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfiguracaoFirebase.getDatabaseReference();
    public static String DATABASE_USUARIO = "usuario";
    public static String DATABASE_CUPOM = "cupons";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        getSupportActionBar().setTitle("Cadastro");




        textInputNome = findViewById(R.id.textInputEditTextNome);
        textInputSenha = findViewById(R.id.textInputEditTextSenha);
        textInputTelefone = findViewById(R.id.textInputEditTextTelefone);
        textInputEndereco = findViewById(R.id.textInputEditTextEndereco);
        textInputNumero = findViewById(R.id.textInputEditTextNumero);
        textInputComplemento = findViewById(R.id.textInputEditTextComplemento);
        textInputBairro = findViewById(R.id.textInputEditTextBairro);
        textInputEmail = findViewById(R.id.textInputEditTextEmail);

        textInputTelefone.addTextChangedListener(MaskEditUtil.mask(textInputTelefone, MaskEditUtil.FORMAT_FONE));
        textInputSenha.addTextChangedListener(MaskEditUtil.mask(textInputSenha, MaskEditUtil.FORMAT_SENHA));


    }

    public void salvarCampos(View view) {
        if (verificarCampos()) {
            usuario = new Usuario();
            usuario.setNome(textInputNome.getText().toString());
            usuario.setEmail(textInputEmail.getText().toString());
            usuario.setSenha(textInputSenha.getText().toString());
            usuario.setTelefone(textInputTelefone.getText().toString());
            usuario.setEndereco(textInputEndereco.getText().toString());
            usuario.setNumero(textInputNumero.getText().toString());
            usuario.setComplemento(textInputComplemento.getText().toString());
            usuario.setBairro(textInputBairro.getText().toString());


            //Log.i("Usuarios",usuario.getTelefone());
            autenticar.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //Logar usuario logado
                        autenticar.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha());

                        //Busca o UID do usuario logado
                        String uid = autenticar.getCurrentUser().getUid();

                        //Cria novo usuario para salvar no FirebaseDatabase salvando nome do usuario, email, telefone, endereco, numero, complemento, bairro e quantidade de pedidos
                        usuario.setQuantidadePedido(0);
                        usuario.salvar(uid);

                        //Cria primeiro cupom para usuario
                        reference.child(DATABASE_CUPOM).child("1cupom").child(uid).child("CD_CUPOM").setValue("PIZZAIOLO1");
                        reference.child(DATABASE_CUPOM).child("1cupom").child(uid).child("VALOR_DESCONTO").setValue(10.00);
                        reference.child(DATABASE_CUPOM).child("1cupom").child(uid).child("QTD_CUPOM").setValue(1);

                        //Encerra cadastro da activity
                        finish();

                        //Toast confirmando o sucesso do cadastro
                        Toast.makeText(CadastroActivity.this, "Salvo com sucesso.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CadastroActivity.this,PrincipalActivity.class));

                    } else {
                        String excessao = "";
                        try{
                            throw task.getException();
                        } catch (FirebaseAuthWeakPasswordException e){
                            excessao = "Digite uma senha mais forte!";
                        }
                        catch (FirebaseAuthInvalidCredentialsException e){
                            excessao = "Digite um e-mail válido!";
                        }
                        catch (FirebaseAuthUserCollisionException e){
                            excessao = "Esta conta já foi cadastrada";
                        }
                        catch ( Exception e){
                            excessao="Erro a cadastrar usuário: "+ e.getMessage();
                            e.printStackTrace();
                        }


                        Toast.makeText(CadastroActivity.this,excessao,Toast.LENGTH_SHORT).show();
                    }
                }
            });




        }
    }


    public boolean verificarCampos() {


        if (!textInputNome.getText().toString().isEmpty()) {
            if (!textInputEmail.getText().toString().isEmpty()) {
                if (!textInputSenha.getText().toString().isEmpty()) {
                    if (!textInputTelefone.getText().toString().isEmpty()) {
                        if (!textInputEndereco.getText().toString().isEmpty()) {
                            if (!textInputNumero.getText().toString().isEmpty()) {
                                if (!textInputComplemento.getText().toString().isEmpty()) {
                                    if (!textInputBairro.getText().toString().isEmpty()) {
                                        String bairro = textInputBairro.getText().toString().toLowerCase();
                                        if (bairro.equals("meireles") || bairro.equals("aldeota") || bairro.equals("papicu") || bairro.equals("coco") || bairro.equals("mucuripe") || bairro.equals("varjota")) {
                                            return true;
                                        } else {
                                            Toast.makeText(this, "Fora da area de entrega do APP, desculpa.", Toast.LENGTH_SHORT).show();
                                            return false;
                                        }

                                    } else {
                                        Toast.makeText(this, "Preencha o campo Bairro", Toast.LENGTH_SHORT).show();
                                        return false;
                                    }

                                } else {
                                    Toast.makeText(this, "Preencha o campo Complemento", Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                            } else {
                                Toast.makeText(this, "Preencha o campo Numero", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        } else {
                            Toast.makeText(this, "Preencha o campo Endereco", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    } else {
                        Toast.makeText(this, "Preencha o campo Telefone", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(this, "Preencha o campo Senha", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                Toast.makeText(this, "Preencha o campo Email", Toast.LENGTH_SHORT).show();
                return false;
            }

        } else {
            Toast.makeText(this, "Preencha o campo Nome", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}