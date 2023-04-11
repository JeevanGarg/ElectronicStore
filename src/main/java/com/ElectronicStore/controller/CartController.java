package com.ElectronicStore.controller;

import com.ElectronicStore.dtos.AddItemToCartRequest;
import com.ElectronicStore.dtos.ApiResponseMessage;
import com.ElectronicStore.dtos.CartDto;
import com.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ElectronicStore.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController
{
    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable("userId") String usierId, @RequestBody AddItemToCartRequest request) throws ResourceNotFoundException {
        CartDto cartDto = cartService.addItemToCart(usierId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable("userId") String userId,
                                                                 @PathVariable("itemId") int itemId) throws ResourceNotFoundException {
        cartService.removeItemFromCart(userId,itemId);
        ApiResponseMessage message = ApiResponseMessage.builder().message("Item is removed !").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable("userId") String userId) throws ResourceNotFoundException {
        cartService.clearCart(userId);
        ApiResponseMessage message = ApiResponseMessage.builder().message("Cart is blank!").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId) throws ResourceNotFoundException {
        CartDto cartByUser = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartByUser,HttpStatus.OK);
    }
}
