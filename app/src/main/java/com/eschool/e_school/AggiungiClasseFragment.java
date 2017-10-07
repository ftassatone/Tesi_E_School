package com.eschool.e_school;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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

    private EditText classe, sezione, nomeAlunno, cognomeAlunno, dataNascitaAlunno, cfAlunno, luogoNascitaAlunno, residenzaAlunno, telefonoAlunno,
            cellulareAlunno, emailAlunno, txtError;
    private Button btConfermaAlunno, btFine;
    private CheckBox opzDsaAlunno;
    private String classeTxt, sezioneTxt, nomeAlunnoTxt, cognomeAlunnoTxt, dataNascitaAlunnoTxt, cfAlunnoTxt, luogoNascitaAlunnoTxt, residenzaAlunnoTxt,
            telefonoAlunnoTxt, cellulareAlunnoTxt, emailAlunnoTxt;

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
        btConfermaAlunno = (Button) view.findViewById(R.id.btConfermaAlunno);
        btFine = (Button) view.findViewById(R.id.btFine);
        opzDsaAlunno = (CheckBox) view.findViewById(R.id.opzDsaAlunno);
        txtError = (EditText) view.findViewById(R.id.txtError);

        btConfermaAlunno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acquisizioneDati();

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
        classeTxt = classe.getText().toString();
        sezioneTxt = sezione.getText().toString();
        nomeAlunnoTxt = nomeAlunno.getText().toString();
        cognomeAlunnoTxt = cognomeAlunno.getText().toString();
        dataNascitaAlunnoTxt = dataNascitaAlunno.getText().toString();
        cfAlunnoTxt = cfAlunno.getText().toString();
        luogoNascitaAlunnoTxt = luogoNascitaAlunno.getText().toString();
        residenzaAlunnoTxt = residenzaAlunno.getText().toString();
        telefonoAlunnoTxt = telefonoAlunno.getText().toString();
        cellulareAlunnoTxt = cellulareAlunno.getText().toString();
        emailAlunnoTxt = emailAlunno.getText().toString();

    }

    public void aggiungiNuovoAlunno(){
        //raccolgo i dati inseriti dall'utente
        HashMap<String, String> parametri = new HashMap<String, String>();

        parametri.put("cf", cfAlunnoTxt);
        parametri.put("nome", nomeAlunnoTxt);
        parametri.put("cognome", cognomeAlunnoTxt);
        parametri.put("dataNascita", dataNascitaAlunnoTxt);
        parametri.put("luogoNascita", luogoNascitaAlunnoTxt);
        parametri.put("residenza", residenzaAlunnoTxt);
        parametri.put("numeroTelefono", telefonoAlunnoTxt);
        parametri.put("cellulare", cellulareAlunnoTxt);
        parametri.put("email", emailAlunnoTxt);
        //dsa
        parametri.put("nomeClasse", classeTxt+sezioneTxt);
    }


}
