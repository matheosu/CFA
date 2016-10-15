package br.edu.unigranrio.ect.si.cfa.service.bean;

import br.edu.unigranrio.ect.si.cfa.commons.model.*;
import br.edu.unigranrio.ect.si.cfa.service.FlowService;

import javax.inject.Named;
import javax.persistence.criteria.*;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Named
public class FlowServiceBean extends BaseService implements FlowService {

    public Float availableFlow(Long userId) {
        User user = find(User.class, userId); // Descobre usuário
        FlowRestriction flowRestriction = user.getFlowRestriction(); // Pega sua restrição de fluxo
        Period period = flowRestriction.getPeriod(); // Pega o período da restrição
        List<Flow> flows = findFlowByPeriod(userId, period); // Verifica quantos fluxos já foram realizados nesse periodo
        Double totalUsedFlows = flows.stream().mapToDouble(Flow::getMeasure).sum(); // soma todos os fluxos
        Float totalFlowPerPeriod = flowRestriction.getValue(); // pega o valor total de fluxos por periodo
        return totalFlowPerPeriod - totalUsedFlows.floatValue(); // Realiza o calculo
    }

    public List<Flow> findFlowByPeriod(Long userId, Period period) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUtil<Flow, Flow> util = createCriteriaUtil(Flow.class);
        CriteriaQuery<Flow> query = util.getQuery();
        Root<Flow> from = util.getFrom();
        Path<Long> userPath = from.join(Flow_.user).get(User_.id);
        Path<Calendar> registrantionPath = from.get(Flow_.registrantion);

        int expValue = 0;
        Expression<Integer> expPath = null;
        Calendar now = Calendar.getInstance();
        if(period != null) switch (period) {
            case DAY:
                expValue = now.get(Calendar.DAY_OF_MONTH);
                expPath = cb.function("day", Integer.class, registrantionPath);
                break;

            case WEEK:
                expValue = now.get(Calendar.WEEK_OF_MONTH);
                expPath = cb.function("week", Integer.class, registrantionPath);
                break;

            case MONTH:
                expValue = now.get(Calendar.MONTH) + 1;
                expPath = cb.function("month", Integer.class, registrantionPath);
                break;

            case YEAR:
                expValue = now.get(Calendar.YEAR);
                expPath = cb.function("year", Integer.class, registrantionPath);
                break;
            default:
                return Collections.emptyList();
        }

        return resultList(query.where(cb.and(cb.equal(userPath, userId), cb.equal(expPath, expValue))));

    }
}
