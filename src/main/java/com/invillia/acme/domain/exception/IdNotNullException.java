package com.invillia.acme.domain.exception;

/**
 * Exceção para ID não nulo.
 *
 * @author guilhermecamargo
 */
public class IdNotNullException extends ValidationException {

    @Override
    public String getProperty() {
        return "id";
    }
}
