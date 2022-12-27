package org.example.service;

import org.example.model.Item;
import org.example.repository.ItemRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ItemService {

    private final ItemRepository repository;

    public ItemService(final ItemRepository repository) {
        this.repository = repository;
    }

    @Cacheable("items")
    public Mono<Item> getItem(final String id) {
        return repository.findById(id);
    }

    public Mono<Item> save(final Item item) {
        return repository.save(item);
    }

}
