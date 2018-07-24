package com.wangxin.springboot.common.cache;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisCache {

    // StringRedisTemplate使用的是StringRedisSerializer进行序列化
    @Autowired
    private StringRedisTemplate srt;

    public boolean hasKey(String key) {
        return srt.hasKey(key);
    }

    // 插入字符串键值对方法
    public void put (String key, String obj) {
        srt.opsForValue().set(key, obj);
    }

    // 设置过期时间
    public void putWithExpireTime(String key, String obj, int expireTime) {
        srt.opsForValue().set(key, obj);
    }

    // 通过键获取值
    public String get(String key) {
        return srt.opsForValue().get(key);
    }
}
