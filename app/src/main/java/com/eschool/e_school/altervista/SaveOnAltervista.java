package com.eschool.e_school.altervista;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Questa classe permette l'upload su altervista
 */

public class SaveOnAltervista extends AsyncTask<File,Void,Void> {
    File file;
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //una volta salvato lo elimino dal dispositivo
        file.delete();
    }

    @Override
    protected Void doInBackground(File... files) {
        FTPClient client = new FTPClient();
        file = files[0];
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
            Log.d("DATO","name " + file.getName());
            result = client.storeFile("/File/"+file.getName(), in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result) Log.v("upload result", "succeeded");
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
}

