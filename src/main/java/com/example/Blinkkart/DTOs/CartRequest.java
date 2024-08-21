package com.example.Blinkkart.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {
    private Long userId;
    private Long productId;
    private int quantity;
}
