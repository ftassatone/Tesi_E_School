package com.eschool.e_school;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LenovoZ70 on 17/09/2017.
 */
public class HomeDocente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_docente);

        TextView txParteDiagnostica = (TextView) findViewById(R.id.txParteDiagnostica);
        txParteDiagnostica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeDocente.this,SezioneDiagnostica.class);
                startActivity(i);
            }
        });
        ListView lvMaterie = (ListView) findViewById(R.id.lvMaterie);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,ricezioneDatiIntent());
        lvMaterie.setAdapter(adapter);

    }

    public ArrayList<String> ricezioneDatiIntent(){
        Bundle dati = getIntent().getExtras();
        ArrayList<String> lista = new ArrayList<>();
        for(int i=0; i < dati.size(); i++){
            lista.add(dati.getString("arrayMaterie"));
        }
        return lista;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_classe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_impostazioni) {
            Intent i = new Intent(HomeDocente.this,SezioneScegliClasse.class);
            startActivity(i);
        }else if (id == R.id.action_logout){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

