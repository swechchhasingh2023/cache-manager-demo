package com.example.cachemanager.cache;
import org.springframework.stereotype.Component;

import com.example.cachemanager.entity.Promotion;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class CacheManager {

    private final Map<Integer, Promotion> cache = new ConcurrentHashMap<>();

    public void put(Integer id, Promotion promotion) {
        cache.put(id, promotion);
    }

    public Promotion get(Integer id) {
        return cache.get(id);
    }

    public void remove(Integer id) {
        cache.remove(id);
    }

    public void clear() {
        cache.clear();
    }
}