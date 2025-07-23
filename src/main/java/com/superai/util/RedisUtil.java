package com.superai.util;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis操作工具类，简化常用操作，全部以json字符串存储
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置指定key的值（json字符串），并设置过期天数
     */
    public void set(String key, Object value, long expireDays) {
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value));
        redisTemplate.expire(key, expireDays, TimeUnit.DAYS);
    }

    /**
     * 获取指定key的值（json字符串），并转为指定类型
     */
    public <T> T get(String key, Class<T> clazz) {
        Object obj = redisTemplate.opsForValue().get(key);
        if (obj == null) return null;
        return JSONUtil.toBean(obj.toString(), clazz);
    }

    /**
     * 向指定List右侧追加元素（json字符串），并设置过期天数
     */
    public void rightPush(String key, Object value, long expireDays) {
        redisTemplate.opsForList().rightPush(key, JSONUtil.toJsonStr(value));
        redisTemplate.expire(key, expireDays, TimeUnit.DAYS);
    }

    /**
     * 获取指定List区间的所有元素（json字符串），并转为指定类型列表
     */
    public <T> List<T> range(String key, long start, long end, Class<T> clazz) {
        List<Object> list = redisTemplate.opsForList().range(key, start, end);
        if (list == null) return null;
        return list.stream().map(obj -> JSONUtil.toBean(obj.toString(), clazz)).collect(Collectors.toList());
    }

    /**
     * 获取所有匹配给定模式的key集合
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }
} 