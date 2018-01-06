package com.eschool.e_school;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapterHM extends BaseAdapter {

    private List<Teoria> lista = null;
    private Context context = null;

    public CustomAdapterHM (Context context, List<Teoria> lista){
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.riga_lista_programma, null);
        }
        return null;
    }
}
