package com.project.breakshop.config;


import com.project.breakshop.models.DTO.CartItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
/*
    @Value : Spring이 지원하는 의존성 주입 방법중 하나입니다.
    SpEL을 지원하며 application.properties의 속성값을 프로퍼티에 넣어줍니다.

    RedisConnectionFactory을 생성하여 스프링 세션을 레디스 서버로 연결시킵니다.
    RedisConnectionFactory는 Connection 객체를 생성하여 관리하는 인터페이스입니다.
    RedisConnection을 리턴합니다. RedisConnection은 Redis 서버와의 통신을 추상화합니다.

    Redis Connection Factory를 리턴해주는 라이브러리로 Lettuce를 선택하였습니다.
    비동기로 요청하기 때문에 높은성능을 가지기 때문입니다.
    Lettuce는 Netty기반이며 Netty는 비동기 네트워크 프레임워크입니다.
    Netty는 Channel에서 발생하는 이벤트들을 EventLoop로 비동기 처리하는 구조입니다.

    RedisCacheManager: Cache Manager는 스프링에서 추상화한 인터페이스고 이를 레디스 방식으로
    구현한 것이 RedisCacheManager입니다. serializeKeysWith, serializeValueWith로
    캐시 Key와 Value를 직렬화,역직렬화 할때 설정을 해줍니다.
    Value에는 GenericJackson2JsonRedisSerializer를 사용했는데 이 Serializer는
    별도로 Class Type을 지정해줄 필요없이 자동으로 Object를 Json으로 직렬화 해줍니다.
    단점으로는 Object의 Class Type을 레디스에 함꼐 넣기 때문에
    데이터를 꺼내올 때 그 클래스타입으로만 가져올 수 있습니다.
 */

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.session.port}")
    private int redisSessionPort;

    @Value("${spring.redis.cache.port}")
    private int redisCachePort;

    @Value("${spring.redis.cart.port}")
    private int redisCartPort;

    @Value("${spring.redis.rider.port}")
    private int redisDeliveryPort;

    @Value("${spring.redis.departHost}")
    private boolean redisDepartHost;

    @Value("${spring.redis.session.host}")
    private String redisSessionHost;

    @Value("${spring.redis.cache.host}")
    private String redisCacheHost;

    @Value("${spring.redis.cart.host}")
    private String redisCartHost;

    @Value("${spring.redis.rider.host}")
    private String redisRiderHost;

    @Value("${spring.redis.password}")
    private String redisPassword;

    public RedisProperties redisProperties = new RedisProperties();

    private String hostUrl = redisHost;

    // lettuce
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        if (redisDepartHost) {
            hostUrl = redisSessionHost;
        }
        redisProperties.setHost(hostUrl);
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public RedisConnectionFactory redisSessionConnectionFactory() {
        if (redisDepartHost) {
            hostUrl = redisSessionHost;
        }

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostUrl);
        redisStandaloneConfiguration.setPort(redisSessionPort);
        redisStandaloneConfiguration.setPassword(redisPassword);

        return new LettuceConnectionFactory(
                redisStandaloneConfiguration);
    }

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        if (redisDepartHost) {
//            hostUrl = redisSessionHost;
//        }
//
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(hostUrl);
//        redisStandaloneConfiguration.setPort(redisSessionPort);
//        redisStandaloneConfiguration.setPassword(redisPassword);
//
//        return new LettuceConnectionFactory(
//                redisStandaloneConfiguration);
//    }


    @Bean
    public RedisConnectionFactory redisCacheConnectionFactory() {

        if (redisDepartHost) {
            hostUrl = redisCacheHost;
        }
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostUrl);
        redisStandaloneConfiguration.setPort(redisCachePort);
        redisStandaloneConfiguration.setPassword(redisPassword);

        return new LettuceConnectionFactory(
                redisStandaloneConfiguration);
    }

    @Bean
    public RedisConnectionFactory redisCartConnectionFactory() {
        hostUrl = redisHost;
        if (redisDepartHost) {
            hostUrl = redisCartHost;
        }
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostUrl);
        redisStandaloneConfiguration.setPort(redisCartPort);
        redisStandaloneConfiguration.setPassword(redisPassword);

        return new LettuceConnectionFactory(
                redisStandaloneConfiguration);
    }

    @Bean
    public RedisConnectionFactory redisDeliveryConnectionFactory() {

        if (redisDepartHost) {
            hostUrl = redisRiderHost;
        }
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostUrl);
        redisStandaloneConfiguration.setPort(redisDeliveryPort);
        redisStandaloneConfiguration.setPassword(redisPassword);

        return new LettuceConnectionFactory(
                redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, CartItemDTO> cartItemDTORedisTemplate() {
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer =
                new GenericJackson2JsonRedisSerializer();

        RedisTemplate<String, CartItemDTO> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisCartConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);

        return redisTemplate;
    }

    @Bean
    @Qualifier("deliveryRedisTemplate")
    public RedisTemplate<String, Object> deliveryRedisTemplate() {
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer =
                new GenericJackson2JsonRedisSerializer();

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisDeliveryConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        return redisTemplate;
    }

    @Bean
    public RedisCacheManager redisCacheManager() {

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .disableCachingNullValues()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer())
                )
                .entryTtl(Duration.ofDays(1L));

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisCacheConnectionFactory())
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }
}

