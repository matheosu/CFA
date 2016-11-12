package br.edu.unigranrio.ect.si.cfa.model;

public enum Period {

    DAY("Dia"),
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
