package com.eschool.e_school;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //AggiungiClasseFragment.//OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AggiungiClasseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AggiungiClasseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    /*TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;*/

    //private OnFragmentInteractionListener mListener;
    private String url = "http://www.eschooldb.altervista.org/PHP/aggiungiClasse.php";

    private EditText classe, sezione, nomeAlunno, cognomeAlunno, dataNascitaAlunno, cfAlunno, luogoNascitaAlunno, residenzaAlunno, telefonoAlunno,
            cellulareAlunno, emailAlunno, txtError, username, password, confermaPsw;
    private Button btNuovoAlunno, btFine;
    private CheckBox opzDsaAlunno;
    private String classeTxt, nomeAlunnoTxt, cognomeAlunnoTxt, dataNascitaAlunnoTxt, cfAlunnoTxt, luogoNascitaAlunnoTxt, residenzaAlunnoTxt,
            telefonoAlunnoTxt, cellulareAlunnoTxt, emailAlunnoTxt, usernameTxt, passwordTxt;
    private boolean opzioneDsa =false;
    private ArrayList<Alunno> listaAlunni;
    private Classe cl;
    private RequestQueue requestQueue;

    public AggiungiClasseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment AggiungiClasseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AggiungiClasseFragment newInstance() {
        AggiungiClasseFragment fragment = new AggiungiClasseFragment();
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

        requestQueue = Volley.newRequestQueue(getContext());

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
        txtError = (EditText) view.findViewById(R.id.txtError);
        username = (EditText) view.findViewById(R.id.userAlunno);
        password = (EditText) view.findViewById(R.id.pswAlunno);
        confermaPsw = (EditText) view.findViewById(R.id.confermaPswAlunno);

        listaAlunni = new ArrayList<>();

        btNuovoAlunno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acquisizioneDati();


            }
        });
        btFine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acquisizioneDati();
                sezione.setText("");
                classe.setText("");
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/

    private void acquisizioneDati(){
        Log.v("LOG", "sono in acquisizioneDati");
        //per il primo inserimento mi salvo la classe
        if(classe.getText() != null || sezione.getText() != null){
            classeTxt = classe.getText().toString()+sezione.getText().toString();

            cl = new Classe(classeTxt);
        }

        if(nomeAlunno.getText() != null  || cognomeAlunno.getText() != null || dataNascitaAlunno.getText() != null
                || cfAlunno.getText() != null || luogoNascitaAlunno.getText() != null || residenzaAlunno.getText() != null || telefonoAlunno.getText() != null
                || cellulareAlunno.getText() != null || emailAlunno.getText() != null){

            nomeAlunnoTxt = nomeAlunno.getText().toString();
            cognomeAlunnoTxt = cognomeAlunno.getText().toString();
            dataNascitaAlunnoTxt = dataNascitaAlunno.getText().toString();
            cfAlunnoTxt = cfAlunno.getText().toString();
            luogoNascitaAlunnoTxt = luogoNascitaAlunno.getText().toString();
            residenzaAlunnoTxt = residenzaAlunno.getText().toString();
            telefonoAlunnoTxt = telefonoAlunno.getText().toString();
            cellulareAlunnoTxt = cellulareAlunno.getText().toString();
            emailAlunnoTxt = emailAlunno.getText().toString();

            if(opzDsaAlunno.isChecked()) {
                opzioneDsa = true;
            }

            usernameTxt = username.getText().toString();

            if(password.getText().toString().equals(confermaPsw.getText().toString())){
                passwordTxt = password.getText().toString();
            }else{
                Toast.makeText(getContext(),"Le password non coincidono.", Toast.LENGTH_LONG).show();
            }

            //TODO si deve acquisire anche foto
            Alunno al = new Alunno(cfAlunnoTxt, nomeAlunnoTxt, cognomeAlunnoTxt, dataNascitaAlunnoTxt, luogoNascitaAlunnoTxt, residenzaAlunnoTxt, telefonoAlunnoTxt,
                    cellulareAlunnoTxt, emailAlunnoTxt,opzioneDsa, usernameTxt, passwordTxt,classeTxt);
            Log.v("LOG", "alunno: "+al.toString());
            listaAlunni.add(al);

            pulizia();

            //invia i dati
            aggiungiSetAlunni();

        }else{
            Toast.makeText(getContext(), "Inserire i campi obbligatori.", Toast.LENGTH_LONG).show();
        }
    }

    public void aggiungiSetAlunni() {
        Log.v("LOG", "sono in aggiungi");
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
                Log.v("LOG","errore-"+error.toString());
            }
        });
        requestQueue.add(richiesta);
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

}
