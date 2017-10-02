package com.eschool.e_school;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.HashMap;

/**
 * Created by LenovoZ70 on 17/09/2017.
 */
public class AggiungiClasse extends AppCompatActivity {

    EditText classe, sezione, nomeAlunno, cognomeAlunno, dataNascitaAlunno, cfAlunno, luogoNascitaAlunno, residenzaAlunno, telefonoAlunno,
            cellulareAlunno, emailAlunno, txtError;
    Button btConfermaAlunno, btFine;
    CheckBox opzDsaAlunno;
    String classeTxt, sezioneTxt, nomeAlunnoTxt, cognomeAlunnoTxt, dataNascitaAlunnoTxt, cfAlunnoTxt, luogoNascitaAlunnoTxt, residenzaAlunnoTxt,
            telefonoAlunnoTxt, cellulareAlunnoTxt, emailAlunnoTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aggiungi_classe);

        classe = (EditText) findViewById(R.id.classe);
        sezione = (EditText) findViewById(R.id.sezione);
        nomeAlunno = (EditText) findViewById(R.id.nomeAlunno);
        cognomeAlunno = (EditText) findViewById(R.id.cognomeAlunno);
        dataNascitaAlunno = (EditText) findViewById(R.id.dataNascitaAlunno);
        cfAlunno = (EditText) findViewById(R.id.cfAlunno);
        luogoNascitaAlunno = (EditText) findViewById(R.id.luogoNascitaAlunno);
        residenzaAlunno = (EditText) findViewById(R.id.residenzaAlunno);
        telefonoAlunno = (EditText) findViewById(R.id.telefonoAlunno);
        cellulareAlunno = (EditText) findViewById(R.id.cellulareAlunno);
        emailAlunno = (EditText) findViewById(R.id.emailAlunno);
        btConfermaAlunno = (Button) findViewById(R.id.btConfermaAlunno);
        btFine = (Button) findViewById(R.id.btFine);
        opzDsaAlunno = (CheckBox) findViewById(R.id.opzDsaAlunno);
        txtError = (EditText) findViewById(R.id.txtError);

        btConfermaAlunno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acquisizioneDati();
                if(controllo()){

                }

            }
        });

    }

    private void acquisizioneDati(){
        classeTxt = classe.getText().toString();
        sezioneTxt = sezione.getText().toString();
        nomeAlunnoTxt = nomeAlunno.getText().toString();
        cognomeAlunnoTxt = cognomeAlunno.getText().toString();
        dataNascitaAlunnoTxt = dataNascitaAlunno.getText().toString();
        cfAlunnoTxt = cfAlunno.getText().toString();
        luogoNascitaAlunnoTxt = luogoNascitaAlunno.getText().toString();
        residenzaAlunnoTxt = residenzaAlunno.getText().toString();
        telefonoAlunnoTxt = telefonoAlunno.getText().toString();
        cellulareAlunnoTxt = cellulareAlunno.getText().toString();
        emailAlunnoTxt = emailAlunno.getText().toString();

    }

    private boolean controllo(){
        if(classeTxt.equals("") || sezioneTxt.equals("") || nomeAlunnoTxt.equals("") || cognomeAlunnoTxt.equals("") ||
                dataNascitaAlunnoTxt.equals("") || luogoNascitaAlunnoTxt.equals("") || luogoNascitaAlunnoTxt.equals("") ||
                residenzaAlunnoTxt.equals("") || telefonoAlunnoTxt.equals("") || cellulareAlunnoTxt.equals("") || emailAlunnoTxt.equals("")){
            txtError.setText(R.string.erroreCampiVuoti);
            return false;
        }
        return true;


    }

    public void aggiungiNuovoAlunno(){
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
            @Override
            protected Void doInBackground(Void... voids) {
                //raccolgo i dati inseriti dall'utente
                HashMap<String, String> parametri = new HashMap<String, String>();

                parametri.put("cf", cfAlunnoTxt);
                parametri.put("nome", nomeAlunnoTxt);
                parametri.put("cognome", cognomeAlunnoTxt);
                parametri.put("dataNascita", dataNascitaAlunnoTxt);
                parametri.put("luogoNascita", luogoNascitaAlunnoTxt);
                parametri.put("residenza", residenzaAlunnoTxt);
                parametri.put("numeroTelefono", telefonoAlunnoTxt);
                parametri.put("cellulare", cellulareAlunnoTxt);
                parametri.put("email", emailAlunnoTxt);
                //dsa
                parametri.put("nomeClasse", classeTxt+sezioneTxt);
                return null;
            }

        }.execute();
    }


}
