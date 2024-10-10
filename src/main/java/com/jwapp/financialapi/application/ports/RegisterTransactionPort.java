package com.jwapp.financialapi.application.ports;

public interface RegisterTransactionPort<T> {

    T entrance(T transaction);
    T exit(T transaction);
}
