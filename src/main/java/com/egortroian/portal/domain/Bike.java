package com.egortroian.portal.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@ToString
public class Bike {
    public Bike() {
    }

    public Bike(String brand, String model, Integer engine) {
        this.brand = brand;
        this.model = model;
        this.engine = engine;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String brand;
    private String model;
    private Integer engine;
    private boolean isAvailable;
    private String imageURL;
}
