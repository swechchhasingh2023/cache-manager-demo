package com.example.cachemanager.service;

import com.example.cachemanager.cache.CacheManager;
import com.example.cachemanager.dao.PromotionDao;
import com.example.cachemanager.entity.Promotion;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PromotionServiceTest {

    @Test
    void shouldReturnPromotionFromCacheWhenCacheHit() {
        CacheManager cacheManager = mock(CacheManager.class);
        PromotionDao promotionDao = mock(PromotionDao.class);

        Promotion promotion = new Promotion(1, "Summer Sale");

        when(cacheManager.get(1)).thenReturn(promotion);

        PromotionService service =
                new PromotionService(cacheManager, promotionDao);

        Promotion result = service.getPromotion(1);

        assertNotNull(result);
        assertEquals("Summer Sale", result.getName());

        verify(cacheManager).get(1);
        verify(promotionDao, never()).findById(1);
    }

    @Test
    void shouldFetchFromDaoWhenCacheMiss() {
        CacheManager cacheManager = mock(CacheManager.class);
        PromotionDao promotionDao = mock(PromotionDao.class);

        Promotion promotion = new Promotion(2, "Winter Sale");

        when(cacheManager.get(2)).thenReturn(null);
        when(promotionDao.findById(2)).thenReturn(promotion);

        PromotionService service =
                new PromotionService(cacheManager, promotionDao);

        Promotion result = service.getPromotion(2);

        assertNotNull(result);
        assertEquals("Winter Sale", result.getName());

        verify(cacheManager).get(2);
        verify(promotionDao).findById(2);
        verify(cacheManager).put(2, promotion);
    }

    @Test
    void shouldReturnNullWhenPromotionDoesNotExist() {
        CacheManager cacheManager = mock(CacheManager.class);
        PromotionDao promotionDao = mock(PromotionDao.class);

        when(cacheManager.get(999)).thenReturn(null);
        when(promotionDao.findById(999)).thenReturn(null);

        PromotionService service =
                new PromotionService(cacheManager, promotionDao);

        Promotion result = service.getPromotion(999);

        assertNull(result);

        verify(cacheManager).get(999);
        verify(promotionDao).findById(999);
        verify(cacheManager, never()).put(anyInt(), any());
    }

    @Test
    void shouldSavePromotionAndPutItIntoCache() {
        CacheManager cacheManager = mock(CacheManager.class);
        PromotionDao promotionDao = mock(PromotionDao.class);

        Promotion promotion = new Promotion(3, "Diwali Sale");

        PromotionService service =
                new PromotionService(cacheManager, promotionDao);

        service.savePromotion(promotion);

        verify(promotionDao).save(promotion);
        verify(cacheManager).put(3, promotion);
    }

    @Test
    void shouldRemovePromotionFromCache() {
        CacheManager cacheManager = mock(CacheManager.class);
        PromotionDao promotionDao = mock(PromotionDao.class);

        PromotionService service =
                new PromotionService(cacheManager, promotionDao);

        service.removeFromCache(4);

        verify(cacheManager).remove(4);
    }

    @Test
    void shouldClearPromotionCache() {
        CacheManager cacheManager = mock(CacheManager.class);
        PromotionDao promotionDao = mock(PromotionDao.class);

        PromotionService service =
                new PromotionService(cacheManager, promotionDao);

        service.clearCache();

        verify(cacheManager).clear();
    }
}