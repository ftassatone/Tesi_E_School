package com.eschool.e_school;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeMateria extends AppCompatActivity {
    private Button btTeoriaMateria,btEserciziMateria;
    private String url = "http://www.eschooldb.altervista.org/PHP/homeMateria.php";
    private ListView listContenitore;
    private TextView titolo;
    private ArrayAdapter adapter;
    private ArrayList lista;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_materia);

        btTeoriaMateria = (Button) findViewById(R.id.btTeoriaMateria);
        btEserciziMateria = (Button) findViewById(R.id.btEserciziMateria);
        listContenitore = (ListView) findViewById(R.id.listViewTeoria);
        titolo = (TextView) findViewById(R.id.titolo);

        //adapter = new ArrayAdapter(getApplicationContext(),R.layout.riga,lista);

        btTeoriaMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btEserciziMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
