package com.nimble.sloth.router.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import static java.lang.System.getenv;

@Configuration
public class RedisConfig {

    private static final String DEFAULT_REDIS_HOST = "localhost";
    private static final int DEFAULT_REDIS_PORT = 6379;

    private static final String PASSWORD_KEY = "password";

    @Bean
    public Jedis jedis() {
        try {
            return remoteConnection();
        } catch (Exception e) {
            return localhostConnection();
        }
    }

    private Jedis remoteConnection() {
        final String password = getenv(PASSWORD_KEY);
        final Jedis jedis = new Jedis("redis-18895.c93.us-east-1-3.ec2.cloud.redislabs.com", 18895);
        jedis.connect();
        jedis.auth(password);
        System.out.println("Connected to remote");
        return jedis;
    }

    private Jedis localhostConnection() {
        final Jedis jedis = new Jedis(DEFAULT_REDIS_HOST, DEFAULT_REDIS_PORT);
        System.out.println("Connected to localhost");
        return jedis;
    }
}
