package com.eschool.e_school.alunno;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.eschool.e_school.R;
import com.eschool.e_school.connessione.JsonRequest;
import com.eschool.e_school.connessione.RequestSingleton;
import com.eschool.e_school.elementiBase.Teoria;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Alunno_HomeMateria extends AppCompatActivity {
    private Button btTeoriaMateria,btEserciziMateria;
    //private String url = "http://www.eschooldb.altervista.org/PHP/homeMateria.php";
    private String url = "http://www.eschooldb.altervista.org/PHP/acquisizioneTeoriaEsercizi.php";
    private String materia,tipologia;
    private String livello;
    private ListView listContenitore, listaTeoria,listaEsercizi;
    private TextView titolo;
    private ArrayAdapter adapterEs, adapterTeo;
    private ArrayList<String> lista;
    private ArrayList<Teoria> listObTeoria;

    //TODO provare con questo nuovo layout, quindi riempire le due listview all'avvio dell'activity e vedere come associare al file
    //di toeria la path che poi verrà inviata all'activity successiva per avviare il downoad

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.home_materia);
        setContentView(R.layout.home_materia_new);
        lista = new ArrayList<>();
        listObTeoria = new ArrayList<>();

        btTeoriaMateria = (Button) findViewById(R.id.btTeoriaMateria);
        btEserciziMateria = (Button) findViewById(R.id.btEserciziMateria);
        listContenitore = (ListView) findViewById(R.id.listContenitore);

        listaTeoria = (ListView) findViewById(R.id.listaTeoria);
        listaEsercizi = (ListView) findViewById(R.id.listaEsercizi);

        titolo = (TextView) findViewById(R.id.titolo);

        materia = getIntent().getStringExtra("materia");
        livello = getIntent().getStringExtra("livello");
        Log.d("DATI","ricevo "+materia+"-"+livello);

        //acquisisco i dati delle due listView
        acquisisci();

        listaTeoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent visualizza = new Intent(getApplicationContext(),Alunno_VisualizzatoreFile.class);
                //visualizza.putExtra("file", file);
                startActivity(visualizza);
            }
        });


        /*btTeoriaMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipologia = btTeoriaMateria.getTag().toString();
                //rimpiLista(tipologia);
                new AcquisizioneDati().execute();
            }
        });*/

/*        btEserciziMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipologia =btEserciziMateria.getTag().toString();
                //rimpiLista(tipologia);
                new AcquisizioneDati().execute();
            }
        });

        listContenitore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("DATI","ho cliccato");
                Intent visualizza = new Intent(getApplicationContext(),Alunno_VisualizzatoreFile.class);
                //visualizza.putExtra("file", file);
                startActivity(visualizza);
            }
        });*/

        listaTeoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent visualizza = new Intent(getApplicationContext(),Alunno_VisualizzatoreFile.class);
                visualizza.putExtra("file",listObTeoria.get(i).getFile());
                startActivity(visualizza);
            }
        });
    }

    /*private class AcquisizioneDati extends AsyncTask<Void,Void,Void> {
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
                    //listaTeoria.setAdapter(adapter);
                    listContenitore.setAdapter(adapter);
                } else if (tipologia.equalsIgnoreCase("esercizio")) {
                    for (int i = 0; i < array.length(); i++) {
                        lista.add(array.getJSONObject(i).getString("codice") + " - " + array.getJSONObject(i).getString("argomento"));
                    }
                    adapter = new ArrayAdapter(getApplicationContext(), R.layout.riga_lista_programma, lista);
                    //listaEsercizi.setAdapter(adapter);
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
    }*/

    /*private void rimpiLista(String tipo){
        Log.d("DATI","sono in riempi");
        new AcquisizioneDati().execute();
        //lista = connessione(tipo);
        Log.d("DATI","lista-"+lista);
        //ho usato la riga (layout) per il programma
        if(!lista.isEmpty()) {
            adapter = new ArrayAdapter(getApplicationContext(), R.layout.riga_lista_programma, lista);
            listContenitore.setAdapter(adapter);
        }
    }*/

    private void acquisisci(){
        Log.d("DATI","sono in connessione");
        final ArrayList listEs = new ArrayList();
        final ArrayList listTeo = new ArrayList();
        HashMap<String,String> parametri = new HashMap();
        parametri.put("materia",materia);
        parametri.put("livello",livello);
        Log.d("DATI","materia-"+materia+"--livello-"+livello);

        JsonRequest richiesta = new JsonRequest(Request.Method.POST, url, parametri, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("DATI","sono qui");
                    JSONArray arrayEsercizi = response.getJSONArray("listaEsercizi");
                    JSONArray arrayTeoria = response.getJSONArray("listaTeoria");
                    Log.d("DATI","arrayE- "+arrayEsercizi);
                    Log.d("DATI","arrayT- "+arrayTeoria);
                    if(arrayTeoria.length() != 0){
                        for(int i = 0; i<arrayTeoria.length();i++){
                            JSONObject ob = (JSONObject) arrayTeoria.get(i);
                            Boolean sintetizzatore = false, microfono = false, riscontroLettura = false;
                            if(arrayTeoria.getJSONObject(i).getString("sintetizzatore").toString() == "1"){
                                sintetizzatore = true;
                            }
                            if(arrayTeoria.getJSONObject(i).getString("microfono").toString() == "1"){
                                microfono = true;
                            }
                            if(arrayTeoria.getJSONObject(i).getString("riscontroLettura").toString() == "1"){
                                riscontroLettura= true;
                            }
                            Teoria t = new Teoria(arrayTeoria.getJSONObject(i).getInt("codiceTeoria"),arrayTeoria.getJSONObject(i).getString("argomento"),
                                    arrayTeoria.getJSONObject(i).getString("titolo"),arrayTeoria.getJSONObject(i).getString("testo"),
                                    sintetizzatore,microfono,riscontroLettura,arrayTeoria.getJSONObject(i).getString("livello"),
                                    arrayTeoria.getJSONObject(i).getString("dataCreazione"),arrayTeoria.getJSONObject(i).getString("codiceMateria"),
                                    arrayTeoria.getJSONObject(i).getString("fileTeoria"),arrayTeoria.getJSONObject(i).getString("nomeMateria"));
                            listObTeoria.add(t);
                            Log.d("DATI","---- "+t);
                            /*Gson el = new Gson();
                            //Teoria t = el.fromJson((JsonElement) arrayTeoria.get(i),Teoria.class);
                            JSONObject ob = (JSONObject) arrayTeoria.get(i);
                            Teoria t = el.fromJson(String.valueOf(ob),Teoria.class);
                            Log.d("DATI", "file- "+ob);
                            Log.d("DATI","---- "+t);
                            //Log.d("DATI",i+"--"+arrayTeoria.getJSONObject(i).getString("titolo"));*/
                            listTeo.add(t.getTitolo());
                        }
                        adapterTeo = new ArrayAdapter(getApplicationContext(), R.layout.riga_lista_programma, listTeo);
                        listaTeoria.setAdapter(adapterTeo);
                    }
                    if(arrayEsercizi.length() != 0){
                        for(int i = 0; i<arrayEsercizi.length();i++){
                            listEs.add(arrayEsercizi.getJSONObject(i).getString("codice")+" - "+arrayEsercizi.getJSONObject(i).getString("argomento"));
                        }
                        adapterEs = new ArrayAdapter(getApplicationContext(), R.layout.riga_lista_programma, listEs);
                        listaEsercizi.setAdapter(adapterEs);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Log.d("DATI",e.toString());
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
    }
}
