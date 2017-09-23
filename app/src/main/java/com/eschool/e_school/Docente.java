package com.eschool.e_school;

public class Docente {
    String matricola;
    String nome;
    String cognome;
    String cf;
    String dataNascita;
    String luogoNascita;
    String residenza;
    String numeroTelefono;
    String cellulare;
    String email;
    String password;

    public Docente (String matricola,String nome,String cognome, String cf, String dataNascita, String luogoNascita,
                    String residenza,String numeroTelefono, String cellulare, String email, String password){
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.cf = cf;
        this.dataNascita = dataNascita;
        this.luogoNascita = luogoNascita;
        this.residenza = residenza;
        this.numeroTelefono = numeroTelefono;
        this.cellulare = cellulare;
        this.email = email;
        this.password = password;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }
}
