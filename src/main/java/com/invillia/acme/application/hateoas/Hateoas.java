package com.invillia.acme.application.hateoas;

import com.invillia.acme.application.resource.StoreResource;
import com.invillia.acme.domain.entity.Store;
import com.invillia.acme.domain.query.Query;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class Hateoas implements Serializable {

    public static final String PAGE_PARAM = "page";
    public static final String SORT_BY_PARAM = "sortBy";
    public static final String SORT_DIRECTION_PARAM = "sortDirection";

    public static final String STORES = "stores";
    private static final String FIND_PARAM = "{?id}";
    private static final String FIND = "find";

     /* Retorna os recursos paginados.
     *
     * @param resources
     * @param totalElements
     * @param page
     * @param <T>
     * @return
     */
    public static <T> PagedResources<Resource<T>> pageResources(final Collection<Resource<T>> resources, final long totalElements, final Long page) {
        final long size = resources.size();
        final long number = Optional.ofNullable(page).filter(p -> p > 0L).orElse(1L);
        final long totalPages = totalElements == 0L ? 0L : totalElements <= Query.LIMIT ? 1L : (long) Math.ceil((double) totalElements / resources.size());

        final long first = 1L;
        final long previous = number <= 1L ? 1L : number - 1L;
        final long next = number >= totalPages ? totalPages : number + 1L;
        final long last = totalPages;

        final List<Link> links = new ArrayList<>();
        links.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(PAGE_PARAM, number).toUriString(), Link.REL_SELF));

        if (totalElements > 0L) {
            if (totalPages > 1L) {
                if (number > 1L) {
                    links.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(PAGE_PARAM, first).toUriString(), Link.REL_FIRST));

                    if (number <= totalPages) {
                        links.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(PAGE_PARAM, previous).toUriString(), Link.REL_PREVIOUS));
                    }
                }

                if (number != totalPages) {
                    if (number <= totalPages) {
                        links.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(PAGE_PARAM, next).toUriString(), Link.REL_NEXT));
                    }

                    links.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(PAGE_PARAM, last).toUriString(), Link.REL_LAST));
                }

                links.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(PAGE_PARAM, 0L).toUriString()
                        .replace("?" + PAGE_PARAM + "=0", FIND_PARAM)
                        .replace("&" + PAGE_PARAM + "=0", FIND_PARAM),
                        FIND));
            }
        }

        return new PagedResources<>(resources, new PagedResources.PageMetadata(size, number, totalElements, totalPages), links);
    }

    /**
     * Transforma uma entidade Store em um recurso HATEOAS.
     *
     * @param store
     * @return
     */
    public static Resource<Store> toResource(Store store) {
        final Resource<Store> resource = new Resource<>(store);
        resource.add(linkTo(methodOn(StoreResource.class).findById(store.getId())).withSelfRel());
        resource.add(linkTo(methodOn(StoreResource.class).find(null, null, null, null)).withRel(STORES));

        return resource;
    }
}
