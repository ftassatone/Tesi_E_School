package com.eschool.e_school;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SchedaAlunno extends AppCompatActivity {

    private Alunno alunno, alunnoMod;
    private EditText nomeAlunno, cognomeAlunnno, dataNascitaAlunno, codiceFiscaleAlunno, luogoNascitaAlunno, residenzaAlunno,
            telefonoAlunno, celAlunno, emailAlunno, usernameAlunno, passwordAlunno, classeTxt;
    private CheckBox opzDsaAlunno;
    private ArrayList datiAlunno;
    private Boolean dsa;
    private Boolean getdsa;
    private Button btConfermaModificaDati, btAnnullaDati;
    private ImageButton btModificaDati;
    private String url = "http://www.eschooldb.altervista.org/PHP/modificaDatiAlunno.php";
    private RequestQueue requestQueue;
    private AlertDialog.Builder infoAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheda_alunno);

        nomeAlunno = (EditText) findViewById(R.id.nomeAlunno);
        cognomeAlunnno = (EditText) findViewById(R.id.cognomeAlunno);
        dataNascitaAlunno = (EditText) findViewById(R.id.dataNascitaAlunno);
        codiceFiscaleAlunno = (EditText) findViewById(R.id.codiceFiscaleAlunno);
        luogoNascitaAlunno = (EditText) findViewById(R.id.luogoNascitaAlunno);
        residenzaAlunno = (EditText) findViewById(R.id.residenzaAlunno);
        telefonoAlunno = (EditText) findViewById(R.id.telefonoAlunno);
        celAlunno = (EditText) findViewById(R.id.celAlunno);
        emailAlunno = (EditText) findViewById(R.id.emailAlunno);
        usernameAlunno = (EditText) findViewById(R.id.usernameAlunno);
        passwordAlunno = (EditText) findViewById(R.id.passwordAlunno);
        classeTxt = (EditText) findViewById(R.id.classeTxt);
        opzDsaAlunno = (CheckBox) findViewById(R.id.opzDsaAlunno);
        btModificaDati = (ImageButton) findViewById(R.id.btModificaDati);
        btConfermaModificaDati = (Button) findViewById(R.id.btConfermaModificaDati);
        btAnnullaDati = (Button) findViewById(R.id.btAnnullaDati);
        infoAlert = new AlertDialog.Builder(getApplicationContext());


        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            alunno = (Alunno) bundle.getParcelable("Alunno");
        }

        riempiScheda();

        btModificaDati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEditDati(true);
            }
        });

        btAnnullaDati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEditDati(false);
            }
        });

        btConfermaModificaDati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifica("dati");
                setEditDati(false);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(opzDsaAlunno.isChecked()) {
            getMenuInflater().inflate(R.menu.menu_bt_modifica, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_opzioni_dsa) {
            // si renderanno editabili le editText
        }

        return super.onOptionsItemSelected(item);
    }

    public void riempiScheda(){
        datiAlunno = new ArrayList();
        datiAlunno.add(alunno);
        for(int i=0; i<datiAlunno.size(); i++) {
            nomeAlunno.setText(alunno.getNome().trim());
            cognomeAlunnno.setText(alunno.getCognome().trim());
            dataNascitaAlunno.setText(alunno.getDataNascita().trim());
            codiceFiscaleAlunno.setText(alunno.getCf().trim());
            luogoNascitaAlunno.setText(alunno.getLuogoNascita().trim());
            residenzaAlunno.setText(alunno.getResidenza().trim());
            telefonoAlunno.setText(alunno.getNumeroTelefono().trim());
            celAlunno.setText(alunno.getCellulare().trim());
            emailAlunno.setText(alunno.getEmail().trim());
            usernameAlunno.setText(alunno.getUsername().trim());
            passwordAlunno.setText(alunno.getPassword().trim());
            classeTxt.setText(alunno.getNomeClasse().trim());
            dsa = alunno.getDsa();
            if (dsa.equals(true)) {
                opzDsaAlunno.setChecked(true);
            } else {
                opzDsaAlunno.setChecked(false);
            }
        }
    }

    public void modifica(String op){
        //TODO potrebbe essere utileinviare solo i dati che si modificano (dati o credenziali)
        HashMap<String, String> parametri = new HashMap<String, String>();

        /*parametri.put("cf",codiceFiscaleAlunno.getText().toString());


        parametri.put("nome",nomeAlunno.getText().toString());
        parametri.put("cognome",cognomeAlunnno.getText().toString());
        parametri.put("luogoNascita",luogoNascitaAlunno.getText().toString());
        parametri.put("dataNascita",dataNascitaAlunno.getText().toString());
        parametri.put("residenza",residenzaAlunno.getText().toString());
        parametri.put("numeroTelefono",telefonoAlunno.getText().toString());
        parametri.put("cellulare", celAlunno.getText().toString());
        parametri.put("email",emailAlunno.getText().toString());
        if(opzDsaAlunno.isChecked())
            getdsa = "1";
        else
            getdsa = "0";
        parametri.put("dsa", getdsa);
        }*/

       if(opzDsaAlunno.isChecked())
            getdsa = true;
        else
            getdsa = false;

        alunnoMod = new Alunno(codiceFiscaleAlunno.getText().toString(),nomeAlunno.getText().toString(), cognomeAlunnno.getText().toString(), dataNascitaAlunno.getText().toString(),
                 luogoNascitaAlunno.getText().toString(),residenzaAlunno.getText().toString(),
                telefonoAlunno.getText().toString(), celAlunno.getText().toString(), emailAlunno.getText().toString(), getdsa,
                usernameAlunno.getText().toString(), passwordAlunno.getText().toString(),classeTxt.getText().toString());

        String datiAlunno = new Gson().toJson(alunnoMod);
        parametri.put("alunno", datiAlunno);

        JsonRequest richiesta = new JsonRequest(Request.Method.POST, url, parametri, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("LOG","ris "+response.getString("risposta"));
                    Toast.makeText(getApplicationContext(), response.getString("risposta"), Toast.LENGTH_LONG).show();
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

    //metodo che mi permette di abilitare o diabilitare le editText
    private void setEditDati(boolean b){
        nomeAlunno.setEnabled(b);
        cognomeAlunnno.setEnabled(b);
        dataNascitaAlunno.setEnabled(b);
        codiceFiscaleAlunno.setEnabled(b);
        luogoNascitaAlunno.setEnabled(b);
        residenzaAlunno.setEnabled(b);
        telefonoAlunno.setEnabled(b);
        celAlunno.setEnabled(b);
        emailAlunno.setEnabled(b);
        opzDsaAlunno.setEnabled(b);
        if(b){
            btConfermaModificaDati.setVisibility(View.VISIBLE);
            btAnnullaDati.setVisibility(View.VISIBLE);

        }else{
            btConfermaModificaDati.setVisibility(View.INVISIBLE);
            btAnnullaDati.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void onBackPressed() {

    }
}
