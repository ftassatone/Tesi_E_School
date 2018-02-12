package com.eschool.e_school.elementiBase;

public class Teoria {
    private int codiceTeoria;
    private String argomento,titolo,livello, codiceMateria,dataCreazione, file, nomeMateria;

    public Teoria(int codiceTeoria, String argomento, String titolo,String livello,String dataCreazione,
                  String codiceMateria, String fileTeoria, String nomeMateria) {
        this.codiceTeoria = codiceTeoria;
        this.argomento = argomento;
        this.titolo = titolo;
        this.livello = livello;
        this.codiceMateria = codiceMateria;
        this.dataCreazione = dataCreazione;
        this.file = fileTeoria;
        this.nomeMateria = nomeMateria;
    }
    public int getCodiceTeoria() {
        return codiceTeoria;
    }

    public void setCodiceTeoria(int codiceTeoria) {
        this.codiceTeoria = codiceTeoria;
    }

    public String getArgomento() {
        return argomento;
    }

    public void setArgomento(String argomento) {
        this.argomento = argomento;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getLivello() {
        return livello;
    }

    public void setLivello(String livello) {
        this.livello = livello;
    }

    public String getCodiceMateria() {
        return codiceMateria;
    }

    public void setCodiceMateria(String codiceMateria) {
        this.codiceMateria = codiceMateria;
    }

    public String getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(String dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public String getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "Teoria{" +
                "codiceTeoria=" + codiceTeoria +
                ", argomento='" + argomento + '\'' +
                ", titolo='" + titolo + '\'' +
                ", livello='" + livello + '\'' +
                ", codiceMateria='" + codiceMateria + '\'' +
                ", dataCreazione='" + dataCreazione + '\'' +
                ", file='" + file + '\'' +
                ", nomeMateria='" + nomeMateria + '\'' +
                '}';
    }
}
