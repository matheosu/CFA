package br.edu.unigranrio.ect.si.cfa.service.bean;

import br.edu.unigranrio.ect.si.cfa.model.*;
import br.edu.unigranrio.ect.si.cfa.service.FlowService;

import javax.inject.Named;
import javax.persistence.criteria.*;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Named
public class FlowServiceBean extends ServiceBean implements FlowService {

    private static final long serialVersionUID = 3532101836322434970L;

    public Float availableFlow(Long userId) {
        User user = find(User.class, userId); // Descobre usuário
        FlowRestriction flowRestriction = user.getFlowRestriction(); // Pega sua restrição de fluxo
        Period period = flowRestriction.getPeriod(); // Pega o período da restrição
        List<Flow> flows = findFlowByPeriod(userId, period); // Verifica quantos fluxos já foram realizados nesse periodo
        Double totalUsedFlows = flows.stream().mapToDouble(Flow::getMeasure).sum(); // soma todos os fluxos
        Float totalFlowPerPeriod = flowRestriction.getValue(); // pega o valor total de fluxos por periodo
        return totalFlowPerPeriod - totalUsedFlows.floatValue(); // Realiza o calculo
    }

    @Override
    public List<Flow> findFlowByPeriod(Long userId, Period period, Calendar date) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUtil<Flow, Flow> util = createCriteriaUtil(Flow.class);
        CriteriaQuery<Flow> query = util.getQuery();
        Root<Flow> from = util.getFrom();

        Path<Long> userPath = from.join(Flow_.user).get(User_.id);
        Path<Calendar> registrantionPath = from.get(Flow_.registrantion);

        Integer expValue = getPeriodValueFromDate(period, date);
        Expression<Integer> expPath = getExpressionFromPeriod(period, registrantionPath);

        return resultList(query.where(cb.and(cb.equal(userPath, userId), cb.equal(expPath, expValue))));
    }

    @Override
    public List<Flow> findFlowByPeriod(Period period, Calendar date) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUtil<Flow, Flow> util = createCriteriaUtil(Flow.class);
        CriteriaQuery<Flow> query = util.getQuery();
        Root<Flow> from = util.getFrom();

        Path<Calendar> registrantionPath = from.get(Flow_.registrantion);

        Integer expValue = getPeriodValueFromDate(period, date);
        Expression<Integer> expPath = getExpressionFromPeriod(period, registrantionPath);

        return resultList(query.where(cb.equal(expPath, expValue)));
    }

    private Expression<Integer> getExpressionFromPeriod(Period period, Path<Calendar> calendarPath) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        if(period != null) switch (period) {
            case DAY:
                return cb.function("day", Integer.class, calendarPath);
            case WEEK:
                return cb.function("week", Integer.class, calendarPath);
            case MONTH:
                return cb.function("month", Integer.class, calendarPath);

            case YEAR:
                return cb.function("year", Integer.class, calendarPath);
        }
        return null;
    }

    private Integer getPeriodValueFromNow(Period period) {
        return getPeriodValueFromDate(period, Calendar.getInstance());
    }

    private Integer getPeriodValueFromDate(Period period, Calendar date) {
        if(period != null && date != null) switch (period) {
            case DAY:
                return date.get(Calendar.DAY_OF_MONTH);
            case WEEK:
                return date.get(Calendar.WEEK_OF_MONTH);
            case MONTH:
                return date.get(Calendar.MONTH) + 1;
            case YEAR:
                return date.get(Calendar.YEAR);
        }
        return -1;
    }

}
