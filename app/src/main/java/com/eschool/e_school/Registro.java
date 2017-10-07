package com.eschool.e_school;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        //abilita il menu indietro sulla action bar, funziona anche senza, basta mettere la parentActivity nel manifest
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {

    }

}
