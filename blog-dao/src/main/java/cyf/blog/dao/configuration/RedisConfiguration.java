package cyf.blog.dao.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Cheng Yufei
 * @create 2018-03-08 下午9:54
 **/
@Configuration
@AutoConfigureBefore({DataSourceConfiguration.class})
@Slf4j
public class RedisConfiguration {

    public static RedisTemplate redisTemplate;

    @Bean
    public RedisTemplate getRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        redisTemplate = stringRedisTemplate;
        return stringRedisTemplate;
    }
}
