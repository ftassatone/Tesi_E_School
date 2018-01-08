package com.eschool.e_school.elementiBase;

public class Esercizio {
    private int codiceEsercizio,voto,numeroErrori, codiceTeoria;
    private String descrizione,opzioneRisposta, livello, dataCreazione;
    private Boolean sintetizzatore,microfono, riscontroLettura;

    public Esercizio(int codiceEsercizio, int voto, int numeroErrori, int codiceTeoria, String descrizione, String livello, String opzioneRisposta, String dataCreazione, Boolean sintetizzatore, Boolean riscontroLettura, Boolean microfono) {
        this.codiceEsercizio = codiceEsercizio;
        this.voto = voto;
        this.numeroErrori = numeroErrori;
        this.codiceTeoria = codiceTeoria;
        this.descrizione = descrizione;
        this.livello = livello;
        this.opzioneRisposta = opzioneRisposta;
        this.dataCreazione = dataCreazione;
        this.sintetizzatore = sintetizzatore;
        this.riscontroLettura = riscontroLettura;
        this.microfono = microfono;
    }

    public int getCodiceEsercizio() {
        return codiceEsercizio;
    }

    public void setCodiceEsercizio(int codiceEsercizio) {
        this.codiceEsercizio = codiceEsercizio;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public int getNumeroErrori() {
        return numeroErrori;
    }

    public void setNumeroErrori(int numeroErrori) {
        this.numeroErrori = numeroErrori;
    }

    public int getCodiceTeoria() {
        return codiceTeoria;
    }

    public void setCodiceTeoria(int codiceTeoria) {
        this.codiceTeoria = codiceTeoria;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getOpzioneRisposta() {
        return opzioneRisposta;
    }

    public void setOpzioneRisposta(String opzioneRisposta) {
        this.opzioneRisposta = opzioneRisposta;
    }

    public String getLivello() {
        return livello;
    }

    public void setLivello(String livello) {
        this.livello = livello;
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

    @Override
    public String toString() {
        return "Esercizio{" +
                "codiceEsercizio=" + codiceEsercizio +
                ", voto=" + voto +
                ", numeroErrori=" + numeroErrori +
                ", codiceTeoria=" + codiceTeoria +
                ", descrizione='" + descrizione + '\'' +
                ", opzioneRisposta='" + opzioneRisposta + '\'' +
                ", livello='" + livello + '\'' +
                ", dataCreazione='" + dataCreazione + '\'' +
                ", sintetizzatore=" + sintetizzatore +
                ", microfono=" + microfono +
                ", riscontroLettura=" + riscontroLettura +
                '}';
    }
}
