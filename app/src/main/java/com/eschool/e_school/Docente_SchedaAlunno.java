package com.eschool.e_school;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Docente_SchedaAlunno extends AppCompatActivity {

    private Alunno alunno, alunnoMod;
    private EditText nomeAlunno, cognomeAlunnno, dataNascitaAlunno, codiceFiscaleAlunno, luogoNascitaAlunno, residenzaAlunno,
            telefonoAlunno, celAlunno, emailAlunno, usernameAlunno, passwordAlunno, classeTxt;
    private CheckBox opzDsaAlunno;
    private Boolean dsa;
    private Boolean getdsa;
    private Button btConfermaModificaDati, btAnnullaDati;
    private ImageButton btModificaDati;
    private String url = "http://www.eschooldb.altervista.org/PHP/modificaDatiAlunno.php";
    private String cfVecchio;

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
                riempiScheda();
                setEditDati(false);
            }
        });

        btConfermaModificaDati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifica();
                setEditDati(false);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(opzDsaAlunno.isChecked()) {
            getMenuInflater().inflate(R.menu.menu_bt_modifica, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_opzioni_dsa) {
            DialogFragment dial = new DialogDsa();
            dial.show(getFragmentManager(),"dialogDSA");

        }else if(id == android.R.id.home){
            Intent vaiHomeClasse = NavUtils.getParentActivityIntent(this);
            vaiHomeClasse.putExtra("Materia",Docente_HomeClasse.materia);
            vaiHomeClasse.putExtra("Classe",Docente_HomeClasse.classe);
            if (NavUtils.shouldUpRecreateTask(this, vaiHomeClasse)) {
                TaskStackBuilder.create(this).addNextIntentWithParentStack(vaiHomeClasse).startActivities();
            } else {
                NavUtils.navigateUpTo(this, vaiHomeClasse);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void riempiScheda(){

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
        cfVecchio = codiceFiscaleAlunno.getText().toString();
    }

    public void modifica(){
        HashMap<String, String> parametri = new HashMap<String, String>();

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
        parametri.put("cfAlunno",cfVecchio);

        JsonRequest richiesta = new JsonRequest(Request.Method.POST, url, parametri, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    alunno = alunnoMod;
                    Toast.makeText(getApplicationContext(), response.getString("risposta"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestSingleton.getInstance(this).addToRequestQueue(richiesta);
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
