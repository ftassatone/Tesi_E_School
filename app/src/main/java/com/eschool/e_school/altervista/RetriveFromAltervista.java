package com.eschool.e_school.altervista;

import android.os.AsyncTask;
import android.renderscript.Sampler;
import android.util.Log;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Edilio on 01/12/2017.
 * LEGGIMI: Questa classe serve a creare un thread asincrono che si collega alla directory di Altervista
 * e serve unicamente ad ottenere un arraylist di stringhe che sono i "contenuti" delle cartella scelta.
 */

public class RetriveFromAltervista extends AsyncTask<Integer,Void,ArrayList<String>> {

    private ArrayList<String> list;


    @Override
    protected ArrayList<String> doInBackground(Integer... voids) {
        int choise = voids[0];
        list = new ArrayList<>();
        FTPClient client = new FTPClient();
        try {
		//questo path lo ottieni da altervista se clicchi su connessione ftp, ti da tutte le informazioni li
            client.connect("ftp.guitiming.altervista.org");
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.enterLocalPassiveMode();
        try {
		//metti la tua login e password
            client.login("guitiming", "kingigofku94");
        } catch (IOException e) {
            e.printStackTrace();
        }
        FTPFile[] files = new FTPFile[0];
	//Allora choise � un intero che passavo per vedere in quale delle due cartelle mi interessa andare a vedere, se conosci la path a prescindere
	//non � un problema
        if(choise==1) {
            try {
                files = client.listFiles("/Sequence/");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(choise==0){
            try {
                files = client.listFiles("/AndroidInterface/");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	//qui inserisco i nomi del file nell'arraylist di stringhe che poi restituisco e faccio visualizzare nella mia UI
        for (FTPFile file : files) {
            System.out.println(file.getName());
            Log.d("FileSequence", file.getName());
            list.add(file.getName());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream("Ftp Files/" + file.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                client.retrieveFile(file.getName(), fos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
