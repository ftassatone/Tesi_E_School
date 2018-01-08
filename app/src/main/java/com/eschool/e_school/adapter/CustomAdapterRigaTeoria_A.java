package com.eschool.e_school.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eschool.e_school.R;
import com.eschool.e_school.alunno.Alunno_VisualizzatoreFile;
import com.eschool.e_school.elementiBase.Teoria;

import java.util.ArrayList;

public class CustomAdapterRigaTeoria_A extends BaseAdapter{
    private Context context;
    private ArrayList<Teoria> lista;
    private Teoria t;

    public CustomAdapterRigaTeoria_A(Context context, ArrayList<Teoria> lista){
        this.context = context;
        this.lista = lista;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.riga_teoria,null);
            TextView nomeFile = view.findViewById(R.id.nomeFile);
            ImageButton btVisualizza = view.findViewById(R.id.visualizzaFile);
            t = (Teoria) getItem(i);
            Log.d("DATI","TTTT--- "+t);

            nomeFile.setText((i+1) + ". - " + t.getArgomento());

            btVisualizza.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent visualizza = new Intent(context, Alunno_VisualizzatoreFile.class);
                    visualizza.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    visualizza.putExtra("file", ((Teoria) getItem(i)).getFile());
                    context.startActivity(visualizza);
                }
            });

        }
        return view;
    }
}
