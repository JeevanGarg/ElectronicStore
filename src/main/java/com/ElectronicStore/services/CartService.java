package com.ElectronicStore.services;

import com.ElectronicStore.dtos.AddItemToCartRequest;
import com.ElectronicStore.dtos.CartDto;
import com.ElectronicStore.exceptions.ResourceNotFoundException;

public interface CartService
{
    //add items to cart:
    //case1: cart for user is not available:we will create the cart and then add the items
    //case2:cart available add the items to cart

    CartDto addItemToCart(String userId, AddItemToCartRequest request) throws ResourceNotFoundException;

    //remove item from cart

    void removeItemFromCart(String userId,int cartItem) throws ResourceNotFoundException;

    void clearCart(String userId) throws ResourceNotFoundException;

    CartDto getCartByUser(String userId) throws ResourceNotFoundException;
}
