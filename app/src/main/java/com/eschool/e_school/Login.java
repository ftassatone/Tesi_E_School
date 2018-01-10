package com.eschool.e_school;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.eschool.e_school.alunno.HomeAlunno;
import com.eschool.e_school.connessione.JsonRequest;
import com.eschool.e_school.connessione.RequestSingleton;
import com.eschool.e_school.docente.HomeDocente;
import com.eschool.e_school.docente.Registrazione;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    private EditText usernameTxt, passwordTxt;
    private Button btConfermaLogin;
    private TextView pswDimenticata, linkNuovoDoc, txtCredenzialiErrate;
    private String urlLogin;
    private String username, psw, pswDecifrata, pswCifrata;
    private AlertDialog.Builder infoAlert;
    // CRED: nome del file sul quale verranno salvate le credenziali
    private final static String CRED = "credenziali";
    private SharedPreferences.Editor edit;
    private JsonRequest richiesta;
    private CheckBox cbRicorda;
    public static String utente;
    private Boolean doc = false, al = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        utente = getIntent().getStringExtra("utente");

        if (utente.equalsIgnoreCase("docente"))
            doc = true;
        else if (utente.equalsIgnoreCase("alunno"))
            al = true;

        if (doc) {
            setContentView(R.layout.login);
            usernameTxt = (EditText) findViewById(R.id.usernameTxt);
            passwordTxt = (EditText) findViewById(R.id.passwordTxt);
            btConfermaLogin = (Button) findViewById(R.id.btConfermaLogin);
            pswDimenticata = (TextView) findViewById(R.id.linkPswDimenticata);
            linkNuovoDoc = (TextView) findViewById(R.id.linkNuovoDoc);
            txtCredenzialiErrate = (TextView) findViewById(R.id.txtCredenzialiErrate);

            pswDimenticata.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    infoAlert.setTitle("Recupero password");
                    infoAlert.setMessage("La nuova password è stata inviata all'indirizzo email");
                    AlertDialog alert = infoAlert.create();
                    alert.show();
                }
            });

            linkNuovoDoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent vaiRegistazione = new Intent(Login.this, Registrazione.class);
                    startActivity(vaiRegistazione);
                }
            });

        } else if (al) {
            setContentView(R.layout.login_alunno);
            usernameTxt = (EditText) findViewById(R.id.usernameTxtAlunno);
            passwordTxt = (EditText) findViewById(R.id.passwordTxtAlunno);
            btConfermaLogin = (Button) findViewById(R.id.btConfermaLoginAl);
            txtCredenzialiErrate = (TextView) findViewById(R.id.txtCredenzialiErrateAl);
            cbRicorda = (CheckBox) findViewById(R.id.cbRicorda);

            SharedPreferences credenziali = getSharedPreferences(CRED, MODE_PRIVATE);
            if(!credenziali.getString("username","").equals("") && !credenziali.getString("password","").equals("")){
                usernameTxt.setText(credenziali.getString("username",""));
                String pswCred = credenziali.getString("password","");
                pswDecifrata = MyCript.decrypt(pswCred);
                passwordTxt.setText(pswDecifrata);
                //passwordTxt.setText(credenziali.getString("password", ""));
                cbRicorda.setChecked(true);
            }
        }

        infoAlert = new AlertDialog.Builder(Login.this);

        //controllo futuro sulla password dimenticata

        //button di accesso
        btConfermaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameTxt.getText().toString().trim().length() != 0 || passwordTxt.getText().toString().trim().length() != 0) {
                    username = usernameTxt.getText().toString().trim();
                    psw = passwordTxt.getText().toString().trim();

                    login();
                } else {
                    usernameTxt.setError("Inserire username");
                    usernameTxt.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            usernameTxt.setTextColor(Color.BLACK);
                            usernameTxt.setError(null);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    passwordTxt.setError("Inserire password");
                    passwordTxt.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            passwordTxt.setTextColor(Color.BLACK);
                            passwordTxt.setError(null);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                }
                Log.v("LOG", "par " + username + ", " + psw);
            }
        });
    }

    //metodo di login, acquisisce i dati dalle editText e le invia al server,
    // ricevuta la risposta (se esiste o meno l'utenete loggato) reindirizza
    // alla homeDocente,inviando l'user dell'utenete loggato
    public void login(){
        //raccolgo i dati inseriti dall'utente
        HashMap<String,String> parametri = new HashMap<String, String>();
        parametri.put("username",username);
        Log.v("LOG","parametri "+ parametri);

        if(doc){
            urlLogin = "http://www.eschooldb.altervista.org/PHP/login.php";
            richiesta = new JsonRequest(Request.Method.POST, urlLogin, parametri, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray docente = response.getJSONArray("docente");
                        if(docente.length()!=0) {
                            pswCifrata = docente.getJSONObject(0).getString("password");
                            pswDecifrata = MyCript.decrypt(pswCifrata);

                            if(psw.equals(pswDecifrata)){
                                Intent vai = new Intent(getApplicationContext(),HomeDocente.class);
                                vai.putExtra("username",username);
                                startActivity(vai);
                                finish();
                            }else  {
                                credenzialiErrate();
                            }
                        }else{
                            credenzialiErrate();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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

            }
        else if(al) {
            //urlLogin = "http://www.eschooldb.altervista.org/PHP/loginAlunno.php";
            urlLogin = "http://www.eschooldb.altervista.org/PHP/logAl.php";
            richiesta = new JsonRequest(Request.Method.POST, urlLogin, parametri, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray alunno = response.getJSONArray("alunno");
                        Log.d("DATI","alunno "+alunno);
                        if((alunno.length() != 0)){
                            Log.d("DATI", "---PIENO---");
                            pswCifrata = alunno.getJSONObject(0).getString("password");
                            pswDecifrata = MyCript.decrypt(pswCifrata);
                            if(pswDecifrata.equals(psw)){
                                if (cbRicorda.isChecked()) {
                                    SharedPreferences credenziali = getSharedPreferences(CRED, MODE_PRIVATE);
                                    edit = credenziali.edit();
                                    edit.putString("username", username);
                                    edit.putString("password", pswCifrata);
                                    edit.commit();
                                }
                                //TODO da decommentare (controllare nel php quando c'è un errore di connessione)
                                Intent homeAlunno = new Intent(getApplicationContext(),HomeAlunno.class);
                                homeAlunno.putExtra("cf", alunno.getJSONObject(0).getString("cf"));
                                startActivity(homeAlunno);
                            }else{
                                Log.d("DATI", "---VUOTO---");
                                credenzialiErrate();
                            }
                        }else{
                            Log.d("DATI", "---ERRATE---");
                            credenzialiErrate();
                        }
                        /*Log.d("LOG","sono nella richiesta");
                        JSONObject dati = response.getJSONObject("datiAlunno");
                        Log.d("DATI","alunoo-- "+dati);
                        pswCifrata = dati.getString("password");
                        Log.d("PSW", "pswCifrata "+pswCifrata);
                        pswDecifrata = MyCript.decrypt(pswCifrata);
                        Log.d("LOG","risultato"+dati);
                        if(!dati.equals(null) && pswDecifrata.equals(psw)){
                            if (cbRicorda.isChecked()) {
                                SharedPreferences credenziali = getSharedPreferences(CRED, MODE_PRIVATE);
                                edit = credenziali.edit();
                                edit.putString("username", username);
                                edit.putString("password", pswCifrata);
                                edit.commit();
                            }
                            //TODO da decommentare (controllare nel php quando c'è un errore di connessione)
                            Intent homeAlunno = new Intent(getApplicationContext(),HomeAlunno.class);
                            homeAlunno.putExtra("cf", dati.getString("cf"));
                            startActivity(homeAlunno);
                        }else{
                            credenzialiErrate();
                            Toast.makeText(getApplicationContext(), "Non esiste nessun alunno con le credendiali inserite.", Toast.LENGTH_LONG).show();
                        }*/
                    } catch (JSONException e) {
                        e.printStackTrace();
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
        }
        //richiesta di connessione al server
        RequestSingleton.getInstance(this).addToRequestQueue(richiesta);

    }

    @Override
    public void onBackPressed() {

    }


    private void credenzialiErrate(){
        Log.d("LOG","sono nell'else");
        txtCredenzialiErrate.setVisibility(View.VISIBLE);
        usernameTxt.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.errore));
        usernameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                usernameTxt.setTextColor(Color.BLACK);
                usernameTxt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwordTxt.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.errore));
        passwordTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordTxt.setTextColor(Color.BLACK);
                passwordTxt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
