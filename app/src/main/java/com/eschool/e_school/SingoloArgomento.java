package com.eschool.e_school;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SingoloArgomento extends AppCompatActivity {
    private ListView listViewEsercizi,listViewTeoria;
    private Button btMultiple,btAperte,btFile,btCaricaFile;
    private String argo;
    private  String url = "http://www.eschooldb.altervista.org/PHP/SingoloArgomento.php";
    private RequestQueue requestQueue;
    private ArrayList<Teoria> listaTeoria;
    private ArrayList<Esercizio> listaEsercizi;
    private ArrayList<String> righeTeoria, righeEsercizi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singolo_argomento);
        Log.d("LOG","entro");
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        //argo = getIntent().getStringExtra("argomento");
        argo = "operazioni";
        connessione();
        Log.d("LOG","es-"+righeEsercizi.size());
        Log.d("LOG","teo-"+righeTeoria.size());
        listViewEsercizi = (ListView) findViewById(R.id.listViewEsercizi);
        ArrayAdapter adapterEsercizi = new ArrayAdapter(getApplicationContext(),R.layout.riga_lista_programma,righeEsercizi);
        listViewEsercizi.setAdapter(adapterEsercizi);
        listViewTeoria = (ListView) findViewById(R.id.listViewTeoria);
        ArrayAdapter adapterTeoria = new ArrayAdapter(getApplicationContext(), R.layout.riga_lista_programma,righeTeoria);
        listViewTeoria.setAdapter(adapterTeoria);

        btAperte = (Button) findViewById(R.id.btAperte);
        btMultiple = (Button) findViewById(R.id.btMultiple);
        btCaricaFile = (Button) findViewById(R.id.btCaricaFile);
        btFile = (Button) findViewById(R.id.btFile);

        btAperte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btCaricaFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btMultiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void connessione(){
        Log.d("LOG","entro in conn");
        HashMap<String,String> parametri = new HashMap<>();
        parametri.put("argomento",argo);
        final Gson gson = new Gson();

        JsonRequest richiesta = new JsonRequest(Request.Method.POST, url, parametri, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //mi faccio restituire tutto l'oggetto. ATTENZIONE QUANDO SI EFFETTUANO LE ELIMINAZIONI
                    JSONArray arrayTeoria = response.getJSONArray("listaTeoria");
                    for(int i = 0; i<arrayTeoria.length();i++){
                        Teoria t = gson.fromJson(String.valueOf(arrayTeoria.getJSONObject(i)),Teoria.class);
                        Log.d("LOG","list-"+righeTeoria);
                        righeTeoria.add(t.getTitolo());
                        listaTeoria.add(t);
                    }

                    JSONArray arrayEs = response.getJSONArray("listaEsercizi");
                    for(int i = 0; i<arrayEs.length();i++){
                        Esercizio ex = gson.fromJson(String.valueOf(arrayEs.getJSONObject(i)),Esercizio.class);
                        righeTeoria.add(String.valueOf(ex.getCodiceEsercizio()));
                        listaEsercizi.add(ex);
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
    }

}
