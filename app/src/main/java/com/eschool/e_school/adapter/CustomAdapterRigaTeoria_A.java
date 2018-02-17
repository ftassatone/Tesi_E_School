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
import com.eschool.e_school.alunno.HomeAlunno;
import com.eschool.e_school.alunno.VisualizzatoreFile;
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
            //view.setBackground(context.getDrawable(R.drawable.liste));
            TextView nomeFile = view.findViewById(R.id.nomeFile);
            final ImageButton btVisualizza = view.findViewById(R.id.visualizzaFile);
            t = (Teoria) getItem(i);

            nomeFile.setText((i+1) + " - " + t.getTitolo());

            btVisualizza.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DATI","file= "+((Teoria) getItem(i)).getFile().equalsIgnoreCase("null"));
                    if(!((Teoria) getItem(i)).getFile().equalsIgnoreCase("null")) {
                        Log.d("DATI","if");
                        Intent visualizza = new Intent(context, VisualizzatoreFile.class);
                        //Intent visualizza = new Intent(context, VisualizzatoreFile.class);
                        visualizza.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        visualizza.putExtra("file", ((Teoria) getItem(i)).getFile());
                        Log.d("ALUNNO","DSA adapter "+HomeAlunno.al.getDsa());
                        visualizza.putExtra("dsa", HomeAlunno.al.getDsa());
                        context.startActivity(visualizza);
                    }else{
                        Log.d("DATI","else");
                        Toast.makeText(context, "File assente.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        return view;
    }
}
