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
    private Button btmodificaDatiAlunno;

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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            alunno = (Alunno) bundle.getParcelable("Alunno");
        }

        riempiScheda();

        btmodificaDatiAlunno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            nomeAlunno.setText(alunno.getNome());
            cognomeAlunnno.setText(alunno.getCognome());
            dataNascitaAlunno.setText(alunno.getDataNascita());
            codiceFiscaleAlunno.setText(alunno.getCf());
            luogoNascitaAlunno.setText(alunno.getLuogoNascita());
            residenzaAlunno.setText(alunno.getResidenza());
            telefonoAlunno.setText(alunno.getNumeroTelefono());
            celAlunno.setText(alunno.getCellulare());
            emailAlunno.setText(alunno.getEmail());
            usernameAlunno.setText(alunno.getUsername());
            passwordAlunno.setText(alunno.getPassword());
            classeTxt.setText(alunno.getNomeClasse());
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
        connessione();
    }

    public void connessione(){

    }




    @Override
    public void onBackPressed() {

    }
}
