package com.cadetech.checkmeta;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.cadetech.checkmeta.dao.MetaDAO;
import com.cadetech.checkmeta.dominio.Meta;
import com.cadetech.checkmeta.utils.DateDialog;

public class MetaActivity extends AppCompatActivity {

    private EditText txtTitulo;
    private EditText txtDescricao;
    private Button btnDataDesejada;
    private Button btnCadastrar;
    private Resources resources;

    private MetaDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta);

        dao = new MetaDAO(this);
        resources = getResources();

        txtTitulo = (EditText)findViewById(R.id.txtTitulo);
        txtDescricao = (EditText) findViewById(R.id.txtDescricao);
        btnDataDesejada = (Button) findViewById(R.id.btnDataDesejada);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titulo = txtTitulo.getText().toString();
                String dataDesejada = btnDataDesejada.getText().toString();

                if(!isEmptyFields(titulo, dataDesejada) && isContentValid(titulo, dataDesejada)){
                    cadastrar();
                }
                //isEmptyFields(txtTitulo.getText().toString());
                //cadastrar();
            }
        });

    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DateDialog();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        newFragment.show(ft, "datePicker");
    }

    public void cadastrar() {

        Meta meta = new Meta();
        meta.setTitulo(txtTitulo.getText().toString());
        meta.setDescricao(txtDescricao.getText().toString());
        meta.setDataDesejada(btnDataDesejada.getText().toString());
        meta.setStatus("Pendente");

        Long insertedId = dao.insert(meta);
        if (insertedId > -1) {
            Toast.makeText(MetaActivity.this, "Meta cadastrada com sucesso", Toast.LENGTH_LONG).show();
            this.finish();
        } else {
            Log.d("ERROR", "Erro ao cadastrar meta");
            Toast.makeText(MetaActivity.this, "Erro ao cadastrar meta", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isEmptyFields(String titulo, String dataDesejada) {
        if (TextUtils.isEmpty(titulo)) {
            txtTitulo.requestFocus();
            txtTitulo.setError(resources.getString(R.string.required_field));
            return true;
        }
        if(TextUtils.isEmpty(dataDesejada)){
            btnDataDesejada.requestFocus();
            btnDataDesejada.setError(resources.getString(R.string.required_field));
            return true;
        }
        return false;
    }

    private boolean isContentValid(String titulo, String dataDesejada){
        if (titulo.length() < 10) {
            txtTitulo.requestFocus();
            txtTitulo.setError(resources.getString(R.string.required_title));
            return false;
        }
        if(dataDesejada.length() < 1)
        {
            btnDataDesejada.requestFocus();
            btnDataDesejada.setError(resources.getString(R.string.required_field));
            return false;
        }
        return true;
    }
}
