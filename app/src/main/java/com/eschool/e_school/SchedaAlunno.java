package com.eschool.e_school;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class SchedaAlunno extends AppCompatActivity {

    private Alunno alunno, alunnoMod;
    private EditText nomeAlunno, cognomeAlunnno, dataNascitaAlunno, codiceFiscaleAlunno, luogoNascitaAlunno, residenzaAlunno,
            telefonoAlunno, celAlunno, emailAlunno, usernameAlunno, passwordAlunno, classeTxt;
    private CheckBox opzDsaAlunno;
    private ArrayList datiAlunno;
    private Boolean dsa, getdsa;
    private Button btmodificaDatiAlunno, btConfermaModifica;
    private String url = "http://www.eschooldb.altervista.org/PHP/modificaDatiAlunni.php";
    private RequestQueue requestQueue;

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
        btmodificaDatiAlunno = (Button) findViewById(R.id.btmodificaDatiAlunno);
        btConfermaModifica = (Button) findViewById(R.id.btConfermaModifica);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            alunno = (Alunno) bundle.getParcelable("Alunno");
        }



        riempiScheda();

        btmodificaDatiAlunno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btConfermaModifica.setVisibility(View.VISIBLE);

                nomeAlunno.setEnabled(true);
                cognomeAlunnno.setEnabled(true);
                dataNascitaAlunno.setEnabled(true);
                codiceFiscaleAlunno.setEnabled(true);
                luogoNascitaAlunno.setEnabled(true);
                residenzaAlunno.setEnabled(true);
                telefonoAlunno.setEnabled(true);
                celAlunno.setEnabled(true);
                emailAlunno.setEnabled(true);
                opzDsaAlunno.setEnabled(true);
                usernameAlunno.setEnabled(true);
                passwordAlunno.setEnabled(true);
            }
        });

        btConfermaModifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifica();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bt_modifica, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_modifica_info) {
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

    public void modifica(){

        if(opzDsaAlunno.isChecked())
            getdsa = true;
        else
            getdsa = false;
        alunnoMod = new Alunno(nomeAlunno.getText().toString(), cognomeAlunnno.getText().toString(), dataNascitaAlunno.getText().toString(),
                codiceFiscaleAlunno.getText().toString(), luogoNascitaAlunno.getText().toString(),residenzaAlunno.getText().toString(),
                telefonoAlunno.getText().toString(), celAlunno.getText().toString(), emailAlunno.getText().toString(), getdsa,
                usernameAlunno.getText().toString(), passwordAlunno.getText().toString(),classeTxt.getText().toString());
        HashMap<String, String> parametri = new HashMap<String, String>();
        String datiAlunno = new Gson().toJson(alunnoMod);
        parametri.put("alunno", datiAlunno);
        JsonRequest richiesta = new JsonRequest(Request.Method.POST, url, parametri, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Toast.makeText(getApplicationContext(), "Modifica avvenuta con successo", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(richiesta);
    }




    @Override
    public void onBackPressed() {

    }
}
