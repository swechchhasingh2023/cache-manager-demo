package com.example.cachemanager.service;

import com.example.cachemanager.cache.CacheManager;
import com.example.cachemanager.dao.PromotionDao;
import com.example.cachemanager.entity.Promotion;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PromotionService {

    private final CacheManager cacheManager;
    private final PromotionDao promotionDao;

    public PromotionService(CacheManager cacheManager, PromotionDao promotionDao) {
        this.cacheManager = cacheManager;
        this.promotionDao = promotionDao;
    }

    public Promotion getPromotion(Integer id) {

        // 1. Check cache
        Promotion promotion = cacheManager.get(id);

        if (promotion != null) {
            System.out.println("Cache HIT for promotion: " + id);
            return promotion;
        }

        // 2. Cache miss → get from database
        System.out.println("Cache MISS for promotion: " + id);

        promotion = promotionDao.findById(id);

        // 3. Put DB result into cache
        if (promotion != null) {
            cacheManager.put(id, promotion);
        }

        return promotion;
    }

    @Transactional
    public void savePromotion(Promotion promotion) {

        // Save to database
        promotionDao.save(promotion);

        // Put newly saved promotion into cache
        cacheManager.put(promotion.getId(), promotion);
    }

    public void removeFromCache(Integer id) {
        cacheManager.remove(id);
    }

    public void clearCache() {
        cacheManager.clear();
    }
}