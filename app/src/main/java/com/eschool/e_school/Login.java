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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity{
    EditText usernameTxt;
    EditText passwordTxt;
    Button btConfermaLogin;
    String urlCarica = "http://www.eschooldb.altervista.org/PHP/loginDocente.php";
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
        final ArrayList<String> listaMaterie = new ArrayList<>();
        JsonObjectRequest richiesta = new JsonObjectRequest(Request.Method.POST, urlCarica, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("LOG", "connesso");
                try {
                    JSONArray materie = response.getJSONArray("materie");

                    for(int i = 0; i < materie.length(); i++){
                        listaMaterie.add(materie.get(i).toString());
                        //JSONObject materia = materie.getJSONObject(i);

                        //String nomeMateria = materia.getString("descrizioneMateria");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Login.this,HomeDocente.class);
                i.putExtra("arrayMaterie",listaMaterie);
                startActivity(i);
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
