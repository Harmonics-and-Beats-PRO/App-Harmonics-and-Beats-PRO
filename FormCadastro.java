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
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseAuthWebException;

public class FormCadastro extends AppCompatActivity {

    private Button bt_irParaLogin ;
    private EditText edit_username_cadastro;
    private EditText edit_email_cadastro;
    private EditText edit_senha_cadastro;
    private EditText edit_cadastro;
    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private Button bt_cadastro;
    private EditText edit_mensagem_erro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        init();
        bt_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_email_cadastro.getText().toString() == null){
                    edit_mensagem_erro.setText("Preencha o E-mail");
                }else if(edit_senha_cadastro.getText().toString() == null){
                    edit_mensagem_erro.setText("Preencha a Senha");
                }else {
                    cadastrar_usuario();
                }
            }
        });
        bt_irParaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FormLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void init(){
        bt_irParaLogin = findViewById(R.id.bt_irParaLogin);
        edit_username_cadastro = findViewById(R.id.edit_username_cadastro);
        edit_email_cadastro = findViewById(R.id.edit_email_cadastro);
        edit_senha_cadastro = findViewById(R.id.edit_senha_cadastro);
        edit_cadastro = findViewById(R.id.edit_confirmar_senha_cadastro);
        bt_cadastro = findViewById(R.id.bt_cadastro);
        edit_mensagem_erro = findViewById(R.id.mensagem_erro);
    }
    private void cadastrar_usuario(){
        String email = edit_email_cadastro.getText().toString();
        String senha = edit_senha_cadastro.getText().toString();
        usuario.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    VoltarParaActivityFormLogin();
                    Toast.makeText(getApplicationContext(), "Cadastro efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        edit_mensagem_erro.setText("Digite uma senha com no mínimo 6 caracteres");
                    }catch (FirebaseAuthInvalidUserException e){
                        edit_mensagem_erro.setText("Digite um usuário válido");
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        edit_mensagem_erro.setText("Digite um e-mail válido");
                    }catch (FirebaseAuthUserCollisionException e){
                        edit_mensagem_erro.setText("Usuário já cadastrado");
                    }catch (FirebaseAuthWebException e){
                        edit_mensagem_erro.setText("Sem conexão com a internet");
                    } catch (Exception e) {
                        edit_mensagem_erro.setText("Ao cadastrar usuário:" + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void VoltarParaActivityFormLogin(){
       Intent intent = new Intent(FormCadastro.this,FormLogin.class);
       startActivity(intent);
    }
    }