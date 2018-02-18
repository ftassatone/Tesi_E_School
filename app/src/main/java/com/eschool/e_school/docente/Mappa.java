package com.eschool.e_school.docente;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eschool.e_school.Dialog.DialogDescr;
import com.eschool.e_school.R;

import java.util.ArrayList;
import java.util.Locale;

public class Mappa extends AppCompatActivity implements TextToSpeech.OnInitListener{
    ArrayList<Nodo> nodiMappa;
    LinearLayout parent;
    public ArrayList<String> titoli, descrizioni;
    public static String titolo, descr;
    int c;
    TextToSpeech tts;
    boolean r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mappa);

        tts = new TextToSpeech(getApplicationContext(), this);
        parent = (LinearLayout) findViewById(R.id.parent);
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        titoli = new ArrayList<>();
        descrizioni = new ArrayList<>();
        nodiMappa = new ArrayList<>();
        nodiMappa = (ArrayList<Nodo>) getIntent().getSerializableExtra("listaNodi");
        Log.d("LOG","size "+nodiMappa);
        r = true;
        creaMappa();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void creaMappa(){
        for(int i=0; i<nodiMappa.size(); i++){
            if(i!=nodiMappa.size()-1){
                TextView titolo = new TextView(getApplicationContext());
                titolo.setId(i);
                titolo.setGravity(Gravity.CENTER);
                titolo.setText(nodiMappa.get(i).getTitolo());
                titolo.setTag(nodiMappa.get(i).getTitolo());
                titolo.setBackground(getDrawable(R.drawable.rounded));
                titolo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                titolo.setPadding(10,5,10,5);
                View view = new View(getApplicationContext());
                view.setTag("");
                view.setBackgroundColor(getColor(R.color.colorPrimaryDark));
                view.setLayoutParams(new LinearLayout.LayoutParams(3,25));
                parent.addView(titolo);
                parent.addView(view);
                titoli.add(titolo.getText().toString());
                Log.d("LOG", "titoli in mappa "+titoli);
                descrizioni.add(nodiMappa.get(i).getDescrizione());
                Log.d("LOG", "descrizioni in mappa"+descrizioni);
                titolo.setOnClickListener(visualizzaDialog(titolo));
            }else{
                TextView titolo = new TextView(getApplicationContext());
                titolo.setId(i);
                titolo.setGravity(Gravity.CENTER);
                titolo.setText(nodiMappa.get(i).getTitolo());
                titolo.setBackground(getDrawable(R.drawable.rounded));
                titolo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                titolo.setPadding(10,5,10,5);
                titolo.setTag(nodiMappa.get(i).getTitolo());
                parent.addView(titolo);
                titoli.add(titolo.getText().toString());
                descrizioni.add(nodiMappa.get(i).getDescrizione());
                titolo.setOnClickListener(visualizzaDialog(titolo));
            }
        }
    }

    public View.OnClickListener visualizzaDialog(TextView textView){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = view.getId();
                for(int i=0; i<nodiMappa.size(); i++) {
                    if (c == i) {
                        titolo = nodiMappa.get(i).getTitolo();
                        leggi(titolo);
                        descr = nodiMappa.get(i).getDescrizione();
                        if (!descr.equals("")) {
                            DialogFragment dialogFragment = new DialogDescr();
                            dialogFragment.show(getFragmentManager(), "dialogDescr");
                        }
                    }
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.salva_mappa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_salva_mappa) {

        }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void leggi(String x){
        tts.speak(x,TextToSpeech.QUEUE_FLUSH, null, null);
    }
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.ITALIAN); //impostiamo l'italiano
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.d("LOG", "Mancano i dati vocali: installali per continuare");
            } else {
                Log.d("LOG", "giunge qui se tutto va come previsto");
            }
        } else {
            Log.d("LOG", "Il Text To Speech sembra non essere supportato");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


