package com.ElectronicStore.services.impl;

import com.ElectronicStore.dtos.AddItemToCartRequest;
import com.ElectronicStore.dtos.CartDto;
import com.ElectronicStore.entities.Cart;
import com.ElectronicStore.entities.CartItem;
import com.ElectronicStore.entities.Product;
import com.ElectronicStore.entities.User;
import com.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ElectronicStore.repository.CartItemRepository;
import com.ElectronicStore.repository.CartRepository;
import com.ElectronicStore.repository.ProductRepository;
import com.ElectronicStore.repository.UserRepository;
import com.ElectronicStore.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService
{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) throws ResourceNotFoundException {
        int quantity= request.getQuantity();
        String productId= request.getProductId();

        if(quantity<=0)
        {
            throw new ResourceNotFoundException("quantity is not valid");
        }

        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("product Id not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        Cart cart=null;


        try
        {
           cart=cartRepository.findByUser(user).get();
        }
        catch (NoSuchElementException e)
        {
            cart=new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }

        //perform cart operation

        //if cart item is already present then update
        AtomicBoolean updated= new AtomicBoolean(false);
        List<CartItem> items=cart.getItems();

        List<CartItem> updatedItem = items.stream().map(e -> {
            if (e.getProduct().getProductId().equals(productId)) {
                //iteam already present in cart
                e.setQuantity(quantity);
                e.setTotalPrice(quantity * product.getPrice());
                updated.set(true);
            }

            return e;
        }).collect(Collectors.toList());

        cart.setItems(updatedItem);


        if(!updated.get())
        {
            CartItem cartItem = CartItem.builder().quantity(quantity).totalPrice(quantity * product.getPrice())
                    .cart(cart).product(product).build();

            cart.getItems().add(cartItem);
        }


        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        return this.modelMapper.map(updatedCart,CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) throws ResourceNotFoundException
    {

        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("cartItem it not found"));
        cartItemRepository.delete(cartItem1);
    }

    @Override
    public void clearCart(String userId) throws ResourceNotFoundException
    {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user id Is invalid"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("cart not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCartByUser(String userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user id Is invalid"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("cart not found"));


        return modelMapper.map(cart,CartDto.class);
    }
}
