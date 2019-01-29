package com.invillia.acme.domain.query;

import java.io.Serializable;
import java.util.Optional;

/**
 * Ordenação de entidades.
 *
 * @author guilhermecamargo
 */
public final class Sorter<T> implements Serializable {

    private final String property;
    private final Direction direction;

    public Sorter(String property, Direction direction) {
        this.property = property;
        this.direction = Optional.ofNullable(direction).orElse(Direction.ASC);
    }

    public static <T> SorterBuilder<T> by(String property) {
        return new SorterBuilder<>(property);
    }

    public String getProperty() {
        return property;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isAsc() {
        return Direction.ASC.equals(direction);
    }

    public boolean isDesc() {
        return Direction.DESC.equals(direction);
    }

    public enum Direction {

        ASC,
        DESC
    }

    public static class SorterBuilder<T> {

        private final String property;

        private SorterBuilder(String property) {
            this.property = property;
        }

        public Sorter<T> asc() {
            return new Sorter<>(property, Direction.ASC);
        }

        public Sorter<T> desc() {
            return new Sorter<>(property, Direction.DESC);
        }

        public Sorter<T> direction(Direction direction) {
            return new Sorter<>(property, direction);
        }
    }
}
