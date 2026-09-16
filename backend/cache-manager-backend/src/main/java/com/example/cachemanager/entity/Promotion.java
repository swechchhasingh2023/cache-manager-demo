package com.example.cachemanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "promotion")
public class Promotion {

    @Id
    private Integer id;

    private String name;

    // Required by JPA
    public Promotion() {
    }

    public Promotion(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
