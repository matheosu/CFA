package br.edu.unigranrio.ect.si.cfa.web;

import br.edu.unigranrio.ect.si.cfa.commons.model.User;

import java.io.Serializable;

public interface Auth extends Serializable {

    String doLogin();

    String doLogout();

    User getUser();

}
