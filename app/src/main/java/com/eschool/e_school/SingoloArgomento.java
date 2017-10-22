package com.eschool.e_school;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class SingoloArgomento extends AppCompatActivity {
    private ListView listViewEsercizi,listViewTeoria;
    //djn
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singolo_argomento);

        listViewEsercizi = (ListView) findViewById(R.id.listViewEsercizi);
        listViewTeoria = (ListView) findViewById(R.id.listViewTeoria);

    }
}
