package br.edu.unigranrio.ect.si.cfa.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static br.edu.unigranrio.ect.si.cfa.model.Restriction.RestrictionType.*;

public enum Restriction {

    MILLILITER(MEASURE),
    LITER(MEASURE),

    MINUTES(TIME),
    HOURS(TIME),
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
        return Arrays.stream(Restriction.values())
                .filter(r -> r.getType().equals(type))
                .collect(Collectors.toList());
    }
}
