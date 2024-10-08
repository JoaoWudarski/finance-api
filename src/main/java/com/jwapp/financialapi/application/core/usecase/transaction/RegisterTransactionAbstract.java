package com.jwapp.financialapi.application.core.usecase.transaction;

import com.jwapp.financialapi.application.core.domain.interfaces.ValueChange;
import com.jwapp.financialapi.application.core.exception.NotFoundException;
import com.jwapp.financialapi.application.ports.RegisterTransactionPort;
import com.jwapp.financialapi.application.ports.gateway.BasicEntityGatewayPort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.function.Predicate;

@RequiredArgsConstructor
abstract class RegisterTransactionAbstract<T, Y extends ValueChange> implements RegisterTransactionPort<T> {

    private final BasicEntityGatewayPort<Y> channelTransactionGatewayPort;

    protected boolean add(BigDecimal value, Long valueChangeId) {
        return threatResponse(valueChange -> valueChange.addValue(value), valueChangeId);
    }

    protected boolean remove(BigDecimal value, Long valueChangeId) {
        return threatResponse(valueChange -> valueChange.removeValue(value), valueChangeId);
    }

    protected BigDecimal negateValue(BigDecimal value) {
        return value.negate();
    }

    private boolean threatResponse(Predicate<ValueChange> makeTransaction, Long valueChangeId) {
        Y valueChange = channelTransactionGatewayPort.findById(valueChangeId)
                .orElseThrow(() -> new NotFoundException("Nao existe canal para transacao com id " + valueChangeId));
        if (!makeTransaction.test(valueChange))
            return false;
        channelTransactionGatewayPort.save(valueChange);
        return true;
    }
}
