package com.eschool.e_school.Dialog;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.eschool.e_school.docente.Mappa;

import java.util.Locale;

public class DialogDescr extends DialogFragment implements TextToSpeech.OnInitListener{

    TextToSpeech tts;
    Boolean wantToCloseDialog = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        tts = new TextToSpeech(getActivity(), this);
        builder.setTitle(Mappa.titolo).setMessage(Mappa.descr)
                .setPositiveButton("ascolta", new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tts.speak(Mappa.descr, TextToSpeech.QUEUE_FLUSH, null, null);
                        //builder.setCancelable(false);
                        if (wantToCloseDialog){
                            dismiss();
                        }
                    }
                }).setNegativeButton("chiudi", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                wantToCloseDialog = true;
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.ITALIAN);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.d("LOG", "Mancano i dati vocali: installali per continuare");
            }
        }
    }
}