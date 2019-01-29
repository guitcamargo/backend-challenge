package com.invillia.acme.domain.exception;

/**
 * Exceção abstrata de validação.
 *
 * @author guilhermecamargo
 */
public abstract class ValidationException extends RuntimeException {

    /**
     * Retorna a propriedade que foi violada.
     *
     * @return
     */
    public abstract String getProperty();
}
