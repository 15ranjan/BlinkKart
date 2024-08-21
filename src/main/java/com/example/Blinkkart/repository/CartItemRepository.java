package com.example.Blinkkart.repository;

import com.example.Blinkkart.model.Cart;
import com.example.Blinkkart.model.CartItem;
import com.example.Blinkkart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart(Cart cart);

    CartItem findByCartAndProduct(Cart cart, Product product);
}
