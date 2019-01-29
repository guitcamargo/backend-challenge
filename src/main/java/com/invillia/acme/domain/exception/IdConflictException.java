package com.invillia.acme.domain.exception;

/**
 * Exceção para conflito de ID.
 *
 * @author guilhermecamargo
 */
public class IdConflictException extends ValidationException {

    @Override
    public String getProperty() {
        return "id";
    }
}
