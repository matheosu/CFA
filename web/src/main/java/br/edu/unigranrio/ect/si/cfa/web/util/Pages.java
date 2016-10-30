package br.edu.unigranrio.ect.si.cfa.web.util;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;

public final class Pages {

    /* Redirect */
    public static final String REDIRECT = "?faces-redirect=true";

    /* Path */
    public static final String ERROR = "error/";
    public static final String RESTRICT = "restrict/";

    /* Default Pages */
    public static final String ACTION_AUTH = "authMsg.action";
    public static final String ACTION_MENU = RESTRICT + "menu.action";

    public static final String ERROR_AUTH_EXPIRED = ERROR + "expired.action";

    /* Pattern */
    public static final String ACTION_LIST = "_list.action";
    public static final String ACTION_EDIT = "_edit.action";


    private Pages() {
    }

    public static String actionMenu() {
        return actionMenu(true);
    }

    public static String actionMenu(boolean redirect) {
        return ACTION_MENU + (redirect  ? REDIRECT : "");
    }

    public static String actionAuth() {
        return actionAuth(true);
    }

    public static String actionAuth(boolean redirect) {
        return ACTION_AUTH + (redirect ? REDIRECT : "");
    }

    public static  <E extends Entity<?>> String actionList(Class<E> clazz, boolean redirect) {
        String classLower = clazz.getSimpleName().toLowerCase();
        return RESTRICT + classLower + ACTION_LIST + (redirect ? REDIRECT : "");
    }

    public static <E extends Entity<?>> String actionList(Class<E> clazz) {
        return actionList(clazz, false);
    }

    public static <E extends Entity<?>> String actionEdit(Class<E> clazz, boolean redirect) {
        String classLower = clazz.getSimpleName().toLowerCase();
        return RESTRICT + classLower + ACTION_EDIT + (redirect ? REDIRECT : "");
    }

    public static <E extends Entity<?>> String actionEdit(Class<E> clazz) {
        return actionEdit(clazz, false);
    }
}
