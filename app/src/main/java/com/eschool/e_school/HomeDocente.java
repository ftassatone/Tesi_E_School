package com.eschool.e_school;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeDocente extends AppCompatActivity {
    private TextView txtParteDiagnostica;
    private String docente;
    private String url = "http://www.eschooldb.altervista.org/PHP/loginDocente.php";
    private RequestQueue requestQueue;
    private ListView lvMaterie,lvClassi;
    private AlertDialog.Builder infoAlert;
    private RadioGroup rgClassi, rgMaterie;
    private ImageButton btVaiClasse;
    private RadioButton[] rbMat,rbCl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_docente);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        infoAlert = new AlertDialog.Builder(HomeDocente.this);

        txtParteDiagnostica = (TextView) findViewById(R.id.txtParteDiagnostica);
        rgMaterie =(RadioGroup) findViewById(R.id.rgMaterie);
        rgClassi =(RadioGroup) findViewById(R.id.rgClassi);
        btVaiClasse = (ImageButton) findViewById(R.id.btViaClasse);

        txtParteDiagnostica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeDocente.this,SezioneDiagnostica.class);
                startActivity(i);
            }
        });

        Bundle dato = getIntent().getExtras();
        docente = dato.getString("username");
        Log.v("LOG","nome doc"+docente);

        connessione();

        btVaiClasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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
            Intent esci = new Intent(HomeDocente.this,Login.class);
            startActivity(esci);
        }

        return super.onOptionsItemSelected(item);
    }

    //metodo per la connessione al server
    public void connessione() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                HashMap<String, String> parametri = new HashMap<String, String>();
                parametri.put("matricola", docente);

                //richiesta di connessione al server
                JsonRequest richiesta = new JsonRequest(Request.Method.POST, url, parametri, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray materie = response.getJSONArray("materie");
                            rbMat = new RadioButton[materie.length()];

                            for (int i = 0; i < materie.length(); i++) {
                                rbMat[i] = new RadioButton(getApplicationContext());
                                rbMat[i].setText(materie.getJSONObject(i).getString("descrizioneMateria"));
                                rgMaterie.addView(rbMat[i]);
                            }

                            JSONArray classi = response.getJSONArray("classi");
                            rbCl = new RadioButton[classi.length()];
                            for (int i = 0; i < classi.length(); i++) {
                                rbCl[i] = new RadioButton(getApplicationContext());
                                rbCl[i].setText(classi.getJSONObject(i).getString("nomeClasse"));
                                rgClassi.addView(rbCl[i]);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("LOG","errore: "+error.toString());
                        infoAlert.setTitle("Errore di connessione");
                        infoAlert.setMessage("Controllare connessione internet e riprovare.");
                        AlertDialog alert = infoAlert.create();
                        alert.show();
                    }
                });
                requestQueue.add(richiesta);
                return null;
            }
        }.execute();
    }

}

