package com.eschool.e_school;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Registrazione extends AppCompatActivity {
    private EditText nomeDoc,cognomeDoc,dataNascitaDoc,luogoNascitaDoc,residenzaDoc,cfDoc,cellulareDoc, telefonoDoc, emailDoc;
    private EditText matricolaDoc,pswDoc,confermaPswDoc;
    private Button btConfermaRegistrazione, btSelezionaMateria, btSelezionaClasse;
    private String nome, cognome, datanascita, luogoNascita, residenza, cf, cellulare, telefono, email, matricola, psw, confermaPsw;
    private TextView txtErrore, txtCf;
    private String urlRegistrazione = "http://www.eschooldb.altervista.org/PHP/registrazione.php";
    private String urlSpinner = "http://www.eschooldb.altervista.org/PHP/getMaterieClassi.php";
    private AlertDialog seleziona;
    private AlertDialog.Builder infoAlert, builder;
    private RequestQueue requestQueue;
    //private Spinner spinnerMaterie, spinnerClassi;
    private CheckBox[] mat, clas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione);


        nomeDoc = (EditText) findViewById(R.id.nomeDoc);
        cognomeDoc = (EditText) findViewById(R.id.cognomeDoc);
        dataNascitaDoc = (EditText) findViewById(R.id.dataNascitaDoc);
        luogoNascitaDoc = (EditText) findViewById(R.id.luogoNascitaDoc);
        cfDoc = (EditText) findViewById(R.id.cfDoc);
        residenzaDoc = (EditText) findViewById(R.id.residenzaDoc);
        telefonoDoc = (EditText) findViewById(R.id.telefonoDoc);
        cellulareDoc = (EditText) findViewById(R.id.cellulareDoc);
        emailDoc = (EditText) findViewById(R.id.emailDoc);
        matricolaDoc = (EditText) findViewById(R.id.matricolaDoc);
        pswDoc = (EditText) findViewById(R.id.pswDoc);
        confermaPswDoc = (EditText) findViewById(R.id.confermaPswDoc);
        txtErrore = (TextView) findViewById(R.id.txtErrore);
        btConfermaRegistrazione = (Button) findViewById(R.id.btConfermaRegistrazione);
        infoAlert = new AlertDialog.Builder(Registrazione.this);
        txtCf = (TextView) findViewById(R.id.txtCf);
        //spinnerMaterie = (Spinner) findViewById(R.id.spinnerMaterie);
        //spinnerClassi = (Spinner) findViewById(R.id.spinnerClassi);
        btSelezionaMateria = (Button) findViewById(R.id.btSelezionaMateria);
        btSelezionaClasse = (Button) findViewById(R.id.btSelezionaClasse);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        //riempiSpinner();

        btConfermaRegistrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aquisiszioneDati();
                if(controllo()){
                    registrazione();
                }
            }
        });

        btSelezionaMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(seleziona !=null && seleziona.isShowing())
                    seleziona.dismiss();
                builder = new AlertDialog.Builder(Registrazione.this);
                builder.setView(R.layout.fragment_seleziona_materia);
                builder.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setTitle("Seleziona materie");
                seleziona = builder.create();
                seleziona.show();
            }
        });

    }

    private void aquisiszioneDati(){
        nome = nomeDoc.getText().toString().trim();
        cognome = cognomeDoc.getText().toString().trim();
        datanascita = dataNascitaDoc.getText().toString().trim();
        luogoNascita = luogoNascitaDoc.getText().toString().trim();
        cf = cfDoc.getText().toString().trim();
        residenza = residenzaDoc.getText().toString().trim();
        telefono = telefonoDoc.getText().toString().trim();
        cellulare = cellulareDoc.getText().toString().trim();
        email = emailDoc.getText().toString().trim();
        matricola = matricolaDoc.getText().toString().trim();
        psw = pswDoc.getText().toString().trim();
        confermaPsw = confermaPswDoc.getText().toString().trim();
    }

    public boolean controllo(){
        if(nome.equals("") || cognome.equals("") || datanascita.equals("")|| luogoNascita.equals("") || cf.equals("") || residenza.equals("")
                || telefono.equals("") || cellulare.equals("") || email.equals("") || matricola.equals("") || psw.equals("")){
            txtErrore.setTextColor(Color.RED);
            return false;
        }

        if(!psw.equals(confermaPsw) ){
            pswDoc.setTextColor(Color.RED);
            pswDoc.setError("Le password non corrispondono");
            pswDoc.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    pswDoc.setTextColor(Color.BLACK);
                    pswDoc.setError(null);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            confermaPswDoc.setTextColor(Color.RED);
            confermaPswDoc.setError("Le password non corrispondono");
            confermaPswDoc.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    confermaPswDoc.setTextColor(Color.BLACK);
                    confermaPswDoc.setError(null);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            return false;
        }

        return true;
    }



    /*public void riempiSpinner(){
        Log.v("LOG", "sono in riempiSpinner");
        JsonRequest richiesta = new JsonRequest(Request.Method.POST, urlSpinner, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("LOG", "response" +response.toString());
                try {
                    JSONArray materie = response.getJSONArray("materie");
                    mat = new CheckBox[materie.length()];
                    for(int i=0; i<materie.length(); i++){
                        mat[i] = new CheckBox(getApplicationContext());
                        mat[i].setText(materie.getJSONObject(i).getString("nomeMateria"));
                        spinnerMaterie.addView(mat[i]);
                    }
                    Log.v("LOG", "sono uscito dal for");

                    JSONArray classi = response.getJSONArray("classi");
                    clas = new CheckBox[classi.length()];
                    for(int i=0; i<classi.length(); i++){
                        clas[i] = new CheckBox(getApplicationContext());
                        clas[i].setText(classi.getJSONObject(i).getString("nomeClasse"));
                        spinnerClassi.addView(clas[i]);
                    }

                } catch (JSONException e) {
                    Log.v("LOG", "errore "+ e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("LOG", "errore"+error.toString());
            }
        });
        requestQueue.add(richiesta);
    }*/

    public void registrazione(){
        //raccolgo i dati inseriti dall'utente
        HashMap<String,String> parametri = new HashMap<String, String>();

        parametri.put("matricola",matricola);
        parametri.put("nome",nome);
        parametri.put("cognome",cognome);
        parametri.put("cf",cf);
        parametri.put("dataNascita",datanascita);
        parametri.put("luogoNascita",luogoNascita);
        parametri.put("residenza",residenza);
        parametri.put("numeroTelefono",telefono);
        parametri.put("cellulare",cellulare);
        parametri.put("email",email);
        parametri.put("password",psw);

        Log.v("LOG","parametri "+ parametri);

        //richiesta di connessione al server
        JsonRequest richiesta = new JsonRequest(Request.Method.POST, urlRegistrazione, parametri, new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject response) {
            String c ="";
            Log.v("LOG","ris "+ response.toString());
            try {
                c  = response.getString("risposta");
            } catch (JSONException e) {
                 e.printStackTrace();
             }
             if(c!=""){
                 Log.v("LOG","sono qui");
                 Intent vaiLogin = new Intent(Registrazione.this,Login.class);
                 startActivity(vaiLogin);
                 Toast toast = Toast.makeText(getApplicationContext(),c,Toast.LENGTH_LONG);
                 toast.show();

             }else if(c=="") {
                 infoAlert.setTitle("Errore");
                 infoAlert.setMessage("Errore");
                 AlertDialog alert = infoAlert.create();
                 alert.show();
             }
        }
        }, new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.v("LOG","errore "+ error.toString());

            infoAlert.setTitle("Errore di connessione");
            infoAlert.setMessage("Controllare connessione internet e riprovare.");
            AlertDialog alert = infoAlert.create();
            alert.show();
        }
        });
                requestQueue.add(richiesta);
    }

    @Override
    public void onBackPressed() {

    }

}