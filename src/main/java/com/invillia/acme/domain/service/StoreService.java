package com.invillia.acme.domain.service;

import com.invillia.acme.domain.entity.Store;
import com.invillia.acme.domain.query.Query;
import com.invillia.acme.infra.jpa.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class StoreService implements Serializable {

    @Autowired
    private StoreRepository storeRepository;

    /**
     * find stores by query
     *
     * @param query
     * @return
     */
    public List<Store> find(Query<Store> query) {
        return storeRepository.find(query);
    }

    /**
     * count stores
     *
     * @param query
     * @return
     */
    public long count(Query<Store> query) {
        return storeRepository.count(query);
    }

    /**
     * find store by ID
     *
     * @param id
     * @return
     */
    public Optional<Store> findById(Long id) {
        return storeRepository.findById(id);
    }

    /**
     * create new store
     *
     * @param store
     */
    @Transactional
    public void create(Store store) {
        storeRepository.create(store);
    }

    /**
     * put store
     *
     * @param store
     */
    @Transactional
    public void update(Store store) {
        storeRepository.update(store);
    }

    /**
     * Remove store
     *
     * @param store
     */
    @Transactional
    public void remove(Store store) {
        storeRepository.remove(store);
    }

    /**
     * Remove store by ID
     *
     * @param id
     */
    @Transactional
    public void removeById(Long id) {
        storeRepository.removeById(id);
    }
    
    
}
