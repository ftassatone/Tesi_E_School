package com.eschool.e_school.docente;

import java.io.Serializable;

public class Nodo implements Serializable {
    private String titolo;
    private String descrizione;
    private int id;
    private String titoloMappa;
    private int seq;

    public Nodo(int id, String titoloMappa, String titolo, String descrizione, int seq){
        this.id = id;
        this.titoloMappa = titoloMappa;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.seq = seq;

    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitoloMappa() {
        return titoloMappa;
    }

    public void setTitoloMappa(String titoloMappa) {
        this.titoloMappa = titoloMappa;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "Nodo{" +
                "titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", id=" + id +
                ", titoloMappa='" + titoloMappa + '\'' +
                ", seq=" + seq +
                '}';
    }
}


