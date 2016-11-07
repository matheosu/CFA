package br.edu.unigranrio.ect.si.cfa.web.action;

import java.io.Serializable;

public interface Action extends Serializable {

    String create();

    String save();

    String edit();

    String delete();

    String back();

    String list();

    boolean isManaged();

}
