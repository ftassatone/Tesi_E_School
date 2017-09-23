package com.eschool.e_school;

import java.io.StringReader;


public class Alunno {
    String cf;
    String nome;
    String cognome;
    String dataNascita;
    String luogoNascita;
    String residenza;
    String numeroTelefono;
    String cellulare;
    String email;
    Boolean dsa;
    String username;
    String password;
    //foto
    String nomeClasse;

    public Alunno(String cf, String nome, String cognome, String dataNascita, String luogoNascita,
                  String residenza, String numeroTelefono, String cellulare, String email,
                  Boolean dsa, String username, String password, String nomeClasse){
        this.cf = cf;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.luogoNascita = luogoNascita;
        this.residenza = residenza;
        this.numeroTelefono = numeroTelefono;
        this.cellulare = cellulare;
        this.email = email;
        this.dsa = dsa;
        this.username = username;
        this.password = password;
        this.nomeClasse = nomeClasse;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }
}
