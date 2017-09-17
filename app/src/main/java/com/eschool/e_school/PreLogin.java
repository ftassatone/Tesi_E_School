package com.eschool.e_school;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

/**
 * Created by LenovoZ70 on 17/09/2017.
 */
public class PreLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_login);

        RadioButton radioDocente = (RadioButton) findViewById(R.id.radioDocente);
        RadioButton radioAlunno = (RadioButton) findViewById(R.id.radioAlunno);
        Button btConfermaUtente = (Button) findViewById(R.id.btConfermaUtente);


        btConfermaUtente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PreLogin.this,HomeDocente.class);
                startActivity(i);
            }
        });


    }
}
