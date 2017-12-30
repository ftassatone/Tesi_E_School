package com.eschool.e_school;

public class Teoria {
    private int codiceTeoria;
    private String argomento,titolo,testo, livello, codiceMateria,dataCreazione, file;
    private Boolean sintetizzatore,microfono,riscontroLettura;

    public Teoria(int codiceTeoria, String argomento, String titolo, String testo, String livello, String codiceMateria, String dataCreazione, Boolean sintetizzatore, Boolean microfono, Boolean riscontroLettura,String file) {
        this.codiceTeoria = codiceTeoria;
        this.argomento = argomento;
        this.titolo = titolo;
        this.testo = testo;
        this.livello = livello;
        this.codiceMateria = codiceMateria;
        this.dataCreazione = dataCreazione;
        this.sintetizzatore = sintetizzatore;
        this.microfono = microfono;
        this.riscontroLettura = riscontroLettura;
        this.file = file;
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

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
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

    public Boolean getSintetizzatore() {
        return sintetizzatore;
    }

    public void setSintetizzatore(Boolean sintetizzatore) {
        this.sintetizzatore = sintetizzatore;
    }

    public Boolean getMicrofono() {
        return microfono;
    }

    public void setMicrofono(Boolean microfono) {
        this.microfono = microfono;
    }

    public Boolean getRiscontroLettura() {
        return riscontroLettura;
    }

    public void setRiscontroLettura(Boolean riscontroLettura) {
        this.riscontroLettura = riscontroLettura;
    }

    public String getFile() {
        return file;
    }


}
