package br.edu.unigranrio.ect.si.cfa.service;


import br.edu.unigranrio.ect.si.cfa.model.Flow;
import br.edu.unigranrio.ect.si.cfa.model.Period;

import java.util.Calendar;
import java.util.List;

public interface FlowService extends Service {

    Float availableFlow(Long userId);

    List<Flow> findFlowByPeriod(Long userId, Period period, Calendar date);

    default List<Flow> findFlowByPeriod(Long userId, Period period){
        return findFlowByPeriod(userId, period, Calendar.getInstance());
    }

    List<Flow> findFlowByPeriod(Period period, Calendar date);

    default List<Flow> findFlowByPeriod(Period period){
        return findFlowByPeriod(period, Calendar.getInstance());
    }
}
