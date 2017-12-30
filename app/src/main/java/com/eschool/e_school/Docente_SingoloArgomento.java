package com.eschool.e_school;

import android.app.Dialog;
import android.content.Intent;
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
import android.view.Window;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Docente_SingoloArgomento extends AppCompatActivity {

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

            }
        });
        btCaricaFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

            }
        });

        listViewTeoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
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

    /*void downloadFile(String path) {
        File dir;
        try {
            URL url = new URL(path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //connect
            urlConnection.connect();
            File sdCard;
            //controllo sulla memeoria esterna
            //String state = Environment.getExternalStorageState();
            sdCard = Environment.getExternalStorageDirectory();
            if (sdCard.exists()) {
                dir = sdCard;
                Log.d("DATI", "card");
            } else {
                Log.d("DATI", "no card");
                dir = getDir("prova.pdf", MODE_PRIVATE);

            }
            //set the path where we want to save the file
            //File SDCardRoot = Environment.getExternalStorageDirectory();
            Log.d("DATI", "memorizzo qui " + dir);
            //create a new file, to save the downloaded file
            File file = new File(dir, "teoria.pdf");

            FileOutputStream fileOutput = new FileOutputStream(file);

            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            totalSize = urlConnection.getContentLength();

            runOnUiThread(new Runnable() {
                public void run() {
                    pb.setMax(totalSize);
                }
            });

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                // update the progressbar //
                runOnUiThread(new Runnable() {
                    public void run() {
                        pb.setProgress(downloadedSize);
                        //per un'eventuale indicazione dei kb scaricati
                        //float per = ((float) downloadedSize / totalSize) * 100;
                        //cur_val.setText("Downloaded " + downloadedSize + "KB / " + totalSize + "KB (" + (int) per + "%)");
                    }
                });
            }
            //close the output stream when complete //
            fileOutput.close();
            runOnUiThread(new Runnable() {
                public void run() {
                    // pb.dismiss(); // if you want close it..
                }
            });
            if (file.exists()) {
                Uri pathF = Uri.fromFile(file);
                Log.d("DATI", String.valueOf(path));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(pathF, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                Log.d("DATI", "non ci sono");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showError(final String err){
        runOnUiThread(new Runnable() {
            public void run() {
                Log.d("DATI","errore");
                Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
            }
        });
    }

   /*void showProgress(String file_path){
        dialog = new Dialog(Docente_SingoloArgomento.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progressbar);
        dialog.setTitle("Download Progress");
        dialog.show();

        pb = (ProgressBar)dialog.findViewById(R.id.progressBar);
        pb.setProgress(0);
        pb.setProgressDrawable(getResources().getDrawable(R.drawable.progress_line));
    }*/
}
