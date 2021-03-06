package com.eschool.e_school.alunno;

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
import com.eschool.e_school.adapter.ButtonAdapter;
import com.eschool.e_school.PreLogin;
import com.eschool.e_school.R;
import com.eschool.e_school.connessione.JsonRequest;
import com.eschool.e_school.connessione.RequestSingleton;
import com.eschool.e_school.elementiBase.Alunno;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeAlunno extends AppCompatActivity {

    private GridView grigliaMaterie;
    private TextView txtBenvenutoAlunno;
    private AlertDialog.Builder infoAlert;
    private String url = "http://www.eschooldb.altervista.org/PHP/getMaterieAlunno.php";
    private String materia,livello;
    public  String alunno;
    public static Alunno al;
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
        //alunno = getIntent().getStringExtra("cf");
        al = (Alunno) getIntent().getSerializableExtra("alunno");
        Log.d("ALUNNO","--"+al);
        txtBenvenutoAlunno.setText("Ciao "+al.getNome());
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
        parametri.put("cf", al.getCf());
        JsonRequest richiesta = new JsonRequest(Request.Method.POST, url, parametri, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                listaBt = new ArrayList<>();
                try {
                    JSONArray materie = response.getJSONArray("nomeMaterie");
                    arrayMaterie = new ArrayList<>();
                    for(int i=0; i<materie.length(); i++){
                        materia = materie.getJSONObject(i).getString("nomeMateria");
                        Log.d("MATERIA","mat "+materia);
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
