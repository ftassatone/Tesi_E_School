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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeAlunno extends AppCompatActivity {

    private GridView grigliaMaterie;
    private TextView txtBenvenutoAlunno;
    private RequestQueue requestQueue;
    private AlertDialog.Builder infoAlert;
    private String url = "http://www.eschooldb.altervista.org/PHP/getMaterieAlunno.php";
    private ArrayAdapter<String> adapterMaterie;
    private Button[] btMaterie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_alunno);

        grigliaMaterie = (GridView) findViewById(R.id.grigliaMaterie);
        txtBenvenutoAlunno = (TextView) findViewById(R.id.txtBenvenutoAlunno);
        infoAlert = new AlertDialog.Builder(getApplicationContext());

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        connessione();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Intent i = new Intent(getApplicationContext(),PreLogin.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void connessione(){
        Log.v("LOG", "sono in connessione");
        JsonRequest richiesta = new JsonRequest(Request.Method.POST, url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray materie = response.getJSONArray("nomeMaterie");
                    Log.v("LOG", "materie "+materie.length());
                    btMaterie = new Button[materie.length()];
                    for(int i=0; i<materie.length(); i++){
                        Log.v("LOG", "nome "+materie.getJSONObject(i).getString("nomeMateria"));
                        btMaterie[i] = new Button(getApplicationContext());
                        btMaterie[i].setText(materie.getJSONObject(i).getString("nomeMateria"));
                        if(materie.getJSONObject(i).getString("nomeMateria").equals("italiano")){
                            //TODO inserisci immagine in base alla materia. Conviene fare uno switch
                        }
                        grigliaMaterie.addView(btMaterie[i]);

                    }
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

        requestQueue.add(richiesta);
    }
}
