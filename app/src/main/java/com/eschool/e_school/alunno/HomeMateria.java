package com.eschool.e_school.alunno;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.eschool.e_school.R;
import com.eschool.e_school.adapter.CustomAdapterRigaTeoria_A;
import com.eschool.e_school.connessione.JsonRequest;
import com.eschool.e_school.connessione.RequestSingleton;
import com.eschool.e_school.elementiBase.Esercizio;
import com.eschool.e_school.elementiBase.Teoria;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeMateria extends AppCompatActivity {
    private Button btTeoriaMateria,btEserciziMateria;
    //private String url = "http://www.eschooldb.altervista.org/PHP/homeMateria.php";
    private String url = "http://www.eschooldb.altervista.org/PHP/acquisizioneTeoriaEsercizi.php";
    public static String materia;
    public static String livello;
    private ListView listContenitore, listaTeoria,listaEsercizi;
    private TextView titolo;
    private ArrayAdapter adapterEs;
    private CustomAdapterRigaTeoria_A adapterTeo;
    private ArrayList<String> lista;
    private ArrayList<Teoria> listObTeoria;
    private ArrayList<Esercizio> listObEsercizio;

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
        Log.d("DATI", "ricevo " + materia + "-" + livello);

        //acquisisco i dati delle due listView
        acquisisci();
    }

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

                            Teoria t = new Teoria(arrayTeoria.getJSONObject(i).getInt("codiceTeoria"),arrayTeoria.getJSONObject(i).getString("argomento"),
                                    arrayTeoria.getJSONObject(i).getString("titolo"),arrayTeoria.getJSONObject(i).getString("livello"),
                                    arrayTeoria.getJSONObject(i).getString("dataCreazione"),arrayTeoria.getJSONObject(i).getString("codiceMateria"),
                                    arrayTeoria.getJSONObject(i).getString("fileTeoria"),arrayTeoria.getJSONObject(i).getString("nomeMateria"));
                            listObTeoria.add(t);
                            listTeo.add(t.getTitolo());
                        }
                        //adapterTeo = new ArrayAdapter(getApplicationContext(), R.layout.riga_lista_programma, listTeo);
                        Log.d("DATI","titoli: "+listObTeoria);
                        adapterTeo = new CustomAdapterRigaTeoria_A(getApplicationContext(),listObTeoria);
                        listaTeoria.setAdapter(adapterTeo);
                    }
                    if(arrayEsercizi.length() != 0){
                        for(int i = 0; i<arrayEsercizi.length();i++){
                            listEs.add(arrayEsercizi.getJSONObject(i).getString("codice")+" - "+arrayEsercizi.getJSONObject(i).getString("argomento"));
                            /*Boolean sintetizzatore = false, microfono = false, riscontroLettura = false;
                            if(arrayEsercizi.getJSONObject(i).getString("sintetizzatore").toString() == "1"){
                                sintetizzatore = true;
                            }
                            if(arrayEsercizi.getJSONObject(i).getString("microfono").toString() == "1"){
                                microfono = true;
                            }
                            if(arrayEsercizi.getJSONObject(i).getString("riscontroLettura").toString() == "1"){
                                riscontroLettura= true;
                            }
                            Esercizio es = new Esercizio(arrayEsercizi.getJSONObject(i).getInt("codice"),arrayEsercizi.getJSONObject(i).getInt("voto"),
                                    arrayEsercizi.getJSONObject(i).getInt("numeroErrori"),arrayEsercizi.getJSONObject(i).getInt("codiceTeoria"),
                                    arrayEsercizi.getJSONObject(i).getString("descrizione"),arrayEsercizi.getJSONObject(i).getString("livello"),
                                    arrayEsercizi.getJSONObject(i).getString("opzioneRisposta"), arrayEsercizi.getJSONObject(i).getString("dataCreazione"),
                                    sintetizzatore, riscontroLettura,microfono);
                            listObEsercizio.add(es);*/
                            }


                        adapterEs = new ArrayAdapter(getApplicationContext(), R.layout.riga_lista_programma, listEs);
                        listaEsercizi.setAdapter(adapterEs);
                    }
                } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Log.d("DATI","fuori");
        RequestSingleton.getInstance(this).addToRequestQueue(richiesta);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent back = NavUtils.getParentActivityIntent(this);
            back.putExtra("alunno", HomeAlunno.al);
            if (NavUtils.shouldUpRecreateTask(this, back)) {
                TaskStackBuilder.create(this).addNextIntentWithParentStack(back).startActivities();
            } else {
                NavUtils.navigateUpTo(this, back);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
