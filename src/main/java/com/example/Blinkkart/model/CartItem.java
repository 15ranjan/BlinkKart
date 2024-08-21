package com.example.Blinkkart.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CartItem extends BaseModel{

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;
}
