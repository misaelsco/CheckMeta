package com.cadetech.checkmeta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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


        listMetas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = listMetas.getItemAtPosition(position);
                TextView itemID = (TextView) view.findViewById(R.id.tvIdMeta);
                Log.d("[ChangeActivity]", "Home > Meta id: " + itemID.getText().toString());
                Intent i = new Intent(HomeActivity.this, MetaActivity.class);
                i.putExtra("metaId", Long.parseLong(itemID.getText().toString()));
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
