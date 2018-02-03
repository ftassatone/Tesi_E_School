package com.eschool.e_school.alunno;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eschool.e_school.R;

import java.util.Locale;

public class CalcolatriceParlante extends Activity implements TextToSpeech.OnInitListener {

    private TextView mDisplay;
    private TextView mSecondaryDisplay;
    private TextView mOperatorDisplay;
    private TextView mMemoryDisplay;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private Button mButton6;
    private Button mButton7;
    private Button mButton8;
    private Button mButton9;
    private Button mButton0;
    private Button mButtonPlus;
    private Button mButtonMinus;
    private Button mButtonMultiplication;
    private Button mButtonDivision;
    private Button mButtonDot;
    private Button mButtonEqual;
    private Button mButtonClear;
    private Button mButtonMemoryClear;
    private Button mButtonMemorySet;
    private Button mButtonMemoryRecall;

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
        mButtonPlus =           (Button)this.findViewById(R.id.ButtonPlus);
        mButtonMinus =          (Button)this.findViewById(R.id.ButtonMinus);
        mButtonMultiplication = (Button)this.findViewById(R.id.ButtonMultiplication);
        mButtonDivision =       (Button)this.findViewById(R.id.ButtonDivision);
        mButtonDot =            (Button)this.findViewById(R.id.ButtonDot);
        mButtonEqual =          (Button)this.findViewById(R.id.ButtonEqual);
        mButtonClear =          (Button)this.findViewById(R.id.ButtonClear);
        mButtonMemoryClear =    (Button)this.findViewById(R.id.ButtonMemoryClear);
        mButtonMemorySet =      (Button)this.findViewById(R.id.ButtonMemorySet);
        mButtonMemoryRecall =   (Button)this.findViewById(R.id.ButtonMemoryRecall);

        mButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButton1.getText().toString();
                updateDisplay('1');
                leggi(x);
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButton2.getText().toString();
                updateDisplay('2');
                leggi(x);
            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButton3.getText().toString();
                updateDisplay('3');
                leggi(x);
            }
        });
        mButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButton4.getText().toString();
                updateDisplay('4');
                leggi(x);
            }
        });
        mButton5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButton5.getText().toString();
                updateDisplay('5');
                leggi(x);
            }
        });
        mButton6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButton6.getText().toString();
                updateDisplay('6');
                leggi(x);
            }
        });
        mButton7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButton7.getText().toString();
                updateDisplay('7');
                leggi(x);
            }
        });
        mButton8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButton8.getText().toString();
                updateDisplay('8');
                leggi(x);
            }
        });
        mButton9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButton9.getText().toString();
                updateDisplay('9');
                leggi(x);
            }
        });
        mButton0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButton0.getText().toString();
                updateDisplay('0');
                leggi(x);
            }
        });

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
                leggi(x);
                mSecondaryDisplay.setText("");
                mOperatorDisplay.setText("");
                mDisplay.setText("");
            }
        });

        mButtonMemorySet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButtonMemorySet.getText().toString();
                leggi(x);
                mMemoryDisplay.setText(mDisplay.getText().toString());
            }
        });

        mButtonMemoryRecall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButtonMemoryRecall.getText().toString();
                leggi(x);
                mDisplay.setText(mMemoryDisplay.getText().toString());
            }
        });

        mButtonMemoryClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = mButtonMemoryClear.getText().toString();
                leggi(x);
                mMemoryDisplay.setText("");
            }
        });


    }
    private void updateDisplay(char digit) {
        if (equalJustPressed) {
            mDisplay.setText("");
            equalJustPressed = false;
        }
        mDisplay.setText(mDisplay.getText().toString()+digit);
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


