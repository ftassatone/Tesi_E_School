package com.eschool.e_school.alunno;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eschool.e_school.R;

import java.util.Locale;

public class CalcolatriceParlante extends Activity implements TextToSpeech.OnInitListener {

    private TextView mDisplay, mSecondaryDisplay,mOperatorDisplay,mMemoryDisplay, mSecondaryDisplay2,mOperatorDisplay2,displayRis;
    private ImageButton mButton1,mButton2,mButton3,mButton4,mButton5,mButton6,mButton7,mButton8,mButton9,mButton0,mButtonPlus, mButtonMinus,
            mButtonMultiplication,mButtonDivision, mButtonDot, mButtonEqual,mButtonClear,mButtonMemoryClear,mButtonMemorySet,mButtonMemoryRecall;
    private View linea;
    private Boolean equalJustPressed = false;
    private TextToSpeech tts;
    private String numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calcolatrice_parlante);

        tts = new TextToSpeech(getApplicationContext(), this);
        mDisplay = findViewById(R.id.calcdisplay);
        mSecondaryDisplay = findViewById(R.id.secondarydisplay);
        mOperatorDisplay = findViewById(R.id.operatordisplay);
        mSecondaryDisplay2 = findViewById(R.id.secondarydisplay2);
        mOperatorDisplay2 = findViewById(R.id.operatordisplay2);
        mMemoryDisplay = findViewById(R.id.memorydisplay);
        displayRis = findViewById(R.id.displayRis);

        linea = findViewById(R.id.linea);

        mButton1 = this.findViewById(R.id.Button1);
        mButton2 = this.findViewById(R.id.Button2);
        mButton3 = this.findViewById(R.id.Button3);
        mButton4 = this.findViewById(R.id.Button4);
        mButton5 = this.findViewById(R.id.Button5);
        mButton6 = this.findViewById(R.id.Button6);
        mButton7 = this.findViewById(R.id.Button7);
        mButton8 = this.findViewById(R.id.Button8);
        mButton9 = this.findViewById(R.id.Button9);
        mButton0 = this.findViewById(R.id.Button0);
        mButtonPlus = this.findViewById(R.id.ButtonPlus);
        mButtonMinus = this.findViewById(R.id.ButtonMinus);
        mButtonMultiplication = this.findViewById(R.id.ButtonMolt);
        mButtonDivision = this.findViewById(R.id.ButtonDiv);
        mButtonDot = this.findViewById(R.id.ButtonDot);
        mButtonEqual = this.findViewById(R.id.ButtonEqual);
        mButtonClear = this.findViewById(R.id.ButtonClear);
        mButtonMemoryClear = this.findViewById(R.id.ButtonMemoryClear);
        mButtonMemorySet = this.findViewById(R.id.ButtonMemorySet);
        mButtonMemoryRecall = this.findViewById(R.id.ButtonMemoryRecall);

        mButtonDot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //String x = mButtonDot.getTag().toString();
                leggi("punto");
                if (mDisplay.getText().toString().contains(".")) {
                    //do nothing
                } else {
                    mDisplay.setText(mDisplay.getText().toString()+".");
                }
            }
        });

        mButtonPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButtonPlus.getTag().toString();
                leggi(x);
                scrivi(v.getTag().toString());
            }
        });

        mButtonMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                leggi("meno");
                scrivi(v.getTag().toString());
            }
        });

        mButtonMultiplication.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                leggi("per");
                scrivi(v.getTag().toString());
            }
        });

        mButtonDivision.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                leggi("diviso");
                scrivi(v.getTag().toString());
            }
        });

        mButtonEqual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Boolean controlloDec= false;
                int nDec = 0;
                float leggiNum = 0;
                scrivi(v.getTag().toString());
                if(mSecondaryDisplay.getText().toString().contains(".")) {
                    controlloDec = true;
                    String[] p = mSecondaryDisplay.getText().toString().split("\\.");
                    nDec = p[1].length();
                }
                float op1 = Float.parseFloat(mSecondaryDisplay.getText().toString());
                float op2 = Float.parseFloat(mSecondaryDisplay2.getText().toString());
                //float op2 = Float.parseFloat(mDisplay.getText().toString());
                float res = 0;
                switch (mOperatorDisplay.getText().toString().charAt(0)) {
                    case '+': res = op1 + op2;leggi(String.valueOf(res)); break;
                    case '-': res = op1 - op2;leggi(String.valueOf(res)); break;
                    case 'x': res = op1 * op2;leggi(String.valueOf(res)); break;
                    case '/': res = op1 / op2;leggi(String.valueOf(res)); break;
                    default: //should nevere be here!
                }
                String ris = String.valueOf(res);
                if(controlloDec){
                    leggiNum = arrotonda(res,nDec);
                    displayRis.setText(Float.toString(leggiNum));
                    leggi("uguale "+ leggiNum);
                }else{
                    Log.d("DEC","ris no dec "+ris);
                    int r = (int) res;
                    displayRis.setText(Integer.toString(r));
                    leggi("uguale "+ r);
                }
                equalJustPressed = true;
            }
        });

        mButtonClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                leggi("cancella");
                mSecondaryDisplay.setText("");
                mOperatorDisplay.setText("");
                mSecondaryDisplay2.setText("");
                mOperatorDisplay2.setText("");
                displayRis.setText("");
                mDisplay.setText("");
            }
        });

        mButtonMemorySet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                leggi("Salva in memoria");
                mMemoryDisplay.setText(displayRis.getText().toString());
            }
        });

        mButtonMemoryRecall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                leggi("Richiama dati in memoria");
                mDisplay.setText(mMemoryDisplay.getText().toString());
            }
        });

        mButtonMemoryClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                leggi("Cancella memoria");
                mMemoryDisplay.setText("");
            }
        });
    }

    // ARROTONDAMENTO CLASSICO
    public static float arrotonda(float value, int numCifreDecimali) {
        float temp = (float) Math.pow(10, numCifreDecimali);
        return Math.round(value * temp) / temp;
    }

    private void crealineaOp(View v){
        LinearLayout riga = new LinearLayout(getApplicationContext());
        riga.setOrientation(LinearLayout.HORIZONTAL);
        TextView operatore = new TextView(getApplicationContext());
        TextView op = new TextView(getApplicationContext());
        Button button = (Button) v;
        String x = button.getText().toString();
        riga.addView(op);
        riga.addView(operatore);
        if(equalJustPressed){
            View linea = new View(getApplicationContext());
            riga.addView(linea);
        }
    }

    private void scrivi(String op) {
        if (!mSecondaryDisplay.getText().toString().equalsIgnoreCase("") && !mOperatorDisplay.getText().toString().equalsIgnoreCase("")
                && op.equalsIgnoreCase("=")){
            //controllo se i due operandi hanno la parte decimale
            //in caso positivo, controllo il numero di cifre decimali
            //se il numero di cifre decimali è lo stesso, non ho nulla da fare,
            //altrimenti se è diverso devo aggiungere al numero con meno cifre decimali tanti 0 quant'è la differenza tra le quantità di
            //cifre decimali dei numeri
            String num2 = mDisplay.getText().toString();
            if(numero.contains(".") && num2.contains(".")){
                String[] parti = numero.split("\\.");
                Log.d("DEC","PARTI "+parti.length);
                int numDec = parti[1].length();
                String[] parti2 = num2.split("\\.");
                int numDec2 = parti2[1].length();
                if(numDec > numDec2){
                    int diff = numDec-numDec2;
                    for(int i=0; i< diff;i++){
                        num2 = num2+"0";
                    }
                    mSecondaryDisplay2.setText(num2);
                }else if(numDec < numDec2){
                    int diff = numDec2-numDec;
                    for(int i=0; i< diff;i++){
                        numero = numero+"0";
                    }
                    mSecondaryDisplay.setText(numero);
                    mSecondaryDisplay2.setText(num2);
                }else{
                    mSecondaryDisplay2.setText(num2);
                }
            }else if(!numero.contains(".") && num2.contains(".")){
                Log.d("DEC","entro num no num2 si");
                String[] parti2 = num2.split("\\.");
                int numDec2 = parti2[1].length();
                numero = numero+".";
                for(int i=0; i< numDec2;i++){
                    numero = numero+"0";
                }
                mSecondaryDisplay.setText(numero);
                mSecondaryDisplay2.setText(num2);
            }else if(numero.contains(".") && !num2.contains(".")){
                String[] parti = numero.split("\\.");
                int numDec = parti[1].length();
                num2 = num2+".";
                for(int i=0; i< numDec;i++){
                    num2 = num2+"0";
                }
                mSecondaryDisplay2.setText(num2);
            }else if(!numero.contains(".") && !num2.contains(".")){
                Log.d("DEC", "entro");
                mSecondaryDisplay2.setText(mDisplay.getText().toString());
            }
            mOperatorDisplay2.setText(op);
        }else{
            mSecondaryDisplay.setText(mDisplay.getText().toString());
            numero = mDisplay.getText().toString();
            mOperatorDisplay.setText(op);
        }
        mDisplay.setText("");
    }

    private void updateDisplay(String digit) {
        if (equalJustPressed) {
            mDisplay.setText("");
            equalJustPressed = false;
        }
        mDisplay.setText(mDisplay.getText().toString()+digit);
    }

    public void calcola(View view){
        if(equalJustPressed){
            mSecondaryDisplay.setText("");
            mOperatorDisplay.setText("");
            mSecondaryDisplay2.setText("");
            mOperatorDisplay2.setText("");
            displayRis.setText("");
        }
            ImageButton button = (ImageButton) view;
            String x = button.getTag().toString();
            updateDisplay(x);
            leggi(x);


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
}


