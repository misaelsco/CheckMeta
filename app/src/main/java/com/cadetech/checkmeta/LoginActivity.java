package com.cadetech.checkmeta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cadetech.checkmeta.dao.UsuarioDAO;
import com.cadetech.checkmeta.dominio.Usuario;

public class LoginActivity extends Activity implements View.OnClickListener{

    private TextView tvCadastrar;
    private EditText etEmail;
    private EditText etPassword;
    private UsuarioDAO usuarioDAO;
    private Context context;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvCadastrar = (TextView) findViewById(R.id.tvCadastroLink);
        tvCadastrar.setOnClickListener(this);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.tvCadastroLink:
                onClickCadastrar();
                break;
            case R.id.btnLogin:
                doLogin();
                break;
        }
    }

    //Chama a tela de cadastro quando clicado no TextView com a frase de cadastro
    public void onClickCadastrar()
    {
        Log.d("Teste", "entrei na tela de cadastro");
        //Intent iCadastro  = new Intent(Login.this, CadastroActivity.class);
        //Login.this.startActivity(iCadastro);
    }

    public void doLogin()
    {
        //TODO Inserir log
        usuarioDAO = new UsuarioDAO(this);
        //TODO Validar e-mail
        if(etEmail.getText().toString().length() > 6){
            if(etPassword.getText().toString().trim().length() >= 6)
            {
                Usuario usuario = usuarioDAO.isValidCredentials(etEmail.getText().toString(), etPassword.getText().toString());
                //if(usuario != null){
                if(1+1==2){
                    goToMainActivity();
                }
                else
                {
                    Toast.makeText(this, "E-mail e/ou Senha inválido", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(this, "A senha contém no mínimo 6 caracteres", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this, "Digite um e-mail válido", Toast.LENGTH_LONG).show();
        }
    }

    public void goToMainActivity(){
        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
        LoginActivity.this.startActivity(i);
        this.finish();
    }
}
