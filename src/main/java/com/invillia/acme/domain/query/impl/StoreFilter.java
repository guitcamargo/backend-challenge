package com.invillia.acme.domain.query.impl;

import com.invillia.acme.domain.entity.Store;
import com.invillia.acme.domain.query.Filter;

/**
 * Filter para a entidade Store
 *
 * @author guilhermecamargo
 */
public class StoreFilter implements Filter<Store> {

    private final String descricao;

    public StoreFilter(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
