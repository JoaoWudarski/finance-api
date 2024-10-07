package com.jwapp.financialapi.usecase.impl;

import com.jwapp.financialapi.domain.Card;
import com.jwapp.financialapi.domain.payment.CardPayment;
import com.jwapp.financialapi.repository.CardPaymentRepository;
import com.jwapp.financialapi.usecase.FindBill;
import com.jwapp.financialapi.usecase.FindCard;
import com.jwapp.financialapi.usecase.model.Bill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FindBillImpl implements FindBill {

    private final CardPaymentRepository cardPaymentRepository;
    private final FindCard findCard;

    @Override
    public Bill byCardIdAndMonths(Long cardId, Long minusMonths) {
        Card card = findCard.byId(cardId);
        LocalDate actualDate = LocalDate.now().minusMonths(minusMonths);
        LocalDateTime finalDate = LocalDateTime.of(LocalDate.of(actualDate.getYear(), actualDate.getMonth(), card.getCloseDay()), LocalTime.MAX);
        LocalDateTime initialDate = finalDate.minusMonths(1);

        LocalDate maturityDate = LocalDate.of(actualDate.getYear(), actualDate.getMonth(), card.getPaymentDay());
        if (maturityDate.isBefore(finalDate.toLocalDate()))
            maturityDate = maturityDate.plusMonths(1L);

        List<CardPayment> cardPaymentList = cardPaymentRepository.findByCardAndDateTimeBetween(card, initialDate, finalDate);

        return new Bill(initialDate.toLocalDate(), finalDate.toLocalDate(), maturityDate, cardPaymentList);
    }
}
