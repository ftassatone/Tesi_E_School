package com.eschool.e_school;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Alunno_HomeMateria extends AppCompatActivity {
    private Button btTeoriaMateria,btEserciziMateria;
    private String url = "http://www.eschooldb.altervista.org/PHP/homeMateria.php";
    private String materia,tipologia;
    private String livello;
    private RequestQueue requestQueue;
    private ListView listContenitore;
    private TextView titolo;
    private ArrayAdapter adapter;
    private ArrayList<String> lista;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_materia);

        btTeoriaMateria = (Button) findViewById(R.id.btTeoriaMateria);
        btEserciziMateria = (Button) findViewById(R.id.btEserciziMateria);
        listContenitore = (ListView) findViewById(R.id.listViewTeoria);
        titolo = (TextView) findViewById(R.id.titolo);

        materia = getIntent().getStringExtra("materia");

        livello = getIntent().getStringExtra("livello");
        Log.d("LOG","ricevo "+materia+"-"+livello);

        btTeoriaMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipologia = btTeoriaMateria.getTag().toString();
                rimpiLista(tipologia);
            }
        });

        btEserciziMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipologia =btEserciziMateria.getTag().toString();
                rimpiLista(tipologia);
            }
        });
    }

    private ArrayList connessione(String tipo){
        final ArrayList list = null;
        HashMap<String,String> parametri = new HashMap();
        parametri.put("materia",materia);
        parametri.put("tipologia",tipo);

        JsonRequest richiesta = new JsonRequest(Request.Method.POST, url, parametri, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("lista");
                    if(tipologia.equalsIgnoreCase("teoria")){
                        for(int i = 0; i<array.length();i++){
                            list.add(array.getJSONObject(i).getString("titolo"));
                        }
                    }else if(tipologia.equalsIgnoreCase("esercizio")){
                        for(int i = 0; i<array.length();i++){
                            list.add(array.getJSONObject(i).getString("codice")+" - "+array.getJSONObject(i).getString("argomento"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(richiesta);
        return list;
    }

    private void rimpiLista(String tipo){
        lista = connessione(tipo);
        //ho usato la riga per il programma
        adapter = new ArrayAdapter(getApplicationContext(),R.layout.riga_lista_programma,lista);
        listContenitore.setAdapter(adapter);
    }
}
