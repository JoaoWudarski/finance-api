package com.jwapp.financialapi.application.ports;

public interface CreateEntityPort<T> {

    Long createNew(T entity);
}
