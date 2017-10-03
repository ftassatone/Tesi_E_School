package com.eschool.e_school;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;

public class HomeDocente extends AppCompatActivity {

    private FragmentPagerAdapter adapterViewPager;
    private Button btHome;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /* Per la barra con la guida
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.barra_con_guida);
        actionBar.setDisplayShowCustomEnabled(true);
        ((TextView)findViewById(R.id.title)).setText("Seleziona tipologia esercizio");
        // ----------------------------------------*/

        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        // Per settare il colore della barra sotto al nome del tab
        PagerTabStrip pagerTabStrip = (PagerTabStrip)findViewById(R.id.pager_header);
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setTextSpacing(-200);
        // ---------------------------------------------------------

    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return FirstFragment.newInstance(position,"");
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0: return "Home";
                case 1: return "Aggiungi classe";
                case 2: return "Sezione diagnostica";
            }
            return "No name";
        }

    }

    public void vaiHomeDocente (View v){
        Log.d("DEBUG","cliccato color");
        startActivity(new Intent(getApplicationContext(),HomeDocente.class));
        finish();
    }

    public void vaiAggiungiClasse(View v){
        Log.d("DEBUG","cliccato memo");
        startActivity(new Intent(getApplicationContext(),AggiungiClasse.class));
        finish();
    }

    public void vaiSezDiagnostica(View v){
        Log.d("DEBUG","cliccato casa");
        startActivity(new Intent(getApplicationContext(),SezioneDiagnostica.class));
        finish();
    }

}


