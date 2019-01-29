package com.invillia.acme.application.resource;

import com.invillia.acme.application.hateoas.Hateoas;
import com.invillia.acme.domain.entity.Store;
import com.invillia.acme.domain.query.Query;
import com.invillia.acme.domain.query.Sorter;
import com.invillia.acme.domain.query.impl.StoreFilter;
import com.invillia.acme.domain.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.util.stream.Collectors;

/**
 * REST of entity STORE.
 *
 * @author guilhermecamargo
 */
@RestController
@RequestMapping("/api/stores")
public class StoreResource {

    @Autowired
    private StoreService storeService;

    /**
     * find all stores
     *
     * @param name
     * @param sortProperty
     * @param sortDirection
     * @param page
     * @return
     */
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<PagedResources<Resource<Store>>> find(@RequestParam(value = "name", required = false) final String name,
                                                                @RequestParam(value = Hateoas.SORT_BY_PARAM, required = false) String sortProperty,
                                                                @RequestParam(value = Hateoas.SORT_DIRECTION_PARAM, required = false) Sorter.Direction sortDirection,
                                                                @RequestParam(value = Hateoas.PAGE_PARAM, required = false) Long page) {
        final Query<Store> query = Query.<Store>builder()
                .filter(new StoreFilter(name))
                .sort(Sorter.<Store>by(sortProperty).direction(sortDirection))
                .page(page)
                .build();

        return ResponseEntity.ok(Hateoas.pageResources(
                storeService.find(query).stream().map(Hateoas::toResource).collect(Collectors.toList()),
                storeService.count(query),
                page));
    }

    /**
     * find store by  ID.
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Resource<Store>> findById(@PathVariable("id") Long id) {
        final Store store = storeService.findById(id).orElseThrow(EntityNotFoundException::new);

        return ResponseEntity.ok(Hateoas.toResource(store));
    }

    /**
     * create store.
     *
     * @param store
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody @Valid Store store) {
        storeService.create(store);

        final URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(store.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * update store.
     *
     * @param id
     * @param store
     * @return
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody @Valid Store store) {
        if (store.getId() != null && !store.getId().equals(id)) {
            return null;
        }

        storeService.update(store);
        return ResponseEntity.noContent().build();
    }

    /**
     * remove store by ID.
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
        storeService.removeById(id);
        return ResponseEntity.noContent().build();
    }
}
