package com.eschool.e_school.docente;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.eschool.e_school.R;
import com.eschool.e_school.altervista.ParametriAltervista;
import com.eschool.e_school.altervista.SaveOnAltervista;
import com.eschool.e_school.connessione.JsonRequest;
import com.eschool.e_school.connessione.RequestSingleton;
import com.eschool.e_school.elementiBase.Esercizio;
import com.eschool.e_school.elementiBase.Teoria;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SingoloArgomento extends AppCompatActivity {

    final static int FILE_SELECT_CODE= 0;
    private ListView listViewEsercizi,listViewTeoria;
    private Button btMultiple,btAperte,btFile,btCaricaFile;
    private String argo;
    private String url = "http://www.eschooldb.altervista.org/PHP/SingoloArgomento.php";
    private ArrayList<Teoria> listaTeoria;
    private ArrayList<Esercizio> listaEsercizi;
    private ArrayList<String> righeTeoria, righeEsercizi, listaPath;
    private AlertDialog.Builder infoAlert;
    private String pathFile;
    Dialog dialog;
    ProgressBar pb;
    int downloadedSize = 0;
    int totalSize = 0;
    TextView cur_val;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.singolo_argomento);
        listaTeoria = new ArrayList<>();
        listaEsercizi = new ArrayList<>();
        righeTeoria = new ArrayList<>();
        righeEsercizi = new ArrayList<>();
        listaPath = new ArrayList<>();

        infoAlert = new AlertDialog.Builder(getApplicationContext());

        listViewEsercizi = (ListView) findViewById(R.id.listViewEsercizi);
        listViewTeoria = (ListView) findViewById(R.id.listViewTeoria);
        btAperte = (Button) findViewById(R.id.btAperte);
        btMultiple = (Button) findViewById(R.id.btMultiple);
        btCaricaFile = (Button) findViewById(R.id.btCaricaFile);
        btFile = (Button) findViewById(R.id.btFile);

        argo = getIntent().getStringExtra("argomento");
        connessione();

        btAperte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NuovaPaginaTeoria.class));
            }
        });
        btCaricaFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        btFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btMultiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NuovaPaginaTeoria.class));
            }
        });

        listViewTeoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Log.d("DATI", "cisono");
                //TODO listener per l'apertura del singolo file di teoria
                /*Log.d("DATI","path "+listaPath.get(i));
                //showProgress(listaPath.get(i));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        downloadFile(listaPath.get(i));
                    }
                }).start();*/
            }
        });

    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            Intent vaiHomeClasse = NavUtils.getParentActivityIntent(this);
            vaiHomeClasse.putExtra("Materia", HomeClasse.materia);
            vaiHomeClasse.putExtra("Classe", HomeClasse.classe);
            if (NavUtils.shouldUpRecreateTask(this, vaiHomeClasse)) {
                TaskStackBuilder.create(this).addNextIntentWithParentStack(vaiHomeClasse).startActivities();
            } else {
                NavUtils.navigateUpTo(this, vaiHomeClasse);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void connessione(){
        HashMap<String,String> parametri = new HashMap<>();
        parametri.put("argomento",argo);
        final Gson gson = new Gson();

        JsonRequest richiesta = new JsonRequest(Request.Method.POST, url, parametri, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //mi faccio restituire tutto l'oggetto. ATTENZIONE QUANDO SI EFFETTUANO LE ELIMINAZIONI
                    JSONArray arrayTeoria = response.getJSONArray("listaTeoria");
                    Log.d("DATI","risposta-"+arrayTeoria);
                    for(int i = 0; i<arrayTeoria.length();i++){

                        String str = arrayTeoria.getJSONObject(i).get("fileTeoria").toString();
                        Log.d("DATI","file2 "+str);
                        Teoria t = gson.fromJson(String.valueOf(arrayTeoria.getJSONObject(i)),Teoria.class);
                        Log.d("DATI","t-"+t.toString());
                        righeTeoria.add(t.getTitolo()); //array con i titoli dei file di toeria
                        listaTeoria.add(t); //array con gli oggetti di teoria
                        listaPath.add(str); //array con le path dei file
                        Log.d("DATI","array "+listaPath);
                        ArrayAdapter adapterTeoria = new ArrayAdapter(getApplicationContext(), R.layout.riga_lista_programma,righeTeoria);
                        listViewTeoria.setAdapter(adapterTeoria);
                    }

                    JSONArray arrayEs = response.getJSONArray("listaEsercizi");
                    for(int i = 0; i<arrayEs.length();i++){
                        Esercizio ex = gson.fromJson(String.valueOf(arrayEs.getJSONObject(i)),Esercizio.class);
                        Log.d("LOG","ex-"+ex.toString());
                        Log.d("LOG","ex2-"+String.valueOf(ex.getCodiceEsercizio()));
                        righeEsercizi.add(String.valueOf(ex.getCodiceEsercizio()));
                        listaEsercizi.add(ex);
                        ArrayAdapter adapterEsercizi = new ArrayAdapter(getApplicationContext(),R.layout.riga_lista_programma,righeEsercizi);
                        listViewEsercizi.setAdapter(adapterEsercizi);
                    }
                    Log.d("LOG","size t-"+righeTeoria+" ex-"+righeEsercizi);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("DATI", "errore+"+error);
                    /*infoAlert.setTitle("Errore di connessione");
                    infoAlert.setMessage("Controllare connessione internet e riprovare.");
                    AlertDialog alert = infoAlert.create();
                    alert.show();*/
            }
        });
        RequestSingleton.getInstance(this).addToRequestQueue(richiesta);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d("DATI", "File Uri: " + uri.toString());
                   // String path = ottieniPath(uri, getApplicationContext());
                    Log.d("DATI","Path "+uri.getScheme());
                    Log.d("DATI","ris  "+ottieniPath(uri));

                    /*}else{
                        Log.d("DATI", "non c'è");
                    }*/
                    // Get the path

                    //Log.d("DATI", "File Path: " + path);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String ottieniPath(Uri uri){
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

}
