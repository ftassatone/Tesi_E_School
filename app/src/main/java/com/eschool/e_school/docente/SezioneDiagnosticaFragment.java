package com.eschool.e_school.docente;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.eschool.e_school.R;
import com.eschool.e_school.adapter.AdapterTestDiagnostici;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //SezioneDiagnosticaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SezioneDiagnosticaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SezioneDiagnosticaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listaTestLv1,listaTestLv2,listaTestLv3;

    //private OnFragmentInteractionListener mListener;

    public SezioneDiagnosticaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param
     * @param
     * @return A new instance of fragment SezioneDiagnosticaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SezioneDiagnosticaFragment newInstance() {
        SezioneDiagnosticaFragment fragment = new SezioneDiagnosticaFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sezione_diagnostica, container, false);
        listaTestLv1 = (ListView) view.findViewById(R.id.listaTestLv1);
        listaTestLv2 = (ListView) view.findViewById(R.id.listaTestLv2);
        listaTestLv3 = (ListView) view.findViewById(R.id.listaTestLv3);
        new RetriveFromAltervista2().execute();
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
        //mListener = null;
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


    public class RetriveFromAltervista2 extends AsyncTask<Void,Void,ArrayList<String>> {

        public  ArrayList<String> list,listLv1,listLv2,listLv3;
        private SezioneDiagnosticaFragment sezioneDiagnosticaFragment;
        FTPFile[] filesLv1 = new FTPFile[0];
        FTPFile[] filesLv2 = new FTPFile[0];
        FTPFile[] filesLv3 = new FTPFile[0];
        AdapterTestDiagnostici adapterTestDiagnostici,adapterTestDiagnostici2,adapterTestDiagnostici3;

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            list = new ArrayList<>();
            listLv1 = new ArrayList<>();
            listLv2 = new ArrayList<>();
            listLv3 = new ArrayList<>();
            FTPClient client = new FTPClient();
            try {
                //questo path lo ottieni da altervista se clicchi su connessione ftp, ti da tutte le informazioni li
                client.connect("ftp.eschooldb.altervista.org");
            } catch (IOException e) {
                e.printStackTrace();
            }
            client.enterLocalPassiveMode();
            try {
                //metti la tua login e password
                client.login("eschooldb", "robyfrancy");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Allora choise � un intero che passavo per vedere in quale delle due cartelle mi interessa andare a vedere, se conosci la path a prescindere
            //non � un problema
            try {
                filesLv1 = client.listFiles("File/diagnostica/livello1");
                Log.d("FILE", "lv1 "+filesLv1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                filesLv2 = client.listFiles("File/diagnostica/livello2");
                Log.d("FILE", "lv2 "+filesLv2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                filesLv3 = client.listFiles("File/diagnostica/livello3");
                Log.d("FILE", "lv3 "+filesLv3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //qui inserisco i nomi del file nell'arraylist di stringhe che poi restituisco e faccio visualizzare nella mia UI
            for (FTPFile file : filesLv1) {
                System.out.println(file.getName());
                //Log.d("MIOFILE", file.getLink().toString());
                list.add(file.getName());
                listLv1.add(file.getName());

                // FileOutputStream fos = new FileOutputStream("Ftp Files/" + file.getName());
                // client.retrieveFile(file.getName(), fos);
            }
            for (FTPFile file : filesLv2) {
                System.out.println(file.getName());
                Log.d("FileSequence", file.getName());
                list.add(file.getName());
                listLv2.add(file.getName());

                // FileOutputStream fos = new FileOutputStream("Ftp Files/" + file.getName());
                // client.retrieveFile(file.getName(), fos);
            }
            for (FTPFile file : filesLv3) {
                System.out.println(file.getName());
                list.add(file.getName());
                listLv3.add(file.getName());

                // FileOutputStream fos = new FileOutputStream("Ftp Files/" + file.getName());
                // client.retrieveFile(file.getName(), fos);
            }
            return list;
        }

        @Override
        public void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            adapterTestDiagnostici = new AdapterTestDiagnostici(getActivity(),listLv1);
            listaTestLv1.setAdapter(adapterTestDiagnostici);
            adapterTestDiagnostici2 = new AdapterTestDiagnostici(getActivity(),listLv2);
            listaTestLv2.setAdapter(adapterTestDiagnostici2);
            adapterTestDiagnostici3 = new AdapterTestDiagnostici(getActivity(),listLv3);
            listaTestLv3.setAdapter(adapterTestDiagnostici3);
        }
    }


}
