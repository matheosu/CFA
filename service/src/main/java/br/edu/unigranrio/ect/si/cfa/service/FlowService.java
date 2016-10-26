package br.edu.unigranrio.ect.si.cfa.service;


import br.edu.unigranrio.ect.si.cfa.commons.model.Flow;
import br.edu.unigranrio.ect.si.cfa.commons.model.Period;

import java.util.List;

public interface FlowService extends Service {

    Float availableFlow(Long userId);

    List<Flow> findFlowByPeriod(Long userId, Period period);
}
