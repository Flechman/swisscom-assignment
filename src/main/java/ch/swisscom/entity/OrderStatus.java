package ch.swisscom.entity;

public enum OrderStatus {
    ACKNOWLEDGED("ACK"),
    RUNNING("R"),
    DELIVERED("D"),
    CANCELLED("C");
    
    public final String label;

    private OrderStatus(String label) {
        this.label = label;
    }
}