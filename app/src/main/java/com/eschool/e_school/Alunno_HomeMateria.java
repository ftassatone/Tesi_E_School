package com.eschool.e_school;

import android.os.AsyncTask;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Alunno_HomeMateria extends AppCompatActivity {
    private Button btTeoriaMateria,btEserciziMateria;
    private String url = "http://www.eschooldb.altervista.org/PHP/homeMateria.php";
    private String materia,tipologia;
    private String livello;
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
        Log.d("DATI","ricevo "+materia+"-"+livello);

        btTeoriaMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipologia = btTeoriaMateria.getTag().toString();
                //rimpiLista(tipologia);
                //new X().execute();
            }
        });

        btEserciziMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipologia =btEserciziMateria.getTag().toString();
                //rimpiLista(tipologia);
                new X().execute();
            }
        });
    }

    private class X extends AsyncTask<Void,Void,Void> {
        JSONObject rispFuture;
        //ArrayList list;

        @Override
        protected Void doInBackground(Void... voids) {
            final ArrayList list = new ArrayList();
            HashMap<String, String> parametri = new HashMap();
            parametri.put("materia", materia);
            parametri.put("tipologia", tipologia);
            parametri.put("livello", livello);
            Log.d("DATI", "materia-" + materia + "--tipologia-" + tipologia + "--livello-" + livello);

            RequestFuture<JSONObject> future = RequestFuture.newFuture();
            JsonRequest richiesta = new JsonRequest(Request.Method.POST, url, parametri, future, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("DATI", "err");
                }
            });
            Log.d("DATI", "fuori");
            RequestSingleton.getInstance(getApplicationContext()).addToRequestQueue(richiesta);
            try {
                rispFuture = future.get(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            Log.d("DATI", "lista2-" + list);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            JSONArray array;
            try {
                array = rispFuture.getJSONArray("lista");
                if (tipologia.equalsIgnoreCase("teoria")) {
                    for (int i = 0; i < array.length(); i++) {
                        Log.d("DATI", i + "--" + array.getJSONObject(i).getString("titolo"));
                        lista.add(array.getJSONObject(i).getString("titolo"));
                    }
                    adapter = new ArrayAdapter(getApplicationContext(), R.layout.riga_lista_programma, lista);
                    listContenitore.setAdapter(adapter);
                } else if (tipologia.equalsIgnoreCase("esercizio")) {
                    for (int i = 0; i < array.length(); i++) {
                        lista.add(array.getJSONObject(i).getString("codice") + " - " + array.getJSONObject(i).getString("argomento"));
                    }
                    adapter = new ArrayAdapter(getApplicationContext(), R.layout.riga_lista_programma, lista);
                    listContenitore.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private ArrayList connessione(String tipo){
        Log.d("DATI","sono in connessione");
        final ArrayList list = new ArrayList();
        HashMap<String,String> parametri = new HashMap();
        parametri.put("materia",materia);
        parametri.put("tipologia",tipo);
        parametri.put("livello",livello);
        Log.d("DATI","materia-"+materia+"--tipologia-"+tipo+"--livello-"+livello);

        JsonRequest richiesta = new JsonRequest(Request.Method.POST, url, parametri, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("DATI","sono qui");
                    JSONArray array = response.getJSONArray("lista");
                    Log.d("DATI","array- "+array);
                    if(tipologia.equalsIgnoreCase("teoria")){
                        for(int i = 0; i<array.length();i++){
                            Log.d("DATI",i+"--"+array.getJSONObject(i).getString("titolo"));
                            list.add(array.getJSONObject(i).getString("titolo"));
                        }
                    }else if(tipologia.equalsIgnoreCase("esercizio")){
                        for(int i = 0; i<array.length();i++){
                            list.add(array.getJSONObject(i).getString("codice")+" - "+array.getJSONObject(i).getString("argomento"));
                        }
                    }
                } catch (JSONException e) {
                    Log.d("DATI",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("DATI","err");
            }
        });
        Log.d("DATI","fuori");
        RequestSingleton.getInstance(this).addToRequestQueue(richiesta);
        Log.d("DATI","lista2-"+list);
        return list;
    }

    private void rimpiLista(String tipo){
        Log.d("DATI","sono in riempi");
        new X().execute();
        //lista = connessione(tipo);
        Log.d("DATI","lista-"+lista);
        //ho usato la riga (layout) per il programma
        if(!lista.isEmpty()) {
            adapter = new ArrayAdapter(getApplicationContext(), R.layout.riga_lista_programma, lista);
            listContenitore.setAdapter(adapter);
        }
    }
}
