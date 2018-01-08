package com.eschool.e_school.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.eschool.e_school.R;
import com.eschool.e_school.alunno.HomeMateria;

import java.util.ArrayList;

public class ButtonAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Button> lista;
    private ArrayList<String> mat;
    private String liv;

    public ButtonAdapter(Context c, ArrayList<Button> l, ArrayList<String> mat,String liv) {
        this.context = c;
        this.lista = l;
        this.mat = mat;
        this.liv = liv;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.d("LOG","liv "+liv);
        String c = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.cella_materia,null);
        }
        Button bt = (Button) view.findViewById(R.id.btMateria);
        c= mat.get(i);
        bt.setText(c);
        final String finalC = c;
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,HomeMateria.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("materia", finalC);
                intent.putExtra("livello",liv);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
