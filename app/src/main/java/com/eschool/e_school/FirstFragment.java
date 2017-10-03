package com.eschool.e_school;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*public class FirstFragment extends Fragment {
        private int position;

    public static Fragment FirstFragment(int position) {
        FirstFragment f = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get data from Argument
        position = getArguments().getInt("position");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        switch (position){
            case 0:
                return inflater.inflate(R.layout.home_docente, container, false);
            case 1:
                return inflater.inflate(R.layout.aggiungi_classe, container, false);
            case 2:
                return inflater.inflate(R.layout.sezione_diagnostica, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setContentView();
    }

    private void setContentView() {
        if (position == 0) {
            //food fragment
            image.setImageResource(R.mipmap.food);
            content.setText("This is Food Fragment");
        } else if (position == 1) {
            //movie fragment
            image.setImageResource(R.mipmap.movie);
            content.setText("This is Movie Fragment");
        } else if (position == 2) {
            //shopping fragment
            image.setImageResource(R.mipmap.shopping);
            content.setText("This is Shopping Fragment");
        } else {
            //travel fragment
            image.setImageResource(R.mipmap.travel);
            content.setText("This is Travel Fragment");
        }
    }
}
   /* // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static FirstFragment newInstance(int page, String title) {
        FirstFragment fragmentFirst = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        switch(page){
            case 0:
                view = inflater.inflate(R.layout.home_docente, container, false);
                break;
            case 1:
                view = inflater.inflate(R.layout.aggiungi_classe,container,false);
                break;
            case 2:
                view = inflater.inflate(R.layout.sezione_diagnostica,container,false);
                break;
        }
        return view;
    }

}*/
