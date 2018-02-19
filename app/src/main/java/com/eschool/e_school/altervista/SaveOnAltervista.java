package com.eschool.e_school.altervista;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.eschool.e_school.connessione.JsonRequest;
import com.eschool.e_school.connessione.RequestSingleton;
import com.eschool.e_school.elementiBase.Teoria;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Questa classe permette l'upload su altervista
 */

public class SaveOnAltervista extends AsyncTask<ParametriAltervista,Void,Void> {
    ParametriAltervista param;
    File file;
    String path;
    Boolean risultato = false; // controllo sul risultato del caricamento

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //una volta salvato lo elimino dal dispositivo
        if (risultato)
            file.delete();

    }

    @Override
    protected Void doInBackground(ParametriAltervista... files) {
        FTPClient client = new FTPClient();
        param = files[0];
        file = param.getF();
        try {
            client.connect("ftp.eschooldb.altervista.org");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            client.login("eschooldb", "robyfrancy");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            client.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.enterLocalPassiveMode(); // importante!
        try {
            client.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        boolean result = false;
        try {
            //Apposto di system.current ecc. metti il nome del file che ti interessa.
            //result = client.storeFile("/LOG/" + System.currentTimeMillis() +".pdf", in);
            Log.d("DATO", "name " + file.getName());
            ArrayList<String> lista = new ArrayList<>();
            FTPFile[] dirs = client.listDirectories("File");
            for (FTPFile dir : dirs) {
                Log.d("DATI", "nome dir " + dir.getName());
                lista.add(dir.getName());
            }

            path = "File/" + param.getMateria();
            if (!lista.contains(param.getMateria())) {
                if (client.makeDirectory(path) && client.makeDirectory("File/" + param.getMateria() + "/" + param.getClasse())) {
                    result = client.storeFile("File/" + param.getMateria() + "/" + param.getClasse() + "/" + file.getName(), in);
                }
            } else if (lista.contains(param.getMateria())) {
                Log.d("DATI", "ENTRO!!");
                //controllo l'esistenza della cartella relativa alla classe, se non esiste la creo, altrimenti carico il file
                FTPFile[] dirMat = client.listDirectories(path);
                for (FTPFile dir : dirMat) {
                    Log.d("DATI", "nome dir " + dir.getName());
                    lista.add(dir.getName());
                }
                if (!lista.contains(param.getClasse())) {
                    if (client.makeDirectory(path + "/" + param.getClasse())) {
                        result = client.storeFile(path + "/" + param.getClasse() + "/" + file.getName(), in);
                    }
                } else {
                    FTPFile[] lFiles = client.listFiles(path + "/" + param.getClasse());
                    for (FTPFile f : lFiles) {
                        Log.d("DATI", "nome dir " + f.getName());
                        lista.add(f.getName());
                    }
                    if (!lista.contains(file.getName()))
                        result = client.storeFile(path + "/" + param.getClasse() + "/" + file.getName(), in);
                    else
                        Log.d("DATI", "Esiste");
                }
            } else
                Log.d("DATI", "ERRORE");

        } catch (IOException e) {

            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result) {
            risultato = true;

            Log.v("upload result", "succeeded");
        } else
            Log.d("DATI", "errore");

        try {
            client.logout();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void salvaFileDb() {
        String url = "http://www.eschooldb.altervista.org/PHP/aggiungiFileTeoria.php";
        HashMap<String, String> parametri = new HashMap<String, String>();

        parametri.put("fileTeoria", path + "/" + param.getClasse() + "/" + file.getName());
        parametri.put("argomento",param.getArgomento());
        //parametri.put("dataCreazione",);
        parametri.put("livello",param.getClasse().substring(1));
        parametri.put("nomeMateria",param.getMateria());

        JsonRequest richiesta = new JsonRequest(Request.Method.POST, url, parametri, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestSingleton.getInstance(param.getContext()).addToRequestQueue(richiesta);
    }
}
