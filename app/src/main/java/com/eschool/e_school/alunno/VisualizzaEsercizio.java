package com.eschool.e_school.alunno;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.eschool.e_school.R;


public class VisualizzaEsercizio extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calcolatrice);
        getSupportFragmentManager().beginTransaction().add(R.id.containerEsercizi, new EsercizioFragment()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.containerCalc, new FragmentCalcolatrice()).commit();

    }
}
