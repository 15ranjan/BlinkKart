package com.example.Blinkkart.service;

import com.example.Blinkkart.model.Cart;
import com.example.Blinkkart.model.Product;
import com.example.Blinkkart.model.User;

public interface CartService {

    //Retrieve the cart for a specific user.
    Cart getCartForUser(User user);

    // Add a product to the user's cart.
    Cart addProductToCart(User user, Product product, int quantity);

    //Update the quantity of a product in the user's cart.
    Cart updateCartItemQuantity(User user, Product product, int quantity);

    //Remove a product from the user's cart.
    Cart removeProductFromCart(User user, Product product);

}
