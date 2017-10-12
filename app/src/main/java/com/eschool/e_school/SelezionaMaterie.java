package com.eschool.e_school;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelezionaMaterie.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelezionaMaterie#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelezionaMaterie extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // TODO: Rename and change types of parameters

    private String urlMteria = "http://www.eschooldb.altervista.org/PHP/getMaterieClassi.php";
    private RequestQueue requestQueue;
    private CheckBox[] mat;
    private LinearLayout linearMaterie;
    private OnFragmentInteractionListener mListener;

    public SelezionaMaterie() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelezionaMaterie.
     */
    // TODO: Rename and change types and number of parameters
    public static SelezionaMaterie newInstance(String param1, String param2) {
        SelezionaMaterie fragment = new SelezionaMaterie();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seleziona_materie, container, false);

        linearMaterie = (LinearLayout) view.findViewById(R.id.linearMaterie);
        requestQueue = Volley.newRequestQueue(getContext());

        connessione();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void connessione(){
        JsonRequest richiesta = new JsonRequest(Request.Method.POST, urlMteria, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray materie = response.getJSONArray("materie");
                    Log.v("LOG", "nomeMateria" +materie);
                    mat = new CheckBox[materie.length()];

                    for(int i=0; i<materie.length(); i++){
                        mat[i] = new CheckBox(getContext());
                        mat[i].setText(materie.getJSONObject(i).getString("nomeMateria"));
                        linearMaterie.addView(mat[i]);
                    }
                } catch (JSONException e) {
                    Log.v("LOG", "e"+e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("LOG", "error"+error.toString());
            }
        });
        requestQueue.add(richiesta);
    }
}
