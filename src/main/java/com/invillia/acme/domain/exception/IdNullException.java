package com.invillia.acme.domain.exception;

/**
 * Exceção para ID nulo.
 *
 * @author guilhermecamargo
 */
public class IdNullException extends ValidationException {

    @Override
    public String getProperty() {
        return "id";
    }
}
