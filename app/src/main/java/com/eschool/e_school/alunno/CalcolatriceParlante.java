package com.eschool.e_school.alunno;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eschool.e_school.R;

import java.util.Locale;

public class CalcolatriceParlante extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextView mDisplay, mSecondaryDisplay,mOperatorDisplay,mMemoryDisplay;
    private Button mButton1,mButton2,mButton3,mButton4,mButton5,mButton6,mButton7,mButton8,mButton9,mButton0,mButtonPlus, mButtonMinus,
            mButtonMultiplication,mButtonDivision, mButtonDot, mButtonEqual,mButtonClear,mButtonMemoryClear,mButtonMemorySet,mButtonMemoryRecall;

    private Boolean equalJustPressed = false;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calcolatrice_parlante);

        tts = new TextToSpeech(getApplicationContext(), this);


        mDisplay = (TextView)findViewById(R.id.calcdisplay);
        mSecondaryDisplay = (TextView)findViewById(R.id.secondarydisplay);
        mOperatorDisplay = (TextView)findViewById(R.id.operatordisplay);
        mMemoryDisplay = (TextView)findViewById(R.id.memorydisplay);

        mButton1 = (Button)this.findViewById(R.id.Button1);
        mButton2 = (Button)this.findViewById(R.id.Button2);
        mButton3 = (Button)this.findViewById(R.id.Button3);
        mButton4 = (Button)this.findViewById(R.id.Button4);
        mButton5 = (Button)this.findViewById(R.id.Button5);
        mButton6 = (Button)this.findViewById(R.id.Button6);
        mButton7 = (Button)this.findViewById(R.id.Button7);
        mButton8 = (Button)this.findViewById(R.id.Button8);
        mButton9 = (Button)this.findViewById(R.id.Button9);
        mButton0 = (Button)this.findViewById(R.id.Button0);
        mButtonPlus = (Button)this.findViewById(R.id.ButtonPlus);
        mButtonMinus = (Button)this.findViewById(R.id.ButtonMinus);
        mButtonMultiplication = (Button)this.findViewById(R.id.ButtonMultiplication);
        mButtonDivision = (Button)this.findViewById(R.id.ButtonDivision);
        mButtonDot = (Button)this.findViewById(R.id.ButtonDot);
        mButtonEqual = (Button)this.findViewById(R.id.ButtonEqual);
        mButtonClear =          (Button)this.findViewById(R.id.ButtonClear);
        mButtonMemoryClear =    (Button)this.findViewById(R.id.ButtonMemoryClear);
        mButtonMemorySet =      (Button)this.findViewById(R.id.ButtonMemorySet);
        mButtonMemoryRecall =   (Button)this.findViewById(R.id.ButtonMemoryRecall);

        mButtonDot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButtonDot.getText().toString();
                leggi(x);
                if (mDisplay.getText().toString().contains(".")) {
                    //do nothing
                } else {
                    mDisplay.setText(mDisplay.getText().toString()+".");
                }
            }
        });

        mButtonPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButtonPlus.getText().toString();
                leggi(x);
                mSecondaryDisplay.setText(mDisplay.getText().toString());
                mOperatorDisplay.setText("+");
                mDisplay.setText("");
            }
        });

        mButtonMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButtonMinus.getText().toString();
                leggi(x);
                mSecondaryDisplay.setText(mDisplay.getText().toString());
                mOperatorDisplay.setText("-");
                mDisplay.setText("");
            }
        });

        mButtonMultiplication.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButtonMultiplication.getText().toString();
                leggi(x);
                mSecondaryDisplay.setText(mDisplay.getText().toString());
                mOperatorDisplay.setText("*");
                mDisplay.setText("");
            }
        });

        mButtonDivision.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButtonDivision.getText().toString();
                leggi(x);
                mSecondaryDisplay.setText(mDisplay.getText().toString());
                mOperatorDisplay.setText("/");
                mDisplay.setText("");
            }
        });

        mButtonEqual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButtonEqual.getText().toString();
                leggi(x);
                float op1 = Float.parseFloat(mSecondaryDisplay.getText().toString());
                float op2 = Float.parseFloat(mDisplay.getText().toString());
                float res = 0;
                switch (mOperatorDisplay.getText().toString().charAt(0)) {
                    case '+': res = op1 + op2;leggi(String.valueOf(res)); break;
                    case '-': res = op1 - op2;leggi(String.valueOf(res)); break;
                    case '*': res = op1 * op2;leggi(String.valueOf(res)); break;
                    case '/': res = op1 / op2;leggi(String.valueOf(res)); break;
                    default: //should nevere be here!
                }
                mSecondaryDisplay.setText("");
                mOperatorDisplay.setText("");
                mDisplay.setText(Float.toString(res));
                equalJustPressed = true;
            }
        });

        mButtonClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButtonClear.getText().toString();
                leggi("Cancella numero");
                mSecondaryDisplay.setText("");
                mOperatorDisplay.setText("");
                mDisplay.setText("");
            }
        });

        mButtonMemorySet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButtonMemorySet.getText().toString();
                leggi("salva in memoria");
                mMemoryDisplay.setText(mDisplay.getText().toString());
            }
        });

        mButtonMemoryRecall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButtonMemoryRecall.getText().toString();
                leggi("Richiama numero");
                mDisplay.setText(mMemoryDisplay.getText().toString());
            }
        });

        mButtonMemoryClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButtonMemoryClear.getText().toString();
                leggi("Cancella memoria");
                mMemoryDisplay.setText("");
            }
        });


    }
    private void updateDisplay(String digit) {
        if (equalJustPressed) {
            mDisplay.setText("");
            equalJustPressed = false;
        }
        mDisplay.setText(mDisplay.getText().toString()+digit);
    }

    public void calcola(View view){
        Button button = (Button) view;
        String x = button.getText().toString();
        updateDisplay(x);
        leggi(x);
    }


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


