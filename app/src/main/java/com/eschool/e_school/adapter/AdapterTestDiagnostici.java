package com.eschool.e_school.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.eschool.e_school.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FileUtils;

import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.InputStream;
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
    public String getItem(int i) {
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
            final String file = getItem(i);
            String fileName = file.substring(file.lastIndexOf('/')+1);
            TextView test = view.findViewById(R.id.test);
            test.setText(fileName);
            ImageButton bt = view.findViewById(R.id.visualizza);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setDataAndType(Uri.parse(file), "application/pdf");
                    Intent chooser = Intent.createChooser(browserIntent, "");
                    chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // optional
                    context.startActivity(chooser);
                }
            });
        }
        return view;
    }
}
