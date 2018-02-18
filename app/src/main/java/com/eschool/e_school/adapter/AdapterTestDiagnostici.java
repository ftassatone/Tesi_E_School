package com.eschool.e_school.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.eschool.e_school.R;
import java.util.ArrayList;

public class AdapterTestDiagnostici extends BaseAdapter {
    private ArrayList<String> listTest;
    private Context context = null;
    private String t;

    public AdapterTestDiagnostici(Context context, ArrayList<String> listTest){
        this.listTest = listTest;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listTest.size();
    }

    @Override
    public Object getItem(int i) {
        return listTest.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.riga_lista_test, null);
            TextView test = view.findViewById(R.id.test);
            t = listTest.get(i);
            test.setText(t);
            ImageButton bt = view.findViewById(R.id.visualizza);
        }
        return view;
    }
}
