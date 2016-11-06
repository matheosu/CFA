package br.edu.unigranrio.ect.si.cfa.web.authentication;

import java.security.Principal;

public final class PrincipalWrapper implements Principal {

    private final String name;

    public PrincipalWrapper(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}