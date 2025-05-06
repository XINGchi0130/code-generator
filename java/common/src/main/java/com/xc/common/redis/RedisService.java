package com.xc.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置值
     *
     * @param key   键
     * @param value 值
     * @return true设置成功，false设置失败
     */
    public <T> boolean set(String key, T value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置值并设置过期时间
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间（秒）
     * @return true设置成功，false设置失败
     */
    public <T> boolean set(String key, T value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取值
     *
     * @param key 键
     * @return 值，如果键不存在则返回 null
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除键
     *
     * @param key 键
     * @return true删除成功，false删除失败
     */
    public boolean delete(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除多个键
     *
     * @param keys 多个键
     * @return 删除的键的数量
     */
    public Boolean delete(List<String> keys) {
        if (keys != null && keys.size() > 0) {
            if (keys.size() == 1) {
                return redisTemplate.delete(keys.get(0));
            } else {
                return redisTemplate.delete(keys) > 0;
            }
        }
        return true;
    }

    /**
     * 判断键是否存在
     *
     * @param key 键
     * @return true 存在，false 不存在
     */
    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 设置键的过期时间
     *
     * @param key  键
     * @param time 过期时间（秒）
     * @return true设置成功，false设置失败
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
