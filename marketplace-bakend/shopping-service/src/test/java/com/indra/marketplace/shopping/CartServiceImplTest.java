package com.indra.marketplace.shopping;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.indra.marketplace.shopping.client.CustomerClient;
import com.indra.marketplace.shopping.client.ProductClient;
import com.indra.marketplace.shopping.dto.Product;
import com.indra.marketplace.shopping.dto.ProductRequest;
import com.indra.marketplace.shopping.entity.Cart;
import com.indra.marketplace.shopping.entity.CartItem;
import com.indra.marketplace.shopping.entity.Coupon;
import com.indra.marketplace.shopping.exception.ProductOutOfStockException;
import com.indra.marketplace.shopping.repository.CartRepository;
import com.indra.marketplace.shopping.service.CartService;

import java.time.LocalDate;
import java.util.Optional;

class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CustomerClient customerClient;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private CartService cartService;

    @Mock
    private Cart cart;

    @Mock
    private CartItem cartItem;

    @Mock
    private ProductRequest productRequest;

    @Mock
    private Coupon coupon;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCart_whenCartDoesNotExist() {
        // Arrange
        Cart newCart = new Cart();
        newCart.setCustomerId(1L);
        
        when(cartRepository.findByCustomerId(1L)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(newCart);
        
        Product product = new Product();
        product.setPrice(100.0);
        
        when(productClient.getProductPrice(anyLong())).thenReturn(100.0);
        when(productClient.updateStockProduct(anyLong(), anyInt())).thenReturn(null); // Simulating no response

        // Act
        Cart savedCart = cartService.save(newCart);

        // Assert
        assertNotNull(savedCart);
        assertEquals(1L, savedCart.getCustomerId());
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void testSaveCart_whenCartAlreadyExists() {
        // Arrange
        Cart existingCart = new Cart();
        existingCart.setCustomerId(1L);

        when(cartRepository.findByCustomerId(1L)).thenReturn(Optional.of(existingCart));

        // Act
        Cart savedCart = cartService.save(existingCart);

        // Assert
        assertEquals(existingCart, savedCart);
        verify(cartRepository, never()).save(any(Cart.class)); // Never should save because it already exists
    }

    @Test
    void testAddProductToCart_whenProductAddedSuccessfully() {
        // Arrange
        Cart cart = new Cart();
        cart.setCustomerId(1L);
        
        ProductRequest requestProduct = new ProductRequest();
        requestProduct.setProductId(101L);
        requestProduct.setQuantity(2);
        
        when(cartRepository.findByCustomerId(1L)).thenReturn(Optional.of(cart));
        when(productClient.getProductStock(requestProduct.getProductId())).thenReturn(100);
        when(productClient.getProductPrice(requestProduct.getProductId())).thenReturn(50.0);

        // Act
        Cart updatedCart = cartService.addProductToCart(1L, requestProduct);

        // Assert
        assertNotNull(updatedCart);
        assertEquals(1, updatedCart.getItems().size());
        verify(productClient).updateStockProduct(anyLong(), anyInt()); // Ensure stock update is called
    }

    @Test
    void testAddProductToCart_whenInsufficientStock() {
        // Arrange
        Cart cart = new Cart();
        cart.setCustomerId(1L);
        
        ProductRequest requestProduct = new ProductRequest();
        requestProduct.setProductId(101L);
        requestProduct.setQuantity(200); // Trying to add 200 items

        when(cartRepository.findByCustomerId(1L)).thenReturn(Optional.of(cart));
        when(productClient.getProductStock(requestProduct.getProductId())).thenReturn(100); // Only 100 items available

        // Act & Assert
        ProductOutOfStockException exception = assertThrows(ProductOutOfStockException.class, () -> {
            cartService.addProductToCart(1L, requestProduct);
        });
        assertEquals("Stock insuficiente para el producto 101. Stock disponible: 100", exception.getMessage());
    }

    @Test
    void testUpdateProductQuantity_whenQuantityUpdatedSuccessfully() {
        // Arrange
        Cart cart = new Cart();
        cart.setCustomerId(1L);
        CartItem cartItem = new CartItem();
        cartItem.setProductId(101L);
        cartItem.setQuantity(5);
        cart.getItems().add(cartItem);

        when(cartRepository.findByCustomerId(1L)).thenReturn(Optional.of(cart));
        when(productClient.getProductStock(cartItem.getProductId())).thenReturn(100);
        when(productClient.getProductPrice(cartItem.getProductId())).thenReturn(50.0);

        // Act
        Cart updatedCart = cartService.updateProductQuantity(1L, 101L, 3); // Adding 3 more items

        // Assert
        assertEquals(8, updatedCart.getItems().get(0).getQuantity()); // 5 + 3
        verify(productClient).updateStockProduct(anyLong(), anyInt());
    }

    @Test
    void testCalculatePriceWithDiscount() {
        // Arrange
        LocalDate purchaseDate = LocalDate.of(2024, 4, 1); // First semester
        double price = 100.0;

        // Act
        double discountedPrice = cartService.calculatePriceWithDiscount(purchaseDate, price);

        // Assert
        assertEquals(95.0, discountedPrice); // 100 - 5% of 100
    }
}
