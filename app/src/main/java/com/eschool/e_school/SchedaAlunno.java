package com.eschool.e_school;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

public class SchedaAlunno extends AppCompatActivity {

    private Alunno alunno;
        private EditText nomeAlunno, cognomeAlunnno, dataNascitaAlunno, codiceFiscaleAlunno, luogoNascitaAlunno, residenzaAlunno, telefonoAlunno, celAlunno, emailAlunno;
        private CheckBox opzDsaAlunno;
        private ArrayList datiAlunno;
        private Boolean dsa;
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
        opzDsaAlunno = (CheckBox) findViewById(R.id.opzDsaAlunno);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            alunno = (Alunno) bundle.getParcelable("Alunno");
        }

        riempiScheda();
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
        for(int i=0; i<datiAlunno.size(); i++){
            nomeAlunno.setText(alunno.getNome());
            cognomeAlunnno.setText(alunno.getCognome());
            dataNascitaAlunno.setText(alunno.getDataNascita());
            codiceFiscaleAlunno.setText(alunno.getCf());
            luogoNascitaAlunno.setText(alunno.getLuogoNascita());
            residenzaAlunno.setText(alunno.getResidenza());
            telefonoAlunno.setText(alunno.getNumeroTelefono());
            celAlunno.setText(alunno.getCellulare());
            emailAlunno.setText(alunno.getEmail());
            dsa = alunno.getDsa();
            Log.v("LOG", "dsa "+dsa);
            if(dsa.equals(1)) {
                opzDsaAlunno.setChecked(true);
            }else {
                opzDsaAlunno.setChecked(false);
            }
            if(opzDsaAlunno.isChecked())
                Log.v("LOG", "sono dislessico");
        }
    }

    @Override
    public void onBackPressed() {

    }
}
