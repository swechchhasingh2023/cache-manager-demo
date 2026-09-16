package com.example.cachemanager.cache;

import com.example.cachemanager.entity.Promotion;

public class CacheManagerDemo {

    public static void main(String[] args) {

        CacheManager cacheManager = new CacheManager();

        Promotion promotion1 = new Promotion(101, "Summer Sale");
        Promotion promotion2 = new Promotion(102, "Winter Sale");
        System.out.println("Hi " +promotion1);

        // Add promotions to cache
        cacheManager.put(promotion1.getId(), promotion1);
        cacheManager.put(promotion2.getId(), promotion2);

        // Get promotion from cache
        System.out.println("Promotion 101: " + cacheManager.get(101));
        System.out.println("Promotion 102: " + cacheManager.get(102));

        // Remove promotion
        cacheManager.remove(101);

        System.out.println("After removing 101: " + cacheManager.get(101));

        // Clear cache
        cacheManager.clear();

        System.out.println("After clearing cache: " + cacheManager.get(102));
    }
}