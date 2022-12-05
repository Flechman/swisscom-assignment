package ch.swisscom.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String type;
    private String name;
    private String description;
    private int stock;
    
    public Product() {}
    
    public Product(String type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.stock = 0;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getStock() {
        return stock;
    }
    
    public void incrementStock() {
        --stock;
    }
    
    public void decrementStock() {
        ++stock;
    }
    
    public void setStock(int v) {
        stock = v;
    }
    
    public void addStock(int v) {
        stock += v;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Product) {
            Product other = (Product)obj;
            return this.id == other.id && this.type.equals(other.type) && this.name.equals(other.name) && this.description.equals(other.description) && this.stock == other.stock;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.type, this.name, this.description, this.stock);
    }

    @Override
    public String toString() {
        return "Product{id: "+id+", type: "+type+", name: "+name+", description: "+description+", stock: "+stock+"}";
    }
}
