package com.invillia.acme.domain.entity;


import com.invillia.acme.application.hateoas.Hateoas;
import com.querydsl.core.annotations.QueryProjection;
import org.springframework.hateoas.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 */
@Entity
@Table(name = "STORE")
@Relation(collectionRelation = Hateoas.STORES)
@SequenceGenerator(allocationSize = 1, name = "seqStore", sequenceName = "SEQ_STORE")
public class Store implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqStore")
    private Long id;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull
    @Column(name = "ADDRES", nullable = false)
    private String addres;

    public Store() {
    }

    @QueryProjection
    public Store(Long id){
        this.id = id;
    }

    @QueryProjection
    public Store(String name, String addres) {
        this.name = name;
        this.addres = addres;
    }

    @QueryProjection
    public Store(Long id, String name, String addres) {
        this.name = name;
        this.addres = addres;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Objects.equals(getId(), store.getId()) &&
                Objects.equals(getName(), store.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addres='" + addres + '\'' +
                '}';
    }
}
