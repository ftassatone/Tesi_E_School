package com.eschool.e_school.docente;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.eschool.e_school.R;

import java.util.ArrayList;

/**
 * Created by LenovoZ70 on 18/02/2018.
 */
public class Riassumiamo extends AppCompatActivity {
    LinearLayout parent;
    EditText titolo, descrizioneInv,sottotitolo, descrizione;
    Button btNewLines;
    ArrayList<Nodo> listaNodi;
    int count, seq;
    ArrayList<EditText> editTitoli, editDescrizioni;
    ArrayList<String> titoli, descrizioni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_crea_mappa);

        parent = (LinearLayout) findViewById(R.id.parent);
        titolo = (EditText) findViewById(R.id.titolo);
        descrizioneInv = (EditText) findViewById(R.id.descrizioneInv);
        btNewLines = (Button) findViewById(R.id.btNewLines);
        count = 0;
        listaNodi = new ArrayList<>();
        editTitoli = new ArrayList<>();
        editDescrizioni = new ArrayList<>();
        titoli = new ArrayList<>();
        descrizioni = new ArrayList<>();

        btNewLines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTitoli.isEmpty()){
                    Log.d("LOG", "size "+editTitoli.size());
                    if(titolo.getText().toString().equals("")){
                        Log.d("LOG", "edit vuota");
                        titolo.setError("E' obbligatorio compilare il campo");
                        titolo.setTextColor(Color.RED);
                        titolo.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                titolo.setError(null);
                                titolo.setTextColor(Color.BLACK);
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });
                    }else{
                        editTitoli.add(titolo);
                        editDescrizioni.add(descrizioneInv);
                        LinearLayout newLinear = new LinearLayout(getApplicationContext());
                        newLinear.setOrientation(LinearLayout.HORIZONTAL);
                        newLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
                        sottotitolo = new EditText(getApplicationContext());
                        sottotitolo.setHint("Inserisci sottotitolo");
                        descrizione = new EditText(getApplicationContext());
                        descrizione.setHint("Inserisci descrizione");
                        newLinear.addView(sottotitolo);
                        newLinear.addView(descrizione);
                        parent.addView(newLinear);
                        sottotitolo.setId(count);
                        descrizione.setId(count);
                        count++;
                    }
                }else{
                    if(controllo()) {
                        editTitoli.add(sottotitolo);

                        editDescrizioni.add(descrizione);
                        final LinearLayout newLinear = new LinearLayout(getApplicationContext());
                        newLinear.setOrientation(LinearLayout.HORIZONTAL);
                        newLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
                        sottotitolo = new EditText(getApplicationContext());
                        sottotitolo.setHint("Inserisci sottotitolo");
                        descrizione = new EditText(getApplicationContext());
                        descrizione.setHint("Inserisci descrizione");
                        newLinear.addView(sottotitolo);
                        newLinear.addView(descrizione);
                        parent.addView(newLinear);
                        sottotitolo.setId(count);
                        descrizione.setId(count);
                        count++;
                    }
                }
            }
        });
    }

    public boolean controllo(){
        boolean res = true;
        if(sottotitolo.getText().toString().equals("")){
            sottotitolo.setError("E' obbligatorio compilare il campo");
            sottotitolo.setTextColor(Color.RED);
            sottotitolo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    sottotitolo.setError(null);
                    sottotitolo.setTextColor(Color.BLACK);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            res = false;
        }else if(descrizione.getText().toString().equals("")) {
            descrizione.setError("E' obbligatorio compilare il campo");
            descrizione.setTextColor(Color.RED);
            descrizione.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    descrizione.setError(null);
                    descrizione.setTextColor(Color.BLACK);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            res = false;
        }
        return res;
    }

    public void creaNodo(){
        seq++;
        if(controllo()) {
            editTitoli.add(sottotitolo);
            editDescrizioni.add(descrizione);
            for(int i =0; i<editTitoli.size(); i++){
                Nodo nodo = new Nodo(i,titolo.getText().toString(),editTitoli.get(i).getText().toString(), editDescrizioni.get(i).getText().toString(),seq);
                listaNodi.add(nodo);
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crea_mappa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_crea_mappa) {
            creaNodo();
            Intent vaiCreaMappa = new Intent(getApplicationContext(), Mappa.class);
            vaiCreaMappa.putExtra("listaNodi", listaNodi);
            startActivity(vaiCreaMappa);
        }

        return super.onOptionsItemSelected(item);
    }
}

