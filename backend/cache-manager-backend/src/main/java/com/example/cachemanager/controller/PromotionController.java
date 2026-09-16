package com.example.cachemanager.controller;

import com.example.cachemanager.entity.Promotion;
import com.example.cachemanager.service.PromotionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping("/{id}")
    public Promotion getPromotion(@PathVariable Integer id) {
        return promotionService.getPromotion(id);
    }

    @PostMapping
    public void savePromotion(@RequestBody Promotion promotion) {
        promotionService.savePromotion(promotion);
    }
   // only for cache
    @DeleteMapping("/{id}")
    public void removeFromCache(@PathVariable Integer id) {
        promotionService.removeFromCache(id);
    }
    // only for cache
    @DeleteMapping("/cache")
    public void clearCache() {
        promotionService.clearCache();
    }
}