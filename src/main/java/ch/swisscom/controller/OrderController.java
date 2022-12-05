package ch.swisscom.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.swisscom.entity.Order;
import ch.swisscom.entity.OrderStatus;
import ch.swisscom.entity.Product;
import ch.swisscom.prototype.OrderRepository;
import ch.swisscom.prototype.ProductRepository;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @PostMapping("/product/{productId}")
    public Order createNewOrder(@PathVariable("productId") long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()) {
          throw new NoSuchElementException(
            "Entity with id [" + id + "] does not exist"
          );
        }
        if(product.get().getStock() <= 0) {
            throw new IllegalStateException(
              "Product out of stock!"
            );
        }
        Product p = product.get();
        p.addStock(-1);
        productRepository.save(p);
        return orderRepository.save(new Order(p.getId(), OrderStatus.ACKNOWLEDGED));
    }
    
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable("id") long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty()) {
            throw new NoSuchElementException(
                "Entity with id [" + id + "] does not exist"
              );
        }
        return order.get();
    }
    
    @GetMapping("/{id}/product")
    public Product getOrderProduct(@PathVariable("id") long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty()) {
            throw new NoSuchElementException(
                "Entity with id [" + id + "] does not exist"
              );
        }
        return productRepository.findById(order.get().getProductId()).get(); //product exists if order exists
    }
    
    @PutMapping("/{id}/cancel")
    public Order cancelOrder(@PathVariable("id") long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty()) {
            throw new NoSuchElementException(
                "Entity with id [" + id + "] does not exist"
              );
        }
        Order o = order.get();
        if(o.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException(
                "Order already delivered"
              );
        }
        o.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(o);
    }
    
    @PutMapping("/{id}/deliver")
    public Order deliverOrder(@PathVariable("id") long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty()) {
            throw new NoSuchElementException(
                "Entity with id [" + id + "] does not exist"
              );
        }
        Order o = order.get();
        if(o.getStatus() != OrderStatus.DELIVERED && o.getStatus() != OrderStatus.CANCELLED) {
            o.setStatus(OrderStatus.DELIVERED);
            return orderRepository.save(o);
        } else {
            throw new IllegalStateException(
                "Order is either cancelled or has already been delivered"
              );
        }
    }
    
    @GetMapping("/{id}/status")
    public OrderStatus getOrderStatus(@PathVariable("id") long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty()) {
            throw new NoSuchElementException(
                "Entity with id [" + id + "] does not exist"
              );
        }
        return order.get().getStatus();
    }
    
}
