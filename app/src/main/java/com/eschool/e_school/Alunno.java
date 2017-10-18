package com.eschool.e_school;

import android.os.Parcel;
import android.os.Parcelable;

public class Alunno implements Parcelable{
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
    //foto da caricare..
    String nomeClasse;

    public Alunno(String cf, String nome, String cognome, String dataNascita, String luogoNascita,
                  String residenza, String numeroTelefono, String cellulare, String email,
                  Boolean dsa,String username, String password,String nomeClasse){
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public void setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
    }

    public String getResidenza() {
        return residenza;
    }

    public void setResidenza(String residenza) {
        this.residenza = residenza;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getDsa() {
        return dsa;
    }

    public void setDsa(Boolean dsa) {
        this.dsa = dsa;
    }

    public String getNomeClasse() {
        return nomeClasse;
    }

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    private Alunno(Parcel in){
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        cf = in.readString();
        nome = in.readString();
        cognome = in.readString();
        dataNascita = in.readString();
        luogoNascita = in.readString();
        residenza = in.readString();
        numeroTelefono = in.readString();
        cellulare = in.readString();
        email = in.readString();
        dsa  = Boolean.valueOf(in.readString());
        username = in.readString();
        password = in.readString();
        nomeClasse = in.readString();
    }

    public static final Parcelable.Creator<Alunno> CREATOR = new Creator<Alunno>() {
        @Override
        public Alunno createFromParcel(Parcel parcel) {
            return new Alunno(parcel);
        }
        @Override
        public Alunno[] newArray(int i) {
            return new Alunno[i];
        }
    };

    public void writeToParcel(Parcel out, int flags){
        out.writeString(cf);
        out.writeString(nome);
        out.writeString(cognome);
        out.writeString(dataNascita);
        out.writeString(luogoNascita);
        out.writeString(residenza);
        out.writeString(numeroTelefono);
        out.writeString(cellulare);
        out.writeString(email);
        out.writeString(String.valueOf(dsa));
        out.writeString(username);
        out.writeString(password);
        out.writeString(nomeClasse);
    }

    public int describeContents() {
        return 0;
    }

}
