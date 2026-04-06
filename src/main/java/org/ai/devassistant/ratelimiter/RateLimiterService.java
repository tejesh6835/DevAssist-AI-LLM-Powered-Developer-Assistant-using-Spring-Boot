package org.ai.devassistant.ratelimiter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;

    private static final int MAX_REQUESTS = 5;
    private static final int WINDOW_SECONDS = 60;

    public boolean isAllowed(String key) {

        String redisKey = "rate_limit:" + key;

        Long count = redisTemplate.opsForValue().increment(redisKey);

        if (count == 1) {
            redisTemplate.expire(redisKey, Duration.ofSeconds(WINDOW_SECONDS));
        }

        return count <= MAX_REQUESTS;
    }
}