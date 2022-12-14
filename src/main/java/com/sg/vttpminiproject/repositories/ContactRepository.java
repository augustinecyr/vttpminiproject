package com.sg.vttpminiproject.repositories;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.sg.vttpminiproject.models.Contact;

@Repository
public class ContactRepository {
    @Autowired
    // always rmb to set REDIS_PASSWORD on cli
    @Qualifier("redislab")
    private RedisTemplate<String, String> redisTemplate;

    public void save(Contact entry) {
        redisTemplate.opsForValue().set(entry.getEmail(), entry.toJson().toString());
        redisTemplate.expire(entry.getEmail(), Duration.ofHours(10));
    }
    // retrieves email from redis by calling get
    public String get(String email) {
        if (!redisTemplate.hasKey(email))
            return "";
        String data = redisTemplate.opsForValue().get(email);
        System.out.println(data);
        return data;
    }

}
