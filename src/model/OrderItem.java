package model;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {

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

}
