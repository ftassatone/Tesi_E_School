package com.eschool.e_school.elementiBase;

public class Classe {

    private String nomeClasse;

    public Classe (String nomeClasse){
        this.nomeClasse = nomeClasse;
    }

    public String getNomeClasse() {
        return nomeClasse;
    }

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    @Override
    public String toString() {
        return "Classe{" +
                "nomeClasse='" + nomeClasse + '\'' +
                '}';
    }
}

