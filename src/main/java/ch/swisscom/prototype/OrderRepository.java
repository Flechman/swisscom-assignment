package ch.swisscom.prototype;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.swisscom.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findAll();   
}
