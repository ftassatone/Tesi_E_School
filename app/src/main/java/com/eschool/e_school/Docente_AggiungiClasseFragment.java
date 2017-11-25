package com.eschool.e_school;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //AggiungiClasseFragment.//OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Docente_AggiungiClasseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Docente_AggiungiClasseFragment extends Fragment {

    private String url = "http://www.eschooldb.altervista.org/PHP/addClass.php";
    private String urlControllo = "http://www.eschooldb.altervista.org/PHP/controlloAggiungiClasse.php";
    private EditText classe, sezione, nomeAlunno, cognomeAlunno, dataNascitaAlunno, cfAlunno, luogoNascitaAlunno, residenzaAlunno, telefonoAlunno,
            cellulareAlunno, emailAlunno, username, password, confermaPsw;
    private TextView txtError;
    private Button btNuovoAlunno, btFine;
    private CheckBox opzDsaAlunno;
    private String classeTxt, nomeAlunnoTxt, cognomeAlunnoTxt, dataNascitaAlunnoTxt, cfAlunnoTxt, luogoNascitaAlunnoTxt, residenzaAlunnoTxt,
            telefonoAlunnoTxt, cellulareAlunnoTxt, emailAlunnoTxt, usernameTxt, passwordTxt,pswCifrata;
    private boolean opzioneDsa =false, newAlunno=true;
    private ArrayList<Alunno> listaAlunni;
    private Classe cl;
    private AlertDialog.Builder infoAlert;

    public Docente_AggiungiClasseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment AggiungiClasseFragment.
     */

    public static Docente_AggiungiClasseFragment newInstance() {
        Docente_AggiungiClasseFragment fragment = new Docente_AggiungiClasseFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_aggiungi_classe, container, false);
        infoAlert = new AlertDialog.Builder(getContext());

        classe = (EditText) view.findViewById(R.id.classe);
        sezione = (EditText) view.findViewById(R.id.sezione);
        nomeAlunno = (EditText) view.findViewById(R.id.nomeAlunno);
        cognomeAlunno = (EditText) view.findViewById(R.id.cognomeAlunno);
        dataNascitaAlunno = (EditText) view.findViewById(R.id.dataNascitaAlunno);
        cfAlunno = (EditText) view.findViewById(R.id.cfAlunno);
        luogoNascitaAlunno = (EditText) view.findViewById(R.id.luogoNascitaAlunno);
        residenzaAlunno = (EditText) view.findViewById(R.id.residenzaAlunno);
        telefonoAlunno = (EditText) view.findViewById(R.id.telefonoAlunno);
        cellulareAlunno = (EditText) view.findViewById(R.id.cellulareAlunno);
        emailAlunno = (EditText) view.findViewById(R.id.emailAlunno);
        btNuovoAlunno = (Button) view.findViewById(R.id.btNuovoAlunno);
        btFine = (Button) view.findViewById(R.id.btFine);
        opzDsaAlunno = (CheckBox) view.findViewById(R.id.opzDsaAlunno);
        txtError = (TextView) view.findViewById(R.id.txtError);
        username = (EditText) view.findViewById(R.id.userAlunno);
        password = (EditText) view.findViewById(R.id.pswAlunno);
        confermaPsw = (EditText) view.findViewById(R.id.confermaPswAlunno);

        listaAlunni = new ArrayList<>();

        btNuovoAlunno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(acquisizineDati()){
                    new Controllo().execute();
                }
            }
        });

        btFine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newAlunno = false;
                if(acquisizineDati()){
                    new Controllo().execute();
                }
            }
        });

        return view;
    }

    public void onButtonPressed(Uri uri) {
       /* if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.

     public interface OnFragmentInteractionListener {
     void onFragmentInteraction(Uri uri);
     }*/
    private class Controllo extends AsyncTask<Void,Void,Void>{
        JSONObject risposta;
        boolean controlloCf=true,controlloUser=true;
        @Override
        protected Void doInBackground(Void... voids) {

            HashMap<String,String> param = new HashMap<>();
            param.put("cf",cfAlunnoTxt);
            param.put("username",usernameTxt);
            RequestFuture<JSONObject> future = RequestFuture.newFuture();

            JsonRequest richiestaControllo = new JsonRequest(Request.Method.POST,  urlControllo, param, future, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestSingleton.getInstance(getContext()).addToRequestQueue(richiestaControllo);
            try {
                risposta=future.get(5, TimeUnit.SECONDS);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            } catch (ExecutionException e1) {
                e1.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("DATI","risposta+"+risposta);
            try {
                if(risposta.getString("rispostaCf").equals("false")){
                     controlloCf = false;
                }else if(risposta.getString("rispostaUser").equals("false")){
                    controlloUser = false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(controlloDati() && controlloCf && controlloUser){
                Alunno al = new Alunno(cfAlunnoTxt, nomeAlunnoTxt, cognomeAlunnoTxt, dataNascitaAlunnoTxt, luogoNascitaAlunnoTxt, residenzaAlunnoTxt, telefonoAlunnoTxt,
                        cellulareAlunnoTxt, emailAlunnoTxt,opzioneDsa, usernameTxt, pswCifrata,classeTxt);
                Log.v("DATI", "alunno: "+al.toString());
                listaAlunni.add(al);
                pulizia();

                if(!newAlunno){
                    aggiungiSetAlunni();
                    sezione.setText("");
                    classe.setText("");
                    Intent home = new Intent(getContext(), Docente_Home.class);
                    home.putExtra("username", Docente_Home.DOC);
                    startActivity(home);
                }
            }else if(!controlloCf){
                cfAlunno.setError("Codice fiscale già esistente.");
                cfAlunno.setTextColor(Color.RED);
                cfAlunno.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        cfAlunno.setTextColor(Color.BLACK);
                        cfAlunno.setError(null);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }else if(!controlloUser){
                username.setError("Username già esistente.");
                username.setTextColor(Color.RED);
                username.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        username.setTextColor(Color.BLACK);
                        username.setError(null);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        }
    }

    private boolean controlloDati(){
       if(cfAlunnoTxt.length() != 16){
           cfAlunno.setTextColor(Color.RED);
           cfAlunno.setError("Il codice fiscale deve essere di 16 caratteri.");
           cfAlunno.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               }

               @Override
               public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                   cfAlunno.setTextColor(Color.BLACK);
                   cfAlunno.setError(null);
               }

               @Override
               public void afterTextChanged(Editable editable) {
               }
           });
           return false;
       }

        if(cellulareAlunnoTxt.length() != 10){
            cellulareAlunno.setTextColor(Color.RED);
            cellulareAlunno.setError("Il numero deve essere di 10 cifre.");
            cellulareAlunno.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    cellulareAlunno.setTextColor(Color.BLACK);
                    cellulareAlunno.setError(null);
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            return false;
        }

        if(telefonoAlunnoTxt.length() != 10){
            telefonoAlunno.setTextColor(Color.RED);
            telefonoAlunno.setError("Il numero deve essere di 10 cifre.");
            telefonoAlunno.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    telefonoAlunno.setTextColor(Color.BLACK);
                    telefonoAlunno.setError(null);
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            return false;
        }
        if(password.getText().toString().equals(confermaPsw.getText().toString())){
            passwordTxt = password.getText().toString();
            pswCifrata = MyCript.encrypt(passwordTxt);
        }else{
            password.setTextColor(Color.RED);
            password.setError("Le password non coincidono");
            password.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    password.setTextColor(Color.BLACK);
                    password.setError(null);
                    confermaPsw.setTextColor(Color.BLACK);
                    confermaPsw.setError(null);
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            confermaPsw.setTextColor(Color.RED);
            confermaPsw.setError("Le password non coincidono");
            confermaPsw.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    password.setTextColor(Color.BLACK);
                    password.setError(null);
                    confermaPsw.setTextColor(Color.BLACK);
                    confermaPsw.setError(null);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            txtError.setTextColor(Color.RED);
            return false;
        }
        return true;
    }

     public void aggiungiSetAlunni() {
        HashMap<String, String> parametri = new HashMap<>();

        Gson lista = new Gson();
        Gson classe = new Gson();
        parametri.put("arrayAlunni", lista.toJson(listaAlunni));
        parametri.put("classe", classe.toJson(cl));

        Log.v("LOG", "arrayAlunno-"+lista.toJson(listaAlunni));
        Log.v("LOG", "classe-"+classe.toJson(cl));

        JsonRequest richiesta = new JsonRequest(Request.Method.POST, url, parametri, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(getContext(),response.getString("messaggio") , Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                infoAlert.setTitle("Errore di connessione");
                infoAlert.setMessage("Controllare connessione internet e riprovare.");
                AlertDialog alert = infoAlert.create();
                alert.show();
            }
        });
        RequestSingleton.getInstance(getContext()).addToRequestQueue(richiesta);
    }

    private void pulizia(){
        nomeAlunno.setText("");
        cognomeAlunno.setText("");
        dataNascitaAlunno.setText("");
        cfAlunno.setText("");
        luogoNascitaAlunno.setText("");
        residenzaAlunno.setText("");
        telefonoAlunno.setText("");
        cellulareAlunno.setText("");
        emailAlunno.setText("");
        password.setText("");
        username.setText("");
        confermaPsw.setText("");
        opzDsaAlunno.setChecked(false);
    }

    //acquisisco tutti i dati
    private boolean acquisizineDati() {
        if (!classe.getText().toString().equals("") && !sezione.getText().toString().equals("")) {
            classeTxt = classe.getText().toString() + sezione.getText().toString().toUpperCase();
            cl = new Classe(classeTxt);

            if (!nomeAlunno.getText().toString().equals("") && !cognomeAlunno.getText().toString().equals("") && !dataNascitaAlunno.getText().toString().equals("")
                    && !cfAlunno.getText().toString().equals("") && !luogoNascitaAlunno.getText().toString().equals("") && !residenzaAlunno.getText().toString().equals("") && !telefonoAlunno.getText().toString().equals("")
                    && !cellulareAlunno.getText().toString().equals("") && !emailAlunno.getText().toString().equals("")) {

                nomeAlunnoTxt = nomeAlunno.getText().toString();
                cognomeAlunnoTxt = cognomeAlunno.getText().toString();
                dataNascitaAlunnoTxt = dataNascitaAlunno.getText().toString();
                cfAlunnoTxt = cfAlunno.getText().toString().trim();
                luogoNascitaAlunnoTxt = luogoNascitaAlunno.getText().toString();
                residenzaAlunnoTxt = residenzaAlunno.getText().toString();
                telefonoAlunnoTxt = telefonoAlunno.getText().toString();
                cellulareAlunnoTxt = cellulareAlunno.getText().toString();
                emailAlunnoTxt = emailAlunno.getText().toString();
                usernameTxt = username.getText().toString();

                if (opzDsaAlunno.isChecked()) {
                    opzioneDsa = true;
                }
            }else {
                txtError.setTextColor(Color.RED);
                return false;
            }
        }else{
            txtError.setTextColor(Color.RED);
            return false;
        }
        txtError.setTextColor(Color.BLACK);
        return true;
    }
}
