package br.edu.unigranrio.ect.si.cfa.commons.util;

import java.util.Arrays;
import java.util.List;

public final class Constants {


    public static final String LOGGED_USER_ATTR = "loggedUser";

    /**
     * System User ID
     */
    public static final Long SYSTEM_ID = 1L;


    /**
     * System Features
     */
    public static final Long roleFeatureId = 10L;
    public static final Long userFeatureId = 20L;
    public static final Long controllerFeatureId = 30L;
    public static final Long localizationFeatureId = 40L;
    public static final Long flowRestrictionFeatureId = 50L;

    public static final List<Long> systemFeaturesId = Arrays.asList(roleFeatureId, userFeatureId,
            controllerFeatureId, localizationFeatureId, flowRestrictionFeatureId);

    private Constants() {
    }
}
