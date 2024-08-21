package com.example.Blinkkart.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OrderItem extends BaseModel{

    @ManyToOne
    private Product product;
    private int quantity;
    private double price;

    @ManyToOne
    private Order order;
}
