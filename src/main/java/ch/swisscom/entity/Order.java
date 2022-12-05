package ch.swisscom.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Long productId;
    private String status;
    
    public Order() {}
    
    public Order(Long productId, OrderStatus status) {
        this.productId = productId;
        this.status = status.label;
    }
    
    public Long getId() {
        return id;
    }
    
    public Long getProductId() {
        return productId;
    }
    
    public OrderStatus getStatus() {
        return OrderStatus.valueOf(status);
    }
    
    public void setStatus(OrderStatus status) {
        this.status = status.label;
    }
    
    public void setProductId(Long id) {
        this.productId = id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Order) {
            Order other = (Order)obj;
            return id == other.id && productId == other.productId && status.equals(other.status);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.productId);
    }

    @Override
    public String toString() {
        return "Product{id: "+id+", product id: "+productId+", status: "+status+"}";
    }

}
