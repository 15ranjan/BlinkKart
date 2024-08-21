package com.example.Blinkkart.controller;

import com.example.Blinkkart.DTOs.CartRequest;
import com.example.Blinkkart.model.Cart;
import com.example.Blinkkart.model.Product;
import com.example.Blinkkart.model.User;
import com.example.Blinkkart.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    // Retrieve the cart for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCartForUser(@PathVariable Long userId) {
        try {
            // Assuming userService has a method to find a user by ID
            User user = userService.findById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Cart cart = cartService.getCartForUser(user);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            // Log the error and handle it
            System.err.println("Error in getCartForUser: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Add a product to the user's cart
    @PostMapping("/add")
    public ResponseEntity<Cart> addProductToCart(@RequestBody CartRequest cartRequest) {
        try {
            User user = userService.findById(cartRequest.getUserId());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Product product = productService.getProductById(cartRequest.getProductId());
            if (product == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            if (cartRequest.getQuantity() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            Cart cart = cartService.addProductToCart(user, product, cartRequest.getQuantity());
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            // Log error and return a generic error response
            System.err.println("Error adding product to cart: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Update the quantity of a product in the user's cart
    @PutMapping("/update")
    public ResponseEntity<Cart> updateCartItemQuantity(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        User user = new User();
        user.setId(userId);
        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Cart cart = cartService.updateCartItemQuantity(user, product, quantity);
        return ResponseEntity.ok(cart);
    }

    // Remove a product from the user's cart
    @DeleteMapping("/remove")
    public ResponseEntity<Cart> removeProductFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        User user = new User();
        user.setId(userId);
        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Cart cart = cartService.removeProductFromCart(user, product);
        return ResponseEntity.ok(cart);
    }
}
