package br.edu.unigranrio.ect.si.cfa.service.bean;

import br.edu.unigranrio.ect.si.cfa.commons.util.DateTimeUtils;
import br.edu.unigranrio.ect.si.cfa.model.*;
import br.edu.unigranrio.ect.si.cfa.service.FlowService;

import javax.inject.Named;
import javax.persistence.criteria.*;
import java.util.Calendar;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Named
public class FlowServiceBean extends ServiceBean implements FlowService {

    private static final long serialVersionUID = 3532101836322434970L;

    private static final Integer FACTOR_ML_TO_L = 1000; // 1000 mL x 1L

    public Float availableFlow(Long userId) {
        /* Descobre usuário */
        User user = find(User.class, userId);
        if (user == null) return DEFAULT_VALUE;

        /* Pega sua restrição de fluxo */
        FlowRestriction flowRestriction = user.getFlowRestriction();
        /* Pega a restrição */
        Restriction restriction = flowRestriction.getRestriction();
        /* Pega o período da restrição */
        Period period = flowRestriction.getPeriod();
        /* Busca todos os fluxos já realizados nesse periodo */
        List<Flow> flows = findFlowByPeriod(userId, period);

        Float flowRestrictionValue = flowRestriction.getValue() != null ? flowRestriction.getValue() : DEFAULT_VALUE;
        switch (restriction.getType()) {
            /* Caso o tipo da restrição seja de medida retorno sempre em mL */
            case MEASURE: {
                /* Soma todos os fluxos (ML) */
                Double totalUsedFlows = flows.stream().mapToDouble(Flow::getMeasure).sum();
                /* Pega o valor de fluxo por periodo em mL (Caso esteja em L Converto em mL) */
                Float flowPerPeriod = Restriction.LITER.equals(restriction) ?  flowRestrictionValue * FACTOR_ML_TO_L : flowRestrictionValue;
                return flowPerPeriod - totalUsedFlows.floatValue(); /* Realiza o calculo e retorna o fluxo disponível mL */
            }

            /* Caso o tipo da restrição seja de tempo retorno sempre em min */
            case TIME: {
                /* Pega todos os registros de tempo do fluxo */
                SortedSet<Calendar> collect = flows.stream().map(Flow::getRegistrantion)
                        .sorted().collect(Collectors.toCollection(TreeSet::new));
                /* Converto o valor da restrição sempre em minutos */
                Number minuteRestriction = TimeUnit.MINUTES.convert(flowRestrictionValue.longValue(), TimeUnit.valueOf(restriction.name() + "s"));
                return collect.isEmpty() ? minuteRestriction.floatValue() : minuteRestriction.floatValue() -
                        DateTimeUtils.diffCalendars(collect, TimeUnit.MINUTES).floatValue(); /* Calcula a diferença entre as datas retornando em Minutos */
            }

            default: {
                return DEFAULT_VALUE;
            }
        }
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
            case MONTH:
                return date.get(Calendar.MONTH) + 1;
            case YEAR:
                return date.get(Calendar.YEAR);
        }
        return -1;
    }

}
