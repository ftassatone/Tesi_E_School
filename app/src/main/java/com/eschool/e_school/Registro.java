package com.eschool.e_school;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by LenovoZ70 on 17/09/2017.
 */
public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        //abilita il menu indietro sulla action bar, funziona anche senza, basta mettere la parentActivity nel manifest
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
