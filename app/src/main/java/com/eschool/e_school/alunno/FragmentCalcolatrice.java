package com.eschool.e_school.alunno;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eschool.e_school.R;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentCalcolatrice.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentCalcolatrice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCalcolatrice extends Fragment implements TextToSpeech.OnInitListener {


    private TextView mDisplay, mSecondaryDisplay,mOperatorDisplay,mMemoryDisplay, mSecondaryDisplay2,mOperatorDisplay2,displayRis;
    private ImageButton mButtonPlus,mButton1,mButton2,mButton3,mButton4,mButton5,mButton6,mButton7,mButton8,mButton9,mButton0, mButtonMinus, mButtonMultiplication,mButtonDivision, mButtonDot, mButtonEqual,mButtonClear,
            mButtonMemoryClear,mButtonMemorySet,mButtonMemoryRecall;
    private Boolean equalJustPressed = false;
    private TextToSpeech tts;
    private String numero;

    public FragmentCalcolatrice() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_calcolatrice, container, false);
        tts = new TextToSpeech(getContext(), this);
        mDisplay = view.findViewById(R.id.calcdisplay);
        mSecondaryDisplay = view.findViewById(R.id.secondarydisplay);
        mOperatorDisplay = view.findViewById(R.id.operatordisplay);
        mSecondaryDisplay2 = view.findViewById(R.id.secondarydisplay2);
        mOperatorDisplay2 = view.findViewById(R.id.operatordisplay2);
        mMemoryDisplay = view.findViewById(R.id.memorydisplay);
        displayRis = view.findViewById(R.id.displayRis);

        mButton1 = view.findViewById(R.id.Button1);
        mButton2 = view.findViewById(R.id.Button2);
        mButton3 = view.findViewById(R.id.Button3);
        mButton4 = view.findViewById(R.id.Button4);
        mButton5 = view.findViewById(R.id.Button5);
        mButton6 = view.findViewById(R.id.Button6);
        mButton7 = view.findViewById(R.id.Button7);
        mButton8 = view.findViewById(R.id.Button8);
        mButton9 = view.findViewById(R.id.Button9);
        mButton0 = view.findViewById(R.id.Button0);
        mButtonPlus = view.findViewById(R.id.ButtonPlus);
        mButtonMinus = view.findViewById(R.id.ButtonMinus);
        mButtonMultiplication = view.findViewById(R.id.ButtonMolt);
        mButtonDivision = view.findViewById(R.id.ButtonDiv);
        mButtonDot = view.findViewById(R.id.ButtonDot);
        mButtonEqual = view.findViewById(R.id.ButtonEqual);
        mButtonClear = view.findViewById(R.id.ButtonClear);
        mButtonMemoryClear = view.findViewById(R.id.ButtonMemoryClear);
        mButtonMemorySet = view.findViewById(R.id.ButtonMemorySet);
        mButtonMemoryRecall = view.findViewById(R.id.ButtonMemoryRecall);

        mButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcola(view);
            }
        });

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcola(view);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcola(view);
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcola(view);
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcola(view);
            }
        });

        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcola(view);
            }
        });

        mButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcola(view);
            }
        });

        mButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcola(view);
            }
        });

        mButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcola(view);
            }
        });

        mButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcola(view);
            }
        });

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

        return  view;
    }

    // ARROTONDAMENTO CLASSICO
    public static float arrotonda(float value, int numCifreDecimali) {
        float temp = (float) Math.pow(10, numCifreDecimali);
        return Math.round(value * temp) / temp;
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
            ImageButton button = (ImageButton) view;
            String x = button.getTag().toString();
            updateDisplay(x);
            leggi(x);
        }
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
