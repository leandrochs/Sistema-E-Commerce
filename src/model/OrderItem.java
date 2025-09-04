package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final Product product;
    private int quantity;
    private final BigDecimal saleValue;

    public OrderItem(UUID id, Product product, int quantity, BigDecimal saleValue) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.saleValue = saleValue;
    }

    public UUID getId() {
        return id;
    }
    public Product getProduct() {
        return product;
    }
    public int getQuantity() {
        return quantity;
    }
    public BigDecimal getSaleValue() {
        return saleValue;
    }
}


