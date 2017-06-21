package br.edu.unigranrio.ect.si.cfa.service;

import br.edu.unigranrio.ect.si.cfa.model.FlowRestriction;
import br.edu.unigranrio.ect.si.cfa.model.Period;
import br.edu.unigranrio.ect.si.cfa.model.Restriction;

import java.util.List;
import java.util.Optional;

public interface  FlowRestrictionService extends Service {

    Optional<FlowRestriction> findByName(String name);

    List<FlowRestriction> findByRestriction(Restriction restriction);

    List<FlowRestriction> findByPeriod(Period period);

    List<FlowRestriction> findByRestrictionAndPeriod(Restriction restriction, Period period);

}
