package com.eschool.e_school;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Alunno_HomeAlunno extends AppCompatActivity {

    private GridView grigliaMaterie;
    private TextView txtBenvenutoAlunno;
    private AlertDialog.Builder infoAlert;
    private String url = "http://www.eschooldb.altervista.org/PHP/getMaterieAlunno.php";
    private String alunno, materia,livello;
    private ArrayList<String> arrayMaterie;
    private ArrayAdapter<String> adapterMaterie;
    private ButtonAdapter adapter;
    private ArrayList<Button> listaBt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_alunno);

        grigliaMaterie = (GridView) findViewById(R.id.grigliaMaterie);
        txtBenvenutoAlunno = (TextView) findViewById(R.id.txtBenvenutoAlunno);
        infoAlert = new AlertDialog.Builder(getApplicationContext());
        alunno = getIntent().getStringExtra("cf");

        connessione();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            Intent i = new Intent(getApplicationContext(),PreLogin.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void connessione(){
        HashMap<String, String> parametri = new HashMap<String, String>();
        parametri.put("cf", alunno);
        JsonRequest richiesta = new JsonRequest(Request.Method.POST, url, parametri, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                listaBt = new ArrayList<>();

                try {
                    JSONArray materie = response.getJSONArray("nomeMaterie");
                    arrayMaterie = new ArrayList<>();
                    for(int i=0; i<materie.length(); i++){
                        txtBenvenutoAlunno.setText(materie.getJSONObject(i).getString("nome"));
                        materia = materie.getJSONObject(i).getString("nomeMateria");
                        livello = String.valueOf(materie.getJSONObject(i).getString("nomeClasse").charAt(0));
                        arrayMaterie.add(materia);
                        listaBt.add(new Button(getApplicationContext()));
                    }
                    adapter = new ButtonAdapter(getApplicationContext(),listaBt,arrayMaterie,livello);
                    grigliaMaterie.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.v("LOG", "e "+e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("LOG","errore: "+error.toString());
                infoAlert.setTitle("Errore di connessione");
                infoAlert.setMessage("Controllare connessione internet e riprovare.");
                AlertDialog alert = infoAlert.create();
                alert.show();
            }
        });
        RequestSingleton.getInstance(this).addToRequestQueue(richiesta);
    }

    @Override
    public void onBackPressed() {

    }
}
