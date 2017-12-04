package com.eschool.e_school;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Docente_SchedaAlunno extends AppCompatActivity {

    private Alunno alunno, alunnoMod;
    private EditText nomeAlunno, cognomeAlunnno, codiceFiscaleAlunno, luogoNascitaAlunno, residenzaAlunno,
            telefonoAlunno, celAlunno, emailAlunno, usernameAlunno, passwordAlunno, classeTxt, editAnno, editMese, editGiorno;
    private CheckBox opzDsaAlunno;
    private Boolean dsa;
    private Boolean getdsa;
    private Button btConfermaModificaDati, btAnnullaDati;
    private ImageButton btModificaDati;
    private String url = "http://www.eschooldb.altervista.org/PHP/modificaDatiAlunno.php";
    private String cfVecchio, dataNascita;
    private int anno, mese, giorno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheda_alunno);

        nomeAlunno = (EditText) findViewById(R.id.nomeAlunno);
        cognomeAlunnno = (EditText) findViewById(R.id.cognomeAlunno);
        codiceFiscaleAlunno = (EditText) findViewById(R.id.codiceFiscaleAlunno);
        editAnno = (EditText) findViewById(R.id.editAnno);
        editMese = (EditText) findViewById(R.id.editMese);
        editGiorno = (EditText) findViewById(R.id.editGiorno);
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
            Log.d("LOG","al "+alunno);
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
                acquisizioneData();
                if(controlloData(anno,mese,giorno)) {
                    modifica();
                    setEditDati(false);
                }
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
        editAnno.setText(alunno.getDataNascita().substring(0,4));
        editMese.setText(alunno.getDataNascita().substring(5,7));
        editGiorno.setText(alunno.getDataNascita().substring(8,10));
        codiceFiscaleAlunno.setText(alunno.getCf().trim());
        luogoNascitaAlunno.setText(alunno.getLuogoNascita().trim());
        residenzaAlunno.setText(alunno.getResidenza().trim());
        telefonoAlunno.setText(alunno.getNumeroTelefono().trim());
        celAlunno.setText(alunno.getCellulare().trim());
        emailAlunno.setText(alunno.getEmail().trim());
        usernameAlunno.setText(alunno.getUsername().trim());
        String pswCifrata = alunno.getPassword().trim();
        Log.d("LOG", "pswCifrata "+pswCifrata);
        String pswDecifrata = MyCript.decrypt(pswCifrata);
        Log.d("LOG","pswDecifrata "+pswDecifrata);
        passwordAlunno.setText(pswDecifrata);
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

        dataNascita = anno + "-" + mese + "-" + giorno;
        alunnoMod = new Alunno(codiceFiscaleAlunno.getText().toString(), nomeAlunno.getText().toString(), cognomeAlunnno.getText().toString(), dataNascita,
                luogoNascitaAlunno.getText().toString(), residenzaAlunno.getText().toString(),
                telefonoAlunno.getText().toString(), celAlunno.getText().toString(), emailAlunno.getText().toString(), getdsa,
                usernameAlunno.getText().toString(), passwordAlunno.getText().toString(), classeTxt.getText().toString());

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
        editAnno.setEnabled(b);
        editMese.setEnabled(b);
        editGiorno.setEnabled(b);
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

    public boolean controlloData(int anno, int mese, int giorno) {
        GregorianCalendar cal = new GregorianCalendar(anno, mese - 1, giorno);
        Log.d("LOG", "data "+anno+mese+giorno);
        cal.set(Calendar.YEAR, anno);
        cal.set(Calendar.MONTH, mese - 1);
        cal.set(Calendar.DAY_OF_MONTH, giorno);
        if (!cal.isLeapYear(anno) && mese == 2 && giorno > 28) {
            editGiorno.setError("Giorno non valido. Anno non bisestile");
            editGiorno.setTextColor(Color.RED);
            editGiorno.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    editGiorno.setError(null);
                    editGiorno.setTextColor(Color.BLACK);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            return false;
        }
        if (anno > 2000) {
            return true;
        } else {
            editGiorno.setError("Data non valida.");
            editGiorno.setTextColor(Color.RED);
            editMese.setTextColor(Color.RED);
            editAnno.setTextColor(Color.RED);
            editGiorno.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    editGiorno.setError(null);
                    editGiorno.setTextColor(Color.BLACK);
                    editMese.setTextColor(Color.BLACK);
                    editAnno.setTextColor(Color.BLACK);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            return false;
        }
    }

    private void acquisizioneData(){
        anno = Integer.parseInt(editAnno.getText().toString());
        mese = Integer.parseInt(editMese.getText().toString());
        giorno = Integer.parseInt(editGiorno.getText().toString());
        dataNascita = anno + "-" + mese + "-" + giorno;
    }
}
