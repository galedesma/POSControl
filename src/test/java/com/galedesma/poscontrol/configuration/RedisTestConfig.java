package com.galedesma.poscontrol.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/*
Clase necesaria ya que a pesar de explicitar el puerto del contenedor de Redis, el cliente Lettuce trata de conectarse
a un puerto al azar si no se lo configura.
 */
@TestConfiguration
public class RedisTestConfig {

    final String REDIS_HOST = "localhost";
    final int REDIS_PORT = 6379;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(REDIS_HOST, REDIS_PORT));
    }
}
