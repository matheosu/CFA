package br.edu.unigranrio.ect.si.cfa.service;


import br.edu.unigranrio.ect.si.cfa.model.Flow;
import br.edu.unigranrio.ect.si.cfa.model.Period;
import br.edu.unigranrio.ect.si.cfa.service.exception.ServiceExeption;

import java.util.Calendar;
import java.util.List;

public interface FlowService extends Service {

    /**
     * Valor Default em Float 0.0F
     */
    Float DEFAULT_VALUE = 0.0F;

    /**
     * Método responsável por verificar quanto de fluxo disponível ainda tem para o usuário. <br/>
     * Métrica de fluxo gasto pelo usuário VS restrição de fluxo.<br/>
     *
     * @param userId id do usuário para realizar a verificação
     * @return Retorna o valor disponível em: <br/>
     *  <ul>
     *      <li>Litros se o tipo da restrição for Medida</li>
     *      <li>Minutos se o tipo da restrição for Tempo</li>
     *      <li>Retorna valor {@value FlowService.DEFAULT_VALUE } Default caso não encontre o usuário</li>
     *  </ul>
     *  @see br.edu.unigranrio.ect.si.cfa.model.Restriction
     *  @see br.edu.unigranrio.ect.si.cfa.model.Restriction.RestrictionType
     */
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
