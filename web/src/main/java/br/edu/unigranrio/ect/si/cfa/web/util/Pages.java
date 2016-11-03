package br.edu.unigranrio.ect.si.cfa.web.util;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;

public final class Pages {

    /* Redirect */
    public static final String REDIRECT = "?faces-redirect=true";

    public static final String EXTENSION = ".xhtml";

    /* Path */
    public static final String ERROR = "/error/";
    public static final String RESTRICT = "/restrict/";

    /* Default Pages */
    public static final String INDEX = "/index" + EXTENSION;
    public static final String ACTION_AUTH = "/auth" + EXTENSION;
    public static final String ACTION_MAIN = RESTRICT + "main" + EXTENSION;

    public static final String ERROR_AUTH_EXPIRED = ERROR + "expired" + EXTENSION;

    /* Pattern */
    public static final String ACTION_LIST = "_list" + EXTENSION;
    public static final String ACTION_EDIT = "_edit" + EXTENSION;


    private Pages() {
    }

    public static String actionMain() {
        return actionMain(true);
    }

    public static String actionMain(boolean redirect) {
        return ACTION_MAIN + (redirect  ? REDIRECT : "");
    }

    public static String actionAuth() {
        return actionAuth(true);
    }

    public static String actionAuth(boolean redirect) {
        return ACTION_AUTH + (redirect ? REDIRECT : "");
    }

    public static  <E extends Entity<?>> String actionList(Class<E> clazz, boolean redirect) {
        String classLower = clazz.getSimpleName().toLowerCase();
        return actionList(classLower, redirect);
    }

    public static String actionList(String page, boolean redirect) {
        return RESTRICT + page + ACTION_LIST + (redirect ? REDIRECT : "");
    }

    public static <E extends Entity<?>> String actionList(Class<E> clazz) {
        return actionList(clazz, false);
    }

    public static <E extends Entity<?>> String actionEdit(Class<E> clazz, boolean redirect) {
        String classLower = clazz.getSimpleName().toLowerCase();
        return actionEdit(classLower, redirect);
    }

    public static String actionEdit(String page, boolean redirect) {
        return RESTRICT + page + ACTION_EDIT + (redirect ? REDIRECT : "");
    }

    public static <E extends Entity<?>> String actionEdit(Class<E> clazz) {
        return actionEdit(clazz, false);
    }
}
