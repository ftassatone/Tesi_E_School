package com.eschool.e_school;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //HomeDocenteFragment.//OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeDocenteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeDocenteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "matricola";
    private static final String ARG_PARAM2 = "param2";

    private String docente, radioMateria, radioClasse;
    private String url = "http://www.eschooldb.altervista.org/PHP/loginDocente.php";
    private RequestQueue requestQueue;
    private AlertDialog.Builder infoAlert;
    private RadioGroup rgClassi, rgMaterie;
    private Button btVaiClasse;
    private RadioButton[] rbMat,rbCl;
    private TextView txtBenvenuto;

    //private OnFragmentInteractionListener mListener;

    public HomeDocenteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param
     * @param
     * @return A new instance of fragment HomeDocenteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeDocenteFragment newInstance(String param) {
        HomeDocenteFragment fragment = new HomeDocenteFragment();
        Bundle args = new Bundle();
       args.putString(ARG_PARAM1, param);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            docente = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_docente, container, false);

        requestQueue = Volley.newRequestQueue(getContext());

        infoAlert = new AlertDialog.Builder(getContext());

        connessione();

        txtBenvenuto = (TextView) view.findViewById(R.id.txtBenvenuto);

        rgMaterie =(RadioGroup) view.findViewById(R.id.rgMaterie);
        rgClassi =(RadioGroup) view.findViewById(R.id.rgClassi);
        btVaiClasse = (Button) view.findViewById(R.id.btVaiClasse);

        btVaiClasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < rbMat.length; i++){
                    if(rbMat[i].isChecked()){
                        radioMateria = rbMat[i].getText().toString();
                        Log.v("LOG","mat "+ rbMat[i]);
                    }
                }

                for(int i = 0; i < rbCl.length; i++){
                    if(rbCl[i].isChecked()){
                        radioClasse = rbCl[i].getText().toString();
                    }
                }

                Intent vaiHomeClasse = new Intent(getContext(), HomeClasse.class);
                vaiHomeClasse.putExtra("Materia",radioMateria);
                vaiHomeClasse.putExtra("Classe",radioClasse);
                startActivity(vaiHomeClasse);
            }
        });

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    } */

    public void connessione() {
        HashMap<String, String> parametri = new HashMap<String, String>();
        parametri.put("matricola", docente);

        //richiesta di connessione al server
        JsonRequest richiesta = new JsonRequest(Request.Method.POST, url, parametri, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray materie = response.getJSONArray("materie");
                    rbMat = new RadioButton[materie.length()];

                    for (int i = 0; i < materie.length(); i++) {
                        rbMat[i] = new RadioButton(getContext());
                        rbMat[i].setText(materie.getJSONObject(i).getString("nomeMateria"));
                        rgMaterie.addView(rbMat[i]);
                    }

                    JSONArray classi = response.getJSONArray("classi");
                    rbCl = new RadioButton[classi.length()];
                    for (int i = 0; i < classi.length(); i++) {
                        rbCl[i] = new RadioButton(getContext());
                        rbCl[i].setText(classi.getJSONObject(i).getString("nomeClasse"));
                        rgClassi.addView(rbCl[i]);
                    }

                    JSONObject nomeDocente = response.getJSONObject("cognomeDoc");
                    txtBenvenuto.setText(getResources().getString(R.string.benvenuto)+" "+ nomeDocente.getString("cognome"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("LOG","errore: "+error.toString());
                infoAlert.setTitle("Errore di connessione");
                infoAlert.setMessage("Controllare connessione internet e riprovare.");
                AlertDialog alert = infoAlert.create();
                alert.show();
            }
        });
        requestQueue.add(richiesta);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home_docente,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_dati:
                // Not implemented here
                return false;
            default:
                break;
        }
        return false;
    }

}
