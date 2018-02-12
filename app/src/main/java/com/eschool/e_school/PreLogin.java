package com.eschool.e_school;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class PreLogin extends AppCompatActivity {
    private RadioButton radioDocente,radioAlunno;
    private Button btConfermaUtente,btAlunno,btDocente;
    int ID_RICHIESTA_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_login);

        //permessi
        int statoPermissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (statoPermissionWrite == PackageManager.PERMISSION_DENIED ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, ID_RICHIESTA_PERMISSION);
        }

        //radioDocente = (RadioButton) findViewById(R.id.radioDocente);
        //radioAlunno = (RadioButton) findViewById(R.id.radioAlunno);
        //btConfermaUtente = (Button) findViewById(R.id.btConfermaUtente);

        btAlunno = (Button) findViewById(R.id.btAlunno);
        btDocente = (Button) findViewById(R.id.btDocente);

        btAlunno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vaiLogin = new Intent(getApplicationContext(),Login.class);
                vaiLogin.putExtra("utente",btAlunno.getText().toString());
                startActivity(vaiLogin);
            }
        });

        btDocente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vaiLogin = new Intent(getApplicationContext(),Login.class);
                vaiLogin.putExtra("utente",btDocente.getText().toString());
                startActivity(vaiLogin);
            }
        });


       /* btConfermaUtente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vaiLogin = new Intent(getApplicationContext(),Login.class);
                if(radioDocente.isChecked()){
                    vaiLogin.putExtra("utente",radioDocente.getText().toString());
                    startActivity(vaiLogin);
                }else if (radioAlunno.isChecked()){
                    vaiLogin.putExtra("utente",radioAlunno.getText().toString());
                    startActivity(vaiLogin);
                }else{
                    Toast.makeText(getApplicationContext(), "Seleziona una tipologia di utente", Toast.LENGTH_SHORT).show();
                }

            }
        });*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bt_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            //TODO vai a info
            //Intent i = new Intent(PreLogin.this,Info.class);
            //startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
