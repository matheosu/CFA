package br.edu.unigranrio.ect.si.cfa.web;

import java.io.Serializable;

public interface Auth extends Serializable {

    String doLogin();

    String doLogout();

    String getUserName();

    boolean isAuthenticate();

    boolean hasFeature(String featureName);

    String urlFeature(final String featureName);

}