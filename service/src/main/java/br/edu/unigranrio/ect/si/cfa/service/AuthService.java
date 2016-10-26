package br.edu.unigranrio.ect.si.cfa.service;

import br.edu.unigranrio.ect.si.cfa.commons.model.User;

public interface AuthService {

    User doLogin(String email, String password);

    void doLogout(User user);

}
