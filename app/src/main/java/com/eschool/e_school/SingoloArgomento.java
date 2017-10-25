package com.eschool.e_school;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
    private AlertDialog.Builder infoAlert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singolo_argomento);
        listaTeoria = new ArrayList<>();
        listaEsercizi = new ArrayList<>();
        righeTeoria = new ArrayList<>();
        righeEsercizi = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        infoAlert = new AlertDialog.Builder(getApplicationContext());

        listViewEsercizi = (ListView) findViewById(R.id.listViewEsercizi);
        listViewTeoria = (ListView) findViewById(R.id.listViewTeoria);
        btAperte = (Button) findViewById(R.id.btAperte);
        btMultiple = (Button) findViewById(R.id.btMultiple);
        btCaricaFile = (Button) findViewById(R.id.btCaricaFile);
        btFile = (Button) findViewById(R.id.btFile);

        argo = getIntent().getStringExtra("argomento");
        connessione();

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
        Log.d("LOG","4");
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
                        Log.d("LOG","t-"+t.toString());
                        Log.d("LOG","t-"+t.getTitolo());
                        righeTeoria.add(t.getTitolo());
                        listaTeoria.add(t);
                        ArrayAdapter adapterTeoria = new ArrayAdapter(getApplicationContext(), R.layout.riga_lista_programma,righeTeoria);
                        listViewTeoria.setAdapter(adapterTeoria);
                    }

                    JSONArray arrayEs = response.getJSONArray("listaEsercizi");
                    for(int i = 0; i<arrayEs.length();i++){
                        Esercizio ex = gson.fromJson(String.valueOf(arrayEs.getJSONObject(i)),Esercizio.class);
                        Log.d("LOG","ex-"+ex.toString());
                        Log.d("LOG","ex2-"+String.valueOf(ex.getCodiceEsercizio()));
                        righeEsercizi.add(String.valueOf(ex.getCodiceEsercizio()));
                        listaEsercizi.add(ex);
                        ArrayAdapter adapterEsercizi = new ArrayAdapter(getApplicationContext(),R.layout.riga_lista_programma,righeEsercizi);
                        listViewEsercizi.setAdapter(adapterEsercizi);
                    }
                    Log.d("LOG","size t-"+righeTeoria+" ex-"+righeEsercizi);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    infoAlert.setTitle("Errore di connessione");
                    infoAlert.setMessage("Controllare connessione internet e riprovare.");
                    AlertDialog alert = infoAlert.create();
                    alert.show();
            }
        });

        requestQueue.add(richiesta);
    }

}
