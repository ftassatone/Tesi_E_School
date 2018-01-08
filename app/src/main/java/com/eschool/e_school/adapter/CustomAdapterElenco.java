package com.eschool.e_school.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eschool.e_school.elementiBase.Alunno;
import com.eschool.e_school.docente.Docente_SchedaAlunno;
import com.eschool.e_school.R;
import com.eschool.e_school.elementiBase.Teoria;

import java.io.Serializable;
import java.util.List;

public class CustomAdapterElenco extends BaseAdapter{

    private List<Teoria> lista = null;
    private Context context = null;
    private Alunno al;

    public CustomAdapterElenco(Context context, List<Teoria> lista){
        this.lista = lista;
        this.context = context;
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
        if (view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.layout_riga_elenco, null);
            TextView nominativo = (TextView) view.findViewById(R.id.nominativo);
            ImageButton bt = (ImageButton) view.findViewById(R.id.visualizza);

            al = (Alunno) getItem(i) ;
            nominativo.setText((i+1)+". - "+al.getNome() +" "+ al.getCognome());
            view.setTag(i);

            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent vaiSchedaAlunno = new Intent(context, Docente_SchedaAlunno.class);
                    vaiSchedaAlunno.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    vaiSchedaAlunno.putExtra("Alunno", (Serializable) getItem(i));
                    context.startActivity(vaiSchedaAlunno);
                }
            });
        }
        return view;
    }

}
