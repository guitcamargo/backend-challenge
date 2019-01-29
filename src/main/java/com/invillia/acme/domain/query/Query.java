package com.invillia.acme.domain.query;

import java.io.Serializable;

/**
 * Consulta de entidades.
 *
 * @author guilhermecamargo
 */
public final class Query<T> implements Serializable {

    public static final long LIMIT = 10L;

    public static final long NO_LIMIT = -1;

    private final Filter<T> filter;
    private final Sorter<T> sorter;
    private final Long page;
    private final long limit;

    private Query(Filter<T> filter, Sorter<T> sorter, Long page, Long limit) {
        this.filter = filter;
        this.sorter = sorter;
        this.page = page;
        this.limit = limit == null ? LIMIT : limit;
    }

    public static <T> QueryBuilder<T> builder() {
        return new QueryBuilder<>();
    }

    public Filter<T> getFilter() {
        return filter;
    }

    public Sorter<T> getSorter() {
        return sorter;
    }

    public Long getPage() {
        return page;
    }

    public long getLimit() {
        return limit;
    }

    public static class QueryBuilder<T> {

        private Filter<T> filter;
        private Sorter<T> sorter;
        private Long page;
        private Long limit;

        private QueryBuilder() {
        }

        public QueryBuilder<T> filter(Filter<T> filter) {
            this.filter = filter;
            return this;
        }

        public QueryBuilder<T> sort(Sorter<T> sorter) {
            this.sorter = sorter;
            return this;
        }

        public QueryBuilder<T> page(Long page) {
            this.page = page;
            return this;
        }

        public QueryBuilder<T> limit(Long limit) {
            this.limit = limit;
            return this;
        }

        public Query<T> build() {
            return new Query<>(filter, sorter, page, limit);
        }
    }
}
