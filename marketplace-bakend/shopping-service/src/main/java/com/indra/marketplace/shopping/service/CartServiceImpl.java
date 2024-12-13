package com.indra.marketplace.shopping.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.indra.marketplace.common.service.CommonServiceImpl;
import com.indra.marketplace.shopping.client.CustomerClient;
import com.indra.marketplace.shopping.client.ProductClient;
import com.indra.marketplace.shopping.dto.Customer;
import com.indra.marketplace.shopping.dto.Product;
import com.indra.marketplace.shopping.dto.ProductRequest;
import com.indra.marketplace.shopping.entity.Cart;
import com.indra.marketplace.shopping.entity.CartItem;
import com.indra.marketplace.shopping.exception.ProductNoFoundCartException;
import com.indra.marketplace.shopping.exception.ProductNoFoundException;
import com.indra.marketplace.shopping.exception.ProductOutOfStockException;
import com.indra.marketplace.shopping.repository.CartRepository;

import feign.FeignException;

@Service
public class CartServiceImpl extends CommonServiceImpl<Cart, CartRepository> implements CartService{
	
	@Autowired
    private CustomerClient customerClient;

    @Autowired
    private ProductClient productClient;
    
    @Value("${descuento-primer-semestre}")
    private double descuentoPrimerSemestre;

    @Value("${descuento-segundo-semestre}")
    private double descuentoSegundoSemestre;

	
	@Override
	public Cart save(Cart cart) {
		
		Cart cartDb = repository.findByCustomerId(cart.getCustomerId()).orElse(null);
		
		if (cartDb != null) {
			return cartDb;
		}
				
		cartDb = repository.save(cart);
		cartDb.getItems().forEach(cartItem -> {
			
			Integer currentStock = getProductStockOrThrow(cartItem.getProductId());
			
			validateStockAvailability(cartItem.getQuantity(), currentStock, cartItem.getProductId());
			
			 Double price = productClient.getProductPrice(cartItem.getProductId());			 
			 cartItem.setPrice(calculatePriceWithDiscount(LocalDate.now(),price));
			 
			 productClient.updateStockProduct( cartItem.getProductId(),
					 cartItem.getQuantity() * -1);
		});

		return cartDb;		
	}
	
	@Override
	public Optional<Cart> findById(Long customerId) {

		Cart cart = repository.findByCustomerId(customerId).orElse(null);
		
		if (null != cart) {
			Customer customer = customerClient.listById(cart.getCustomerId()).getBody();
			cart.setCustomer(customer);
			List<CartItem> listItem = cart.getItems().stream().map(cartItem -> {
				 Product product = productClient.listById(cartItem.getProductId()).getBody();
				 cartItem.setProduct(product);
				return cartItem;
			}).collect(Collectors.toList());
			cart.setItems(listItem);
		}
		return Optional.of(cart);
	} 
	
	public Cart removeProductFromCart(Long customerId, Long productId) {
		
        Cart cart = repository.findByCustomerId(customerId).orElse(null);        
        cart.removeItem(productId);
        return repository.save(cart);
    }
	
	public Cart addProductToCart(Long customerId, ProductRequest requestProduct) {

		Cart cart = repository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCustomerId(customerId);
                    return repository.save(newCart);
                });
		
		Integer currentStock = getProductStockOrThrow(requestProduct.getProductId());
		
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(requestProduct.getProductId()))
                .findFirst();

        Double price = 0.0;
        //int totalRequestedQuantity;
        
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            
            price = productClient.getProductPrice(item.getProductId());
            
            validateStockAvailability(item.getQuantity() + requestProduct.getQuantity(), currentStock, requestProduct.getProductId());
            
			item.setPrice(calculatePriceWithDiscount(LocalDate.now(),price));			
            item.setQuantity(item.getQuantity() + requestProduct.getQuantity());
        } 
        else {
            CartItem newItem = new CartItem();
            newItem.setProductId(requestProduct.getProductId());
            newItem.setQuantity(requestProduct.getQuantity());
            price = productClient.getProductPrice(requestProduct.getProductId());
            newItem.setPrice(calculatePriceWithDiscount(LocalDate.now(),price));
            cart.getItems().add(newItem);
        }
        
        productClient.updateStockProduct(
                requestProduct.getProductId(),
                requestProduct.getQuantity() * -1 
        );

        return repository.save(cart);
    }
	
	public Cart updateProductQuantity(Long customerId, Long productId, Integer quantity) {        
        Cart cart = repository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("El carrito no existe para el cliente con ID: " + customerId));
        
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();
		
		Integer currentStock = getProductStockOrThrow(productId);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            if (quantity > 0) {
                item.setQuantity(item.getQuantity()+quantity);
                
                Double price = productClient.getProductPrice(item.getProductId());
                
                if (price == null) {
                    price = 0.0;  
                }
                
                validateStockAvailability(item.getQuantity() + quantity, currentStock, productId);
                
    			item.setPrice(calculatePriceWithDiscount(LocalDate.now(),price));
                
                productClient.updateStockProduct(
                		productId,
                		quantity * -1 
                );
                
            } else {
                cart.removeItem(productId); 
            }
        } else {
            throw new ProductNoFoundCartException("El producto no existe en el carrito.");
        }

        return repository.save(cart);
    }
	
	
	public  double calculatePriceWithDiscount(LocalDate purchaseDate, double price) {
        if (purchaseDate == null || price <= 0) {
            throw new IllegalArgumentException("La fecha de compra y el monto total deben ser válidos.");
        }

        int month = purchaseDate.getMonthValue();
        double discountRate = (month >= 1 && month <= 6) ? descuentoPrimerSemestre : descuentoSegundoSemestre;

        return price-(price * discountRate);
    }
	
	private Integer getProductStockOrThrow(Long productId) {
	    try {
	        return productClient.getProductStock(productId);
	    } catch (FeignException.FeignClientException e) {
	        throw new ProductNoFoundException("Producto con ID " + productId + " no encontrado.");
	    }
	}
	
	private void validateStockAvailability(Integer quantityRequest, Integer currentStock, Long productId) {	    
	    if (quantityRequest > currentStock) {
	        throw new ProductOutOfStockException(
	            "Stock insuficiente para el producto " + productId +
	            ". Stock disponible: " + currentStock
	        );
	    }
	}
	
	
}
