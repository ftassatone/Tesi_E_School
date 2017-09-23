package com.eschool.e_school;

public class Materia {
    String codiceMateria;
    String desctizione;

    public Materia(String codiceMateria,String desctizione){
        this.codiceMateria = codiceMateria;
        this.desctizione = desctizione;
    }

    public String getCodiceMateria() {
        return codiceMateria;
    }

    public void setCodiceMateria(String codiceMateria) {
        this.codiceMateria = codiceMateria;
    }
}
