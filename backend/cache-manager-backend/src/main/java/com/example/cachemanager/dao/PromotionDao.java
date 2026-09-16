package com.example.cachemanager.dao;

import com.example.cachemanager.entity.Promotion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class PromotionDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Promotion findById(Integer id) {
        return entityManager.find(Promotion.class, id);
    }

    public void save(Promotion promotion) {
        entityManager.persist(promotion);
    }
}