package org.example;

import org.example.model.Item;
import org.example.service.ItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;

@SpringBootTest
public class AppTest {

    @Autowired
    ItemService itemService;

    final static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start();
        registry.add("spring.data.mongodb.uri",  mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    public void bootTest() {}

    @Test
    public void givenItemWhenGetItemIsCalledThenMonoIsCached() throws InterruptedException {
        System.out.println("==> Start Regular Insert <==");
        final Mono<Item> glass = itemService.save(Item.Builder.anItem()
                .withName("glass")
                .withPrice(1.00)
                .build());

        final String id = glass.block().getId();

        System.out.println("==> First get <==");
        final Mono<Item> mono = itemService.getItem(id);
        final Item item = mono.block();
        System.out.println("==> Done <==");

        Assertions.assertNotNull(item);
        Assertions.assertEquals(item.getName(), "glass");
        Assertions.assertEquals(item.getPrice(), 1.00);

        System.out.println("==> Second get <==");
        final Mono<Item> monoCached = itemService.getItem(id);
        final Item itemCached = monoCached.block();
        System.out.println("==> Done <==");

        Assertions.assertNotNull(itemCached);
        Assertions.assertEquals(itemCached.getName(), "glass");
        Assertions.assertEquals(itemCached.getPrice(), 1.00);

        Thread.sleep(8000);

        System.out.println("==> Third get <==");
        itemService.getItem(id).block();
        System.out.println("==> Done <==");

        System.out.println("==> Finish task <==");
    }
}
