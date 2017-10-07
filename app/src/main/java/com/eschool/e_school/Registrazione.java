package com.eschool.e_school;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Registrazione extends AppCompatActivity {
    private EditText nomeDoc,cognomeDoc,dataNascitaDoc,luogoNascitaDoc,residenzaDoc,cfDoc,cellulareDoc, telefonoDoc, emailDoc;
    private EditText matricolaDoc,pswDoc,confermaPswDoc;
    private Button btConfermaRegistrazione;
    private String nome, cognome, datanascita, luogoNascita, residenza, cf, cellulare, telefono, email, matricola, psw, confermaPsw;
    private TextView txtErrore, txtCf;
    private String urlRegistrazione = "http://www.eschooldb.altervista.org/PHP/registrazione.php";
    private AlertDialog.Builder infoAlert;
    private RequestQueue requestQueue;

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

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        btConfermaRegistrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aquisiszioneDati();
                if(controllo()){
                    registrazione();
                }
            }
        });

    }

    private void aquisiszioneDati(){
        nome = nomeDoc.getText().toString();
        cognome = cognomeDoc.getText().toString();
        datanascita = dataNascitaDoc.getText().toString();
        luogoNascita = luogoNascitaDoc.getText().toString();
        cf = cfDoc.getText().toString();
        residenza = residenzaDoc.getText().toString();
        telefono = telefonoDoc.getText().toString();
        cellulare = cellulareDoc.getText().toString();
        email = emailDoc.getText().toString();
        matricola = matricolaDoc.getText().toString();
        psw = pswDoc.getText().toString();
        confermaPsw = confermaPswDoc.getText().toString();
    }

    public boolean controllo(){
        if(nome.equals("") || cognome.equals("") || datanascita.equals("")|| luogoNascita.equals("") || cf.equals("") || residenza.equals("")
                || telefono.equals("") || cellulare.equals("") || email.equals("") || matricola.equals("") || psw.equals("")){
            txtErrore.setText(R.string.erroreCampiVuoti);
            return false;
        }

        if(!psw.equals(confermaPsw) ){
            txtErrore.setText(R.string.erroreCorrispondenza);
            return false;
        }
        /*if(cf.length()!=16) {
            txtCf.setTextColor(Color.RED);
            txtCf.setText("Codice fiscale (16 caratteri)");
            return false;
        }*/
        return true;
    }

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

}