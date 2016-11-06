package br.edu.unigranrio.ect.si.cfa.web.authentication;

import java.io.Serializable;
import java.security.Principal;

public interface Authenticator extends Serializable {

    String doLogin();

    String doLogout();

    Principal getPrincipal();

    boolean isAuthenticate();

}