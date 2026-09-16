package com.example.cachemanager.cache;

import com.example.cachemanager.entity.Promotion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheManagerTest {

    @Test
    void shouldPutAndGetPromotion() {
        CacheManager cacheManager = new CacheManager();

        Promotion promotion = new Promotion(1, "Summer Sale");

        cacheManager.put(1, promotion);

        Promotion result = cacheManager.get(1);

        assertNotNull(result);
        assertEquals("Summer Sale", result.getName());
    }

    @Test
    void shouldReturnNullWhenPromotionDoesNotExist() {
        CacheManager cacheManager = new CacheManager();

        Promotion result = cacheManager.get(999);

        assertNull(result);
    }

    @Test
    void shouldRemovePromotion() {
        CacheManager cacheManager = new CacheManager();

        Promotion promotion = new Promotion(2, "Winter Sale");

        cacheManager.put(2, promotion);
        cacheManager.remove(2);

        Promotion result = cacheManager.get(2);

        assertNull(result);
    }

    @Test
    void shouldClearCache() {
        CacheManager cacheManager = new CacheManager();

        cacheManager.put(1, new Promotion(1, "Summer Sale"));
        cacheManager.put(2, new Promotion(2, "Winter Sale"));

        cacheManager.clear();

        assertNull(cacheManager.get(1));
        assertNull(cacheManager.get(2));
    }
}