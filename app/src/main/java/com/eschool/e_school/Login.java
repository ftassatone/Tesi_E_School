package com.eschool.e_school;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by LenovoZ70 on 17/09/2017.
 */
public class Login extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button btConfermaLogin = (Button) findViewById(R.id.btConfermaLogin);

        TextView textView = (TextView) findViewById(R.id.linkPswDimenticata);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder miaAlert = new AlertDialog.Builder(Login.this);
                miaAlert.setTitle("Recupero password");
                miaAlert.setMessage("La nuova password è stata inviata all'indirizzo email");
                AlertDialog alert = miaAlert.create();
                alert.show();
            }
        });

        btConfermaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, HomeDocente.class);
                startActivity(i);
                //gjg
            }
        });
    }
}
