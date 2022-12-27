package org.example.repository;

import org.example.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class ItemRepository {


    private final MongoTemplate mongoTemplate;

    @Autowired
    public ItemRepository(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Mono<Item> findById(final String id) {
        System.out.println("==> findById");
        return Mono.justOrEmpty(mongoTemplate.findById(id, Item.class));
    }

    public Mono<Item> save(final Item item) {
        System.out.println("==> save");
        return Mono.justOrEmpty(mongoTemplate.save(item));
    }
}
