package com.lera.catalog.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "good")
@Getter
@Setter
@NoArgsConstructor
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "external_id")
    private String externalId;

    public Good(String name, String description, BigDecimal price, String externalId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.externalId = externalId;
    }
}
