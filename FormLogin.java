package com.app.hnbpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseAuthWebException;
import com.google.firebase.auth.FirebaseUser;

public class FormLogin extends AppCompatActivity {

    private EditText edit_email_login;
    private EditText edit_username_senha_login;
    private Button bt_irParaCadastro;
    private Button bt_Login;
    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private EditText erro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        init();

        bt_irParaCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FormCadastro.class);
                startActivity(intent);
                finish();
            }
        });

        bt_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_email_login.getText().toString() == null){
                    erro.setText("Preencha o E-mail");
                }else if(edit_username_senha_login.getText().toString() == null){
                    erro.setText("Preencha a Senha");
                }else {
                    Logar_Usuario();
                }
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser usuarioAtual = usuario.getCurrentUser();
        if (usuarioAtual != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
    public void init(){
        bt_irParaCadastro = findViewById(R.id.bt_irParaCadastro);
        edit_email_login = findViewById(R.id.edit_email_login);
        edit_username_senha_login = findViewById(R.id.edit_senha_login);
        bt_Login = findViewById(R.id.bt_Login);
        erro = findViewById(R.id.Erro); }

    public void Logar_Usuario() {
        String email = edit_email_login.getText().toString();
        String senha = edit_username_senha_login.getText().toString();
        usuario.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro.setText("Senha Inválida");
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro.setText("Senha ou E-mail inválidos");
                    } catch (FirebaseAuthWebException e) {
                        erro.setText("Sem conexão com a internet");
                    } catch (Exception e) {
                        erro.setText("Ao logar o usuário:" + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}