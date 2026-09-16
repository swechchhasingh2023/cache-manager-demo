package com.example.cachemanager.controller;

import com.example.cachemanager.entity.Promotion;
import com.example.cachemanager.service.PromotionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PromotionController.class)
class PromotionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PromotionService promotionService;

    @Test
    void shouldGetPromotion() throws Exception {
        Promotion promotion = new Promotion(1, "Summer Sale");

        when(promotionService.getPromotion(1)).thenReturn(promotion);

        mockMvc.perform(get("/promotions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Summer Sale"));

        verify(promotionService).getPromotion(1);
    }

    @Test
    void shouldReturnNullPromotion() throws Exception {
        when(promotionService.getPromotion(999)).thenReturn(null);

        mockMvc.perform(get("/promotions/999"))
                .andExpect(status().isOk());

        verify(promotionService).getPromotion(999);
    }

    @Test
    void shouldSavePromotion() throws Exception {
        String requestBody = """
                {
                    "id": 2,
                    "name": "Winter Sale"
                }
                """;

        mockMvc.perform(post("/promotions")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());

        verify(promotionService).savePromotion(any(Promotion.class));
    }

    @Test
    void shouldRemovePromotionFromCache() throws Exception {
        mockMvc.perform(delete("/promotions/1"))
                .andExpect(status().isOk());

        verify(promotionService).removeFromCache(1);
    }

    @Test
    void shouldClearCache() throws Exception {
        mockMvc.perform(delete("/promotions/cache"))
                .andExpect(status().isOk());

        verify(promotionService).clearCache();
    }
}