package br.edu.unigranrio.ect.si.cfa.service;

import br.edu.unigranrio.ect.si.cfa.commons.model.User;
import br.edu.unigranrio.ect.si.cfa.service.exception.AuthException;

import java.io.Serializable;

public interface AuthService extends Serializable {

    User doLogin(String email, String password) throws AuthException;

    void doLogout(User user) throws AuthException;

}
