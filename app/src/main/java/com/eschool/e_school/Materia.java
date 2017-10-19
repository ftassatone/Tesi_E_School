package com.eschool.e_school;

public class Materia {
    private String codiceMateria;
    private String desctizione;

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

    public String getDesctizione() {
        return desctizione;
    }

    public void setDesctizione(String desctizione) {
        this.desctizione = desctizione;
    }

    @Override
    public String toString() {
        return "Materia{" +
                "codiceMateria='" + codiceMateria + '\'' +
                ", desctizione='" + desctizione + '\'' +
                '}';
    }
}
