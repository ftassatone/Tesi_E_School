package com.eschool.e_school.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.eschool.e_school.R;


public class DialogAltText extends DialogFragment {
    EditText txt;
    public interface NoticeDialogListener {
        void onDialogPositiveClick(String path, String testoAlt);
    }
    NoticeDialogListener dialogListener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v  = inflater.inflate(R.layout.layout_dialog_alttext, null);
        txt = v.findViewById(R.id.altText);

        builder.setTitle("TESTO ALTERNATIVO");
        builder.setView(v).setPositiveButton("conferma", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        dialogListener.onDialogPositiveClick(getArguments().getString("path"),txt.getText().toString());
                    }
                }).setNegativeButton("cancella", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogAltText.this.getDialog().cancel();
                    }
                });
        return builder.create();
        }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
           dialogListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()+ " must implement NoticeDialogListener");
        }
    }

}

