package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CacheConfig.class
        , MongoConfig.class})
public class AppConfig {}