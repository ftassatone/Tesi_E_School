package com.eschool.e_school;

public class Docente {
    private String matricola;
    private String nome;
    private String cognome;
    private String cf;
    private String dataNascita;
    private String luogoNascita;
    private String residenza;
    private String numeroTelefono;
    private String cellulare;
    private String email;
    private String password;
    private byte[] pswCifrata;

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

    public Docente (String matricola,String nome,String cognome, String cf, String dataNascita, String luogoNascita,
                    String residenza,String numeroTelefono, String cellulare, String email, byte[] password){
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
        this.pswCifrata = password;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    @Override
    public String toString() {
        return "Docente{" +
                "matricola='" + matricola + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", cf='" + cf + '\'' +
                ", dataNascita='" + dataNascita + '\'' +
                ", luogoNascita='" + luogoNascita + '\'' +
                ", residenza='" + residenza + '\'' +
                ", numeroTelefono='" + numeroTelefono + '\'' +
                ", cellulare='" + cellulare + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
