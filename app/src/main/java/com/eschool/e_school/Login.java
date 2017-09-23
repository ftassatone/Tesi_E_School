package com.eschool.e_school;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity{
    EditText usernameTxt;
    EditText passwordTxt;
    Button btConfermaLogin;
    String urlCarica = "http://www.eschooldb.altervista.org/PHP/carica.php";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btConfermaLogin = (Button) findViewById(R.id.btConfermaLogin);
        usernameTxt = (EditText) findViewById(R.id.usernameTxt);
        passwordTxt = (EditText) findViewById(R.id.passwordTxt);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        TextView textView = (TextView) findViewById(R.id.linkPswDimenticata);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder miaAlert = new AlertDialog.Builder(Login.this);
                miaAlert.setTitle("Recupero password");
                miaAlert.setMessage("La nuova password è stata inviata all'indirizzo email");
                AlertDialog alert = miaAlert.create();
                alert.show();
            }
        });

        btConfermaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Login.this, HomeDocente.class);
                startActivity(i);

            }
        });
    }

    public void loginPost(View view) {
        final String username = usernameTxt.getText().toString();
        final String password = passwordTxt.getText().toString();
        StringRequest richiesta = new StringRequest(Request.Method.POST, urlCarica, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("LOG", "connesso");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("LOG", "non connesso");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametri = new HashMap<String, String>();
                parametri.put("codice", username);
                parametri.put("descrizioneMateria", password);

                return parametri;
            }
        };
        requestQueue.add(richiesta);
    }
}
