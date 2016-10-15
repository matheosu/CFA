package br.edu.unigranrio.ect.si.cfa.commons.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static br.edu.unigranrio.ect.si.cfa.commons.model.Restriction.RestrictionType.*;

public enum Restriction {

    MILLILITER(MEASURE),
    LITER(MEASURE),

    SECOND(TIME),
    MINUTE(TIME),
    HOUR(TIME),
    ;

    public enum RestrictionType {
        TIME, MEASURE, ;
    }
    private RestrictionType type;

    Restriction(RestrictionType type) {
        this.type = type;
    }

    public RestrictionType getType() {
        return type;
    }

    public static List<Restriction> getRestritionsByType(RestrictionType type) {
        List<Restriction> restrictions = Arrays.asList(Restriction.values());
        return restrictions.stream()
                .filter(r -> r.getType().equals(type))
                .collect(Collectors.toList());
    }
}
