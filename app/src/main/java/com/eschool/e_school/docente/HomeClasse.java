package com.eschool.e_school.docente;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.eschool.e_school.PreLogin;
import com.eschool.e_school.R;
import com.eschool.e_school.adapter.CustomAdapterElenco;
import com.eschool.e_school.connessione.JsonRequest;
import com.eschool.e_school.connessione.RequestSingleton;
import com.eschool.e_school.elementiBase.Alunno;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class HomeClasse extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listViewElencoAlunni, listViewProgramma;
    private String nomeArgomento;
    static String materia, classe;
    private String url = "http://www.eschooldb.altervista.org/PHP/homeClasse.php";
    private ArrayList elencoAlunni, programma, datiAlunni;
    //private ArrayAdapter<String> adapterAlunni;
    private ArrayAdapter<String> adapterProgramma;
    private Alunno alunno;
    private AlertDialog.Builder infoAlert;
    private CustomAdapterElenco adapterAlunni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_classe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listViewElencoAlunni = (ListView) findViewById(R.id.listViewElencoAlunni);
        listViewProgramma = (ListView) findViewById(R.id.listViewProgramma);

        materia = getIntent().getStringExtra("Materia");
        classe = getIntent().getStringExtra("Classe");

        infoAlert = new AlertDialog.Builder(getApplicationContext());

        connessione();

        /*listViewElencoAlunni.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterAlunni, View view, int i, long l) {
                Log.d("DATI", "mi hai cliccato");
                Toast.makeText(Docente_HomeClasse.this, "clicchi"+ view.getTag(), Toast.LENGTH_SHORT).show();
                Intent vaiSchedaAlunno = new Intent(getApplicationContext(), Docente_SchedaAlunno.class);
                Bundle bundle = new Bundle();
                int x = adapterAlunni.getPositionForView(view);
                Alunno al = (Alunno) elencoAlunni.get(x);
                bundle.putParcelable("Alunno", al);
                vaiSchedaAlunno.putExtras(bundle);
                startActivity(vaiSchedaAlunno);
            }
        });*/

        listViewProgramma.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent vaiArgo = new Intent(getApplicationContext(),SingoloArgomento.class);
                vaiArgo.putExtra("argomento", nomeArgomento);
                startActivity(vaiArgo);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    /**
     * Menù di navigazione laterale
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.eserciziSvolti) {
            //TODO da fare
        } else if (id == R.id.homePrincipale) {
            Intent home = new Intent(getApplicationContext(),HomeDocente.class);
            Log.d("LOG", HomeDocente.DOC);
            home.putExtra("username", HomeDocente.DOC);
            startActivity(home);

        } else if(id == R.id.sezOpzioniDsa){
            //TODO passare alla sezione opzioni dsa
            //startActivity(new Intent(getApplicationContext(),SezioneOpzDsa.class));
            startActivity(new Intent(getApplicationContext(),SingoloArgomento.class));
        }else if (id == R.id.impostazioni) {
            //TODO passare ad impostazioni
        } else if (id == R.id.logout) {
            startActivity(new Intent(getApplicationContext(),PreLogin.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void connessione(){
        HashMap<String, String> parametri = new HashMap<String, String>();
        parametri.put("materia", materia);
        parametri.put("nomeClasse",classe);
        //richiesta di connessione al server
        JsonRequest richiesta = new JsonRequest(Request.Method.POST, url, parametri, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                elencoAlunni = new ArrayList();
                datiAlunni = new ArrayList();
                programma = new ArrayList();
                Boolean dsa;
                try {
                    JSONArray elenco = response.getJSONArray("elenco");
                    for(int i =0; i < elenco.length(); i++){
                        if(elenco.getJSONObject(i).getString("dsa").equals("1")){
                            dsa = true;
                        }else{
                            dsa = false;
                        }

                        //String psw = elenco.getJSONObject(i).getString("password");
                        //String pswDec = MyCript.decrypt(psw);
                        alunno = new Alunno(elenco.getJSONObject(i).getString("cf"),  elenco.getJSONObject(i).getString("nome"), elenco.getJSONObject(i).getString("cognome"),
                                elenco.getJSONObject(i).getString("dataNascita"), elenco.getJSONObject(i).getString("luogoNascita"), elenco.getJSONObject(i).getString("residenza"),
                                elenco.getJSONObject(i).getString("numeroTelefono"), elenco.getJSONObject(i).getString("cellulare"), elenco.getJSONObject(i).getString("email"),
                                dsa, elenco.getJSONObject(i).getString("username"), elenco.getJSONObject(i).getString("password"),
                                elenco.getJSONObject(i).getString("nomeClasse"));
                        elencoAlunni.add(alunno);
                        //datiAlunni.add(alunno.getNome() + " " + alunno.getCognome());
                    }
                    Collections.sort(elencoAlunni, new Ordina());
                    adapterAlunni = new CustomAdapterElenco(getApplicationContext(),elencoAlunni);
                    listViewElencoAlunni.setAdapter(adapterAlunni);
                    //adapterAlunni = new ArrayAdapter<String>(getApplicationContext(), R.layout.layout_riga_elenco, datiAlunni);
                    //listViewElencoAlunni.setAdapter(adapterAlunni);

                    JSONArray elencoProgramma = response.getJSONArray("programma");
                    for(int j =0; j < elencoProgramma.length(); j++){
                        nomeArgomento =elencoProgramma.getJSONObject(j).getString("argomento");
                        programma.add(nomeArgomento);
                    }
                    adapterProgramma = new ArrayAdapter<String>(getApplicationContext(), R.layout.riga_lista_programma, programma);
                    listViewProgramma.setAdapter(adapterProgramma);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.v("LOG", "e "+e.toString());
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
        RequestSingleton.getInstance(this).addToRequestQueue(richiesta);
    }

    /**
     * Ordina la lista di alunni per cognome
     */
    public class Ordina implements Comparator<Alunno> {
        @Override
        public int compare(Alunno al1, Alunno al2) {
            return al1.getCognome().compareTo(al2.getCognome());
        }
    }


}
