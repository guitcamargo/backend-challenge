package com.invillia.acme.infra.jpa.repository.impl;

import com.invillia.acme.domain.exception.IdNotNullException;
import com.invillia.acme.domain.exception.IdNullException;
import com.invillia.acme.infra.jpa.repository.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.GeneratedValue;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;


@SuppressWarnings("unchecked")
public abstract class AbstractJpaRepository<T, ID> implements Repository<T, ID> {

    protected final Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * Retorna se o ID da entidade é gerado automaticamente.
     *
     * @return
     */
    private boolean isGeneratedId() {
        if (Arrays.stream(entityClass.getDeclaredFields()).anyMatch(field -> field.isAnnotationPresent(GeneratedValue.class)) ||
                Arrays.stream(entityClass.getSuperclass().getDeclaredFields()).anyMatch(field -> field.isAnnotationPresent(GeneratedValue.class))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Consulta uma entidade através do ID.
     *
     * @param id
     * @return
     */
    @Override
    public Optional<T> findById(ID id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    /**
     * Insere uma nova entidade.
     *
     * @param entity
     */
    @Override
    public void create(T entity) {
        Optional<ID> id = getIdentifier(entity);
        if (isGeneratedId() && id.isPresent()) {
            throw new IdNotNullException();
        } else if (!isGeneratedId() && !id.isPresent()) {
            throw new IdNotNullException();
        } else {
            entityManager.persist(entity);
            entityManager.flush();
        }
    }

    /**
     * Altera uma entidade.
     *
     * @param entity
     */
    @Override
    public void update(T entity) {
        findById(getIdentifier(entity).orElseThrow(IdNullException::new)).orElseThrow(EntityNotFoundException::new);
        entityManager.merge(entity);
        entityManager.flush();
    }

    /**
     * Remove uma entidade.
     *
     * @param entity
     */
    @Override
    public void remove(T entity) {
        removeById(getIdentifier(entity).orElseThrow(IdNullException::new));
    }

    /**
     * Remove uma entidade através do ID.
     *
     * @param id
     */
    @Override
    public void removeById(ID id) {
        final T entity = entityManager.find(entityClass, id);

        if (Objects.isNull(entity)) {
            throw new EntityNotFoundException();
        }

        entityManager.remove(entity);
        entityManager.flush();
    }

    /**
     * Retorna o ID da entidade.
     *
     * @param entity
     * @return
     */
    protected Optional<ID> getIdentifier(T entity) {
        return Optional.ofNullable((ID) entityManager
                .getEntityManagerFactory()
                .getPersistenceUnitUtil()
                .getIdentifier(entity));
    }
}