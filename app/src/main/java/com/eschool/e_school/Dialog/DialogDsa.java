package com.eschool.e_school.Dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.eschool.e_school.R;

import java.util.ArrayList;

public class DialogDsa extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //TODO vedere cosa usare per i vari strumenti compensativi al posto di un'array statico
        String[] lista= {"sintetizzatore","calcolatrice parlante"};
        //lista degli item selezionati
        final ArrayList itemSelezionati = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.opzioniDSA).setMultiChoiceItems(lista, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                if(isChecked){
                    itemSelezionati.add(i);
                }else if(itemSelezionati.contains(i)){
                    itemSelezionati.remove(Integer.valueOf(i));
                }
            }
        }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //azione positiva
                for(int c=0; c<itemSelezionati.size(); c++){
                    //dovrei settare a true questi strumenti per l'alunno interessato
                }
            }
        }).setNegativeButton(R.string.annulla, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //elimino gli item selezionati dall'array
                itemSelezionati.clear();
            }
        });

        return builder.create();
    }

}
