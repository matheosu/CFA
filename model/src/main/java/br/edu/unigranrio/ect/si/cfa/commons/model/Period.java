package br.edu.unigranrio.ect.si.cfa.commons.model;

public enum Period {

    DAY("Dia"),
    WEEK("Semana"),
    MONTH("Mês"),
    YEAR("Ano"),
    ;

    private String description;

    Period(String description){
        this.description =  description;
    }

    public String getDescription(){
        return description;
    }

}
