package com.eschool.e_school.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.eschool.e_school.R;
import com.eschool.e_school.alunno.HomeAlunno;
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
        bt.setBackgroundColor(context.getColor(R.color.trasparente));
        bt.setTextColor(Color.BLACK);
        bt.setBackground(context.getDrawable(R.drawable.button));
        if(c.equalsIgnoreCase("matematica")){
            bt.setCompoundDrawablesWithIntrinsicBounds( R.drawable.icona_mate, 0, 0, 0);
        }else if(c.equalsIgnoreCase("italiano")){
            bt.setCompoundDrawablesWithIntrinsicBounds( R.drawable.icona_ita, 0, 0, 0);
        }else if(c.equalsIgnoreCase("Storia")){
            bt.setCompoundDrawablesWithIntrinsicBounds( R.drawable.icona_storia2, 0, 0, 0);
        }else if(c.equalsIgnoreCase("Geografia")){
            bt.setCompoundDrawablesWithIntrinsicBounds( R.drawable.icona_geo2, 0, 0, 0);
        }

        final String finalC = c;
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,HomeMateria.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("materia", finalC);
                intent.putExtra("livello",liv);
                intent.putExtra("alunno", HomeAlunno.al);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
