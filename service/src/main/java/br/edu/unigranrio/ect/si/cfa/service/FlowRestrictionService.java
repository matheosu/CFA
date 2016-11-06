package br.edu.unigranrio.ect.si.cfa.service;

import br.edu.unigranrio.ect.si.cfa.model.FlowRestriction;
import br.edu.unigranrio.ect.si.cfa.model.Period;
import br.edu.unigranrio.ect.si.cfa.model.Restriction;

import java.util.List;

public interface  FlowRestrictionService extends Service {

    FlowRestriction findByName(String name);

    List<FlowRestriction> findByRestriction(Restriction restriction);

    List<FlowRestriction> findByPeriod(Period period);

    List<FlowRestriction> findByRestrictionAndPeriod(Restriction restriction, Period period);

}
