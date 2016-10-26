package br.edu.unigranrio.ect.si.cfa.web.util;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;

public final class Pages {

    /* Redirect */
    public static final String REDIRECT = "?faces-redirect=true";

    public static final String RESTRICT = "restrict/";

    /* Pattern */
    public static final String ACTION_LIST = "_list";
    public static final String ACTION_EDIT = "_edit";

    private Pages() {
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
