package com.example.Blinkkart.service;

import com.example.Blinkkart.model.Cart;
import com.example.Blinkkart.model.CartItem;
import com.example.Blinkkart.model.Product;
import com.example.Blinkkart.model.User;
import com.example.Blinkkart.repository.CartItemRepository;
import com.example.Blinkkart.repository.CartRepository;
import com.example.Blinkkart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Cart getCartForUser(User user) {
        Optional<Cart> optionalCart = cartRepository.findByUser(user);
        if(optionalCart.isPresent()){
            return optionalCart.get();
        }else{
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setPrice(0.0);
            return cartRepository.save(newCart);
        }
    }

    @Override
    public Cart addProductToCart(User user, Product product, int quantity) {
        Cart cart = getCartForUser(user);
        boolean productExistsInCart = false;
        for(CartItem cartItem : cart.getCartItems()){
            if(cartItem.getProduct().equals(product)){
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                productExistsInCart = true;
                break;
            }
        }
        if(!productExistsInCart){
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            cart.getCartItems().add(newCartItem);
        }
        cart.setPrice(calculateTotalPrice(cart));
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateCartItemQuantity(User user, Product product, int quantity) {
        try {
            Cart cart = getCartForUser(user);
            CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);

            if (cartItem != null) {
                // Update the quantity of the item in the cart
                cartItem.setQuantity(quantity);

                // Save the updated cart item
                cartItemRepository.save(cartItem);

                // Recalculate and update the total price of the cart
                cart.setPrice(calculateTotalPrice(cart));

                // Save the updated cart
                return cartRepository.save(cart);
            } else {
                // If the item is not found, throw an exception
                throw new RuntimeException("Item not found in cart");
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur during the process
            System.err.println("Error updating cart item quantity: " + e.getMessage());
            return null; // Return null or handle the error as appropriate
        }
    }

    @Override
    public Cart removeProductFromCart(User user, Product product) {
        try {
            Cart cart = getCartForUser(user);
            CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);

            if (cartItem != null) {
                // Remove the item from the cart
                cart.getCartItems().remove(cartItem);

                // Delete the cart item from the repository
                cartItemRepository.delete(cartItem);

                // Recalculate and update the total price of the cart
                cart.setPrice(calculateTotalPrice(cart));

                // Save the updated cart
                return cartRepository.save(cart);
            } else {
                // If the item is not found, throw an exception
                throw new RuntimeException("Item not found in cart");
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur during the process
            System.err.println("Error removing product from cart: " + e.getMessage());
            return null; // Return null or handle the error as appropriate
        }
    }

    private double calculateTotalPrice(Cart cart) {
        double totalprice = 0.0;

        for(CartItem cartItem : cart.getCartItems()){
            double itemTotal = cartItem.getProduct().getPrice() * cartItem.getQuantity();

            totalprice += itemTotal;
        }

        return totalprice;
    }
}
