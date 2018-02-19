package com.eschool.e_school.alunno;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eschool.e_school.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EsercizioFragment extends Fragment {


    public EsercizioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_esercizio, container, false);
    }

}
