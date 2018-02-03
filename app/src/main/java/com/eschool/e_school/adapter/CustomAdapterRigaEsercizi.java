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
import android.widget.Toast;

import com.eschool.e_school.R;
import com.eschool.e_school.alunno.VisualizzatoreFile;
import com.eschool.e_school.elementiBase.Esercizio;
import com.eschool.e_school.elementiBase.Teoria;

import java.util.ArrayList;

public class CustomAdapterRigaEsercizi extends BaseAdapter{
    private Context context;
    private ArrayList<Esercizio> lista;
    private Esercizio t;

    public CustomAdapterRigaEsercizi(Context context, ArrayList<Esercizio> lista){
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
            t = (Esercizio) getItem(i);

            //nomeFile.setText((i+1) + " - " + t.getTitolo());

            btVisualizza.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Log.d("DATI","file= "+((Esercizio) getItem(i)).getFile().equalsIgnoreCase("null"));
                    /*if(!((Esercizio) getItem(i)).getFile().equalsIgnoreCase("null")) {
                        Log.d("DATI","if");
                        Intent visualizza = new Intent(context, VisualizzatoreFile.class);
                        visualizza.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        visualizza.putExtra("file", ((Esercizio) getItem(i)).getFile());
                        context.startActivity(visualizza);
                    }else{
                        Log.d("DATI","else");
                        Toast.makeText(context, "File assente.", Toast.LENGTH_SHORT).show();
                    }*/
                }
            });

        }
        return view;
    }
}
