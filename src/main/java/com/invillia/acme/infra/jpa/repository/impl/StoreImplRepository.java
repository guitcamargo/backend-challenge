package com.invillia.acme.infra.jpa.repository.impl;

import com.invillia.acme.domain.entity.QStore;
import com.invillia.acme.domain.entity.Store;
import com.invillia.acme.domain.query.Query;
import com.invillia.acme.domain.query.impl.StoreFilter;
import com.invillia.acme.infra.jpa.repository.StoreRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação do repositório da entidade Store
 *
 * @author guilhermecamargo
 */
@Repository
public class StoreImplRepository extends AbstractJpaRepository<Store, Long> implements StoreRepository {

    @Override
    public List<Store> find(Query<Store> query) {
        final PathBuilder<Store> path = new PathBuilder<>(Store.class, "store");
        final JPAQuery jpaQuery = this.create(query);

        if (query.getSorter() != null && !StringUtils.isEmpty(query.getSorter().getProperty())) {
            if (query.getSorter().isDesc()) {
                jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).desc());
            } else {
                jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).asc());
            }
        } else {
            jpaQuery.orderBy(path.getString("id").asc());
        }

        if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
            jpaQuery.offset(((query.getPage() - 1L) * query.getLimit()));
        }

        jpaQuery.limit(query.getLimit());

        return jpaQuery.fetch();
    }

    @Override
    public long count(Query<Store> query) {
        return this.create(query).fetchCount();
    }

    private JPAQuery<Store> create(Query<Store> query) {
        final QStore qStore = QStore.store;

        final JPAQuery<Store> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qStore)
                .from(qStore);

        if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof StoreFilter) {
            final StoreFilter filter = (StoreFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getDescricao())) {
                jpaQuery.where(qStore.name.likeIgnoreCase("%" + filter.getDescricao() + "%"));
            }
        }

        return jpaQuery;
    }
}
