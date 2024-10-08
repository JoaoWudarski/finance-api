package com.jwapp.financialapi.application.ports;

public interface FindEntityPort<T, I> {

    T byId(I id);
    boolean exists(I id);
}
