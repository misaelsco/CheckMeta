package com.cadetech.checkmeta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.cadetech.checkmeta.adapters.AdapterMeta;
import com.cadetech.checkmeta.dao.MetaDAO;
import com.cadetech.checkmeta.dominio.Meta;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Context context;
    private MetaDAO dao;
    private ListView listMetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = this.getBaseContext();

        dao = new MetaDAO(this);
        listMetas = (ListView) findViewById(R.id.listMetas);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabNovaMeta);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, MetaActivity.class);
                HomeActivity.this.startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarMetas();
    }

    private void carregarMetas(){
        List<Meta> metas = dao.selectAll();
        AdapterMeta adapterMeta = new AdapterMeta(HomeActivity.this, R.layout.list_meta_row, metas);
        listMetas.setAdapter(adapterMeta);
        adapterMeta.notifyDataSetChanged();
    }
}
