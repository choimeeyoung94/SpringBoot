package org.shark.sample.board.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class BoardRedisConfig {

  @Value("${spring.data.redis.host}")
  private String host;
  
  @Value("${spring.data.redis.port}")
  private int port;
  
  @Bean
  RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(host, port);
  }
  
}
