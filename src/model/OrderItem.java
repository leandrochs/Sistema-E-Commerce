package model;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {

    private final Product product;
    private int quantity;
    private final BigDecimal saleValue;

    public OrderItem(Product product, int quantity, BigDecimal saleValue) {
        this.product = product;
        this.quantity = quantity;
        this.saleValue = saleValue;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSaleValue() {
        return saleValue;
    }
}

