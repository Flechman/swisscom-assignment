package ch.swisscom.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.swisscom.entity.Product;
import ch.swisscom.prototype.ProductRepository;

@RestController
@RequestMapping("/product")
public class ProductController {
    
    @Autowired
    private ProductRepository repository;

    @GetMapping("/{id}")
    public Product findProduct(@PathVariable("id") long id) {
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()) {
          throw new NoSuchElementException(
            "Entity with id [" + id + "] does not exist"
          );
        }
        return product.get();
    }
    
    @PostMapping
    public Product createNewProduct(@RequestBody Product newProduct) {
        return repository.save(newProduct);
    }
    
    @DeleteMapping("/{id}")
    public Product removeProduct(@PathVariable("id") long id) {
        Optional<Product> fromId = repository.findById(id);
        if (fromId.isEmpty()) {
          throw new NoSuchElementException(
            "Entity with id [" + id + "] does not exist"
          );
        }
        repository.deleteById(id);
        return fromId.get();
    }
    
    private Product modifyStock(long id, int stock, boolean replace) {
        Optional<Product> fromId = repository.findById(id);
        if (fromId.isEmpty()) { //Convention to throw an exception
          throw new NoSuchElementException(
            "Cannot update a non-existing code entity, it should first be created."
          );
        }
        Product entity = fromId.get();
        if(!replace) {
            if(entity.getStock() + stock < 0) {
                if(stock < 0) {
                    throw new IllegalArgumentException("not enough stock");
                }
            }
            entity.addStock(stock);
        } else {
            entity.setStock(stock);
        }
        return repository.save(entity);
    }
    
    @PutMapping("/{id}/newstock")
    public Product changeStock(@PathVariable("id") long id, int stock) {
        if(stock < 0) {
            throw new IllegalArgumentException("stock must be positive");
        }
        return modifyStock(id, stock, true);
    }
    
    @PutMapping("/{id}/addstock")
    public Product addStock(@PathVariable("id") long id, int stockAdd) {
        return modifyStock(id, stockAdd, false);
    }
    
    @GetMapping("/{id}/stock")
    public int getStock(@PathVariable("id") long id) {
        Optional<Product> fromId = repository.findById(id);
        if (fromId.isEmpty()) { //Convention to throw an exception
          throw new NoSuchElementException(
            "Cannot update a non-existing code entity, it should first be created."
          );
        }
        Product entity = fromId.get();
        return entity.getStock();
    }
}
