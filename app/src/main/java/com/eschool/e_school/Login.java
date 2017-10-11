package com.eschool.e_school;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity{

    private EditText usernameTxt,passwordTxt;
    private Button btConfermaLogin;
    private TextView pswDimenticata,linkNuovoDoc, txtCredenzialiErrate;
    private String urlLogin = "http://www.eschooldb.altervista.org/PHP/login.php";
    private String matricolaDoc, pswDoc;
    private RequestQueue requestQueue;
    private AlertDialog.Builder infoAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        usernameTxt = (EditText) findViewById(R.id.usernameTxt);
        passwordTxt = (EditText) findViewById(R.id.passwordTxt);
        btConfermaLogin = (Button) findViewById(R.id.btConfermaLogin);
        pswDimenticata = (TextView) findViewById(R.id.linkPswDimenticata);
        linkNuovoDoc = (TextView) findViewById(R.id.linkNuovoDoc);
        txtCredenzialiErrate = (TextView) findViewById(R.id.txtCredenzialiErrate);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        infoAlert = new AlertDialog.Builder(Login.this);

        //controllo futuro sulla password dimenticata
        pswDimenticata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoAlert.setTitle("Recupero password");
                infoAlert.setMessage("La nuova password è stata inviata all'indirizzo email");
                AlertDialog alert = infoAlert.create();
                alert.show();
            }
        });

        //button di accesso
        btConfermaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usernameTxt.getText().toString().trim().length()!= 0 || passwordTxt.getText().toString().trim().length() != 0){
                    matricolaDoc = usernameTxt.getText().toString().trim();
                    pswDoc = passwordTxt.getText().toString().trim();
                    login();
                }else{
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
                Log.v("LOG","par "+matricolaDoc +", "+ pswDoc);

            }
        });

        linkNuovoDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vaiRegistazione = new Intent(Login.this, Registrazione.class);
                startActivity(vaiRegistazione);
            }
        });

    }

    //metodo di login, acquisisce i dati dalle editText e le invia al server,
    // ricevuta la risposta (se esiste o meno l'utenete loggato) reindirizza
    // alla homeDocente,inviando l'user dell'utenete loggato
    public void login(){
        //raccolgo i dati inseriti dall'utente
        HashMap<String,String> parametri = new HashMap<String, String>();
        parametri.put("matricola",matricolaDoc);
        parametri.put("password",pswDoc);

        Log.v("LOG","parametri "+ parametri);

        //richiesta di connessione al server
        JsonRequest richiesta = new JsonRequest(Request.Method.POST, urlLogin, parametri, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String c ="";
                Log.v("LOG","ris "+ response.toString());
                try {
                    c  = response.getString("ris");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(c=="true"){
                    Log.v("LOG","sono qui");
                    Intent vai = new Intent(getApplicationContext(),Home.class);
                    vai.putExtra("username",matricolaDoc);
                    startActivity(vai);
                    finish();
                }else if(c=="false") {
                    /*infoAlert.setTitle("Credenziali errate");
                    infoAlert.setMessage("Username o password errati, riprovare.");
                    AlertDialog alert = infoAlert.create();
                    alert.show();*/
                    txtCredenzialiErrate.setVisibility(View.VISIBLE);
                    usernameTxt.setTextColor(Color.RED);
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

                    passwordTxt.setTextColor(Color.RED);
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
