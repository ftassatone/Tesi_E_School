package com.eschool.e_school;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Registro extends AppCompatActivity {
    private ListView listViewRegistro;
    private Button btModificaRegistro;
    private AlertDialog.Builder alertModifica;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        listViewRegistro = (ListView) findViewById(R.id.listViewRegistro);
        btModificaRegistro = (Button) findViewById(R.id.btModificaRegistro);
        alertModifica = new AlertDialog.Builder(getApplicationContext());

        btModificaRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public void onBackPressed() {

    }

}
