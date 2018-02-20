package com.eschool.e_school.altervista;

import android.content.Context;

import java.io.File;

public class ParametriAltervista{
    private File f;
    private Context context;
    private String materia,classe, argomento;
    public ParametriAltervista(Context context,File f, String materia, String classe,String argomento) {
        this.context = context;
        this.f = f;
        this.materia = materia;
        this.classe = classe;
        this.argomento = argomento;
    }

    public File getF() {
        return f;
    }

    public String getClasse() {
        return classe;
    }

    public String getArgomento() {
        return argomento;
    }

    public String getMateria() {
        return materia;
    }

    public Context getContext() {
        return context;
    }
}
