package ch.swisscom.prototype;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.swisscom.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findAll();   
}
