package com.cadetech.checkmeta;

import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.cadetech.checkmeta.dao.MetaDAO;
import com.cadetech.checkmeta.dominio.Meta;
import com.cadetech.checkmeta.utils.DateDialog;


public class MetaActivity extends AppCompatActivity {


    private EditText txtTitulo;
    private EditText txtDescricao;
    private Button btnDataDesejada;
    private Button btnDataRealizada;
    private Spinner spStatus;
    private TextView tvStatus;
    private TextView tvDataRealizada;
    private Button btnCadastrar;
    private Resources resources;




    private MetaDAO dao;
    private Meta meta;
    private Meta oldMeta;
    Long idMeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta);

        dao = new MetaDAO(this);
        resources = getResources();


        txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        txtDescricao = (EditText) findViewById(R.id.txtDescricao);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

        btnDataDesejada = (Button) findViewById(R.id.btnDataDesejada);
        Log.i("btnDesejada", String.valueOf(btnDataDesejada.getId()));

        btnDataRealizada = (Button) findViewById(R.id.btnDataRealizada);
        Log.i("btnRealizada", String.valueOf(btnDataRealizada.getId()));

        tvDataRealizada = (TextView) findViewById(R.id.tvDataRealizada);
        tvStatus = (TextView) findViewById(R.id.tvStatusMeta);
        spStatus = (Spinner) findViewById(R.id.cbStatus);



        if (getIntent().hasExtra("metaId")) {
            Bundle extras = getIntent().getExtras();
            idMeta = extras.getLong("metaId");

            btnDataRealizada.setVisibility(View.VISIBLE);
            tvStatus.setVisibility(View.VISIBLE);
            tvDataRealizada.setVisibility(View.VISIBLE);
            btnDataRealizada.setVisibility(View.VISIBLE);
            tvStatus.setVisibility(View.VISIBLE);
            spStatus.setVisibility(View.VISIBLE);

            oldMeta = dao.select(idMeta);
            txtTitulo.setText(oldMeta.getTitulo());
            txtDescricao.setText(oldMeta.getDescricao());
            btnDataDesejada.setText(oldMeta.getDataDesejada());
            btnDataRealizada.setText(oldMeta.getDataRealizada());
            spStatus.setSelection(selectSpinner(oldMeta.getStatus()));
        }


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            String titulo = txtTitulo.getText().toString();
            String dataDesejada = btnDataDesejada.getText().toString();

            if (!isEmptyFields(titulo, dataDesejada) && isContentValid(titulo, dataDesejada)) {
                if(getIntent().hasExtra("metaId"))
                    editar();
                else
                    cadastrar();
            }
            }
        });

    }

    public void showDatePickerDialog(View v) {

        DateDialog newFragment = new DateDialog();

       /* if(v == btnDataDesejada){
            newFragment.quemChamou = 1;
        }else{
            newFragment.quemChamou = 2;
        }*/
        newFragment.target  = (Button) v;

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        newFragment.show(ft, "datePicker");

    }

    private int selectSpinner(String text){
        if(text != null){
            if(text.equals("Pendente"))
                return 0;
            if(text.equals("Atrasada"))
                return 1;
            if(text.equals("Realizada"))
                return 2;
            if(text.equals("Despriorizada"))
                return 3;
        }
        return 0;
    }


    public void editar(){

        Meta meta = new Meta();
        meta.setId(idMeta);
        meta.setTitulo(txtTitulo.getText().toString());
        meta.setDescricao(txtDescricao.getText().toString());
        meta.setDataDesejada(btnDataDesejada.getText().toString());
        meta.setDataRealizada(btnDataRealizada.getText().toString());
        meta.setStatus(spStatus.getSelectedItem().toString());

        //Compara se o usuário fez alterações na Meta
        if(!oldMeta.equals(meta)) {
            boolean updatedId = dao.update(meta);
            if (updatedId) {
                Toast.makeText(MetaActivity.this, "Meta editada com sucesso", Toast.LENGTH_LONG).show();
                this.finish();
            } else {
                Toast.makeText(MetaActivity.this, "Falha ao editar meta", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            this.finish();
        }

    }
















































    public void cadastrar() {

        meta = new Meta();
        meta.setIdUsuario(1L);
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
