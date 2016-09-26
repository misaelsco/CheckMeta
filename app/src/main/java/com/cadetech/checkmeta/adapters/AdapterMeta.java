package com.cadetech.checkmeta.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cadetech.checkmeta.R;
import com.cadetech.checkmeta.dominio.Meta;

import java.util.List;

/**
 * Created by misael.correia on 24/09/2016.
 */
public class AdapterMeta extends ArrayAdapter<Meta>{

    private int resource;
    private LayoutInflater inflater;

    public AdapterMeta(Context context, int resourceId, List<Meta> metas){
        super(context,resourceId,metas);
        resource = resourceId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(convertView == null){
            view = inflater.inflate(resource,null);
        }

        Meta meta = getItem(position);

        TextView tvIdMeta = (TextView) view.findViewById(R.id.tvIdMeta);
        tvIdMeta.setText(meta.getId().toString());

        TextView tvNome = (TextView)view.findViewById(R.id.tvName);
        tvNome.setText(meta.getTitulo().toString());

        TextView tvDescription = (TextView)view.findViewById(R.id.tvDescription);
        String descricao = meta.getDescricao().toString();

        //Se a meta não possui descrição, ela replica o título da meta no campo descrição apenas no listview
        if(descricao.isEmpty())
            tvDescription.setText(meta.getTitulo().toString());
        else
            tvDescription.setText(descricao);

        TextView tvDueDate = (TextView)view.findViewById(R.id.tvMetaDueDate);
        tvDueDate.setText(meta.getDataDesejada().toString());

        TextView tvStatus = (TextView) view.findViewById(R.id.tvStatusMeta);
        tvStatus.setText(meta.getStatus().toString());
        if(meta.getStatus().toString().equals("Atrasada"))
            tvStatus.setTextColor(Color.RED);
        if(meta.getStatus().toString().equals("Pendente"))
            tvStatus.setTextColor(Color.BLUE);
        if(meta.getStatus().toString().equals("Realizada"))
            tvStatus.setTextColor(Color.BLACK);
        if(meta.getStatus().toString().equals("Despriorizada"))
            tvStatus.setTextColor(Color.BLACK);

        return view;

    }
}
